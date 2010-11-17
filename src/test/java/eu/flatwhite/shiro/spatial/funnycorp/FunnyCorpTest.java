package eu.flatwhite.shiro.spatial.funnycorp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.InvalidPermissionStringException;

import eu.flatwhite.shiro.spatial.MapSpaceRelationProvider;
import eu.flatwhite.shiro.spatial.MapSpaceResolver;
import eu.flatwhite.shiro.spatial.MapSpatialResolver;
import eu.flatwhite.shiro.spatial.Relation;
import eu.flatwhite.shiro.spatial.SamePointRelationProvider;
import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.SpaceRelationProvider;
import eu.flatwhite.shiro.spatial.SpaceResolver;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.SpatialPermission;
import eu.flatwhite.shiro.spatial.SpatialPermissionResolver;
import eu.flatwhite.shiro.spatial.SpatialResolver;
import eu.flatwhite.shiro.spatial.SphereRelationProvider;
import eu.flatwhite.shiro.spatial.finite.EnumSpatial;
import eu.flatwhite.shiro.spatial.funnycorp.Person.Gender;
import eu.flatwhite.shiro.spatial.inifinite.Point;
import eu.flatwhite.shiro.spatial.inifinite.PointResolver;

public class FunnyCorpTest extends TestCase {
  public void testFunnyCorp() {
    // There is the "FunnyCorp LTD", that has some employees.
    // One day, CEO made a strange decision, and made it written that:
    //
    // The vending machine (that runs with embedded Java and uses Shiro as security framework) shall
    // obey the following rules:
    //
    // 1 - all the girls may have a coffee
    // 2 - all the boys may have a coke
    // 3 - all the "merited" employees (having badgeNo less or equal 10) may have a beer
    //
    // Note: this is an example of "misuse" of spatiality, since we do not have a real "space" defined here
    // in it's Euclidean way, but still, by cheating with relation providers we can do even this.
    // One of these is actually a finite space, etc.

    // let define "person space" and the employees
    PersonSpace personSpace = new PersonSpace();

    Person jason = new Person(personSpace, "Jason", Gender.MALE, 1);
    Person thomas = new Person(personSpace, "Thomas", Gender.MALE, 5);
    Person kristine = new Person(personSpace, "Kristine", Gender.FEMALE, 12);
    Person damian = new Person(personSpace, "Damian", Gender.MALE, 10);
    Person toby = new Person(personSpace, "Toby", Gender.MALE, 15);
    Person linda = new Person(personSpace, "Linda", Gender.FEMALE, 22);

    // we are defining two another spaces, where our rules will be defined
    PersonGenderSpace genderSpace = new PersonGenderSpace();
    PersonMeritSpace meritSpace = new PersonMeritSpace();

    // since we "cheat" with relation provider, it is completely indifferent who we provide in permission as spatial
    // so we use a "template" person, that actually provides "coordinates" to relate to. So, we use a special person
    // that is actually a "template person", and just serves as source for space to get some coordinates and
    // calculate the distance.

    // Rule 1 - all the girls may have a coffee
    // girls may have coffee
    // gender space is finite space, so SamePointRelation provider does it
    // the important thing is the "space" in which we place that template person
    String coffePermission = "coffee";

    SpatialPermission coffeSpatialPermission = new SpatialPermission(new EnumSpatial(genderSpace, Gender.FEMALE), new SamePointRelationProvider(), coffePermission);

    check(false, coffeSpatialPermission, jason, coffePermission);
    check(false, coffeSpatialPermission, thomas, coffePermission);
    check(true, coffeSpatialPermission, kristine, coffePermission);
    check(false, coffeSpatialPermission, damian, coffePermission);
    check(false, coffeSpatialPermission, toby, coffePermission);
    check(true, coffeSpatialPermission, linda, coffePermission);

    // Rule 2 - all the boys may have a coke
    // gender space is finite space, so SamePointRelation provider does it
    String cokePermission = "coke";

    SpatialPermission cokeSpatialPermission = new SpatialPermission(new EnumSpatial(genderSpace, Gender.MALE), new SamePointRelationProvider(), cokePermission);

    check(true, cokeSpatialPermission, jason, cokePermission);
    check(true, cokeSpatialPermission, thomas, cokePermission);
    check(false, cokeSpatialPermission, kristine, cokePermission);
    check(true, cokeSpatialPermission, damian, cokePermission);
    check(true, cokeSpatialPermission, toby, cokePermission);
    check(false, cokeSpatialPermission, linda, cokePermission);

    // Rule 3 - all the "merited" employees (having badgeNo less or equal 10) may have a beer
    // Merit space is non-finite space, and the "merit threshold" is defined as "10 or less", so we
    // need to do it like this:
    String beerPermission = "beer";

    HashMap<Relation, String> permissions = new HashMap<Relation, String>();
    permissions.put(Relation.TOUCHES, beerPermission);
    permissions.put(Relation.INSIDE, beerPermission);

    SpatialPermission beerSpatialPermission = new SpatialPermission(new Point(meritSpace, 10), new SphereRelationProvider(), permissions);

    check(true, beerSpatialPermission, jason, beerPermission);
    check(true, beerSpatialPermission, thomas, beerPermission);
    check(false, beerSpatialPermission, kristine, beerPermission);
    check(true, beerSpatialPermission, damian, beerPermission);
    check(false, beerSpatialPermission, toby, beerPermission);
    check(false, beerSpatialPermission, linda, beerPermission);
  }

  public void testFunnyCorpFromStrings() {
    final PersonSpace personSpace = new PersonSpace();

    SpatialResolver personResolver = new SpatialResolver() {

      private List<Person> people = new ArrayList<Person>();

      {
        people.add(new Person(personSpace, "Jason", Gender.MALE, 1));
        people.add(new Person(personSpace, "Thomas", Gender.MALE, 5));
        people.add(new Person(personSpace, "Kristine", Gender.FEMALE, 12));
        people.add(new Person(personSpace, "Damian", Gender.MALE, 10));
        people.add(new Person(personSpace, "Toby", Gender.MALE, 15));
        people.add(new Person(personSpace, "Linda", Gender.FEMALE, 22));
      }

      @Override
      public Spatial parseSpatial(Space space, String spatialString) {
        for(Person p : people) {
          if(spatialString.equalsIgnoreCase(p.getName())) return p;
        }
        throw new InvalidPermissionStringException("no such person", spatialString);
      }
    };

    final PersonGenderSpace genderSpace = new PersonGenderSpace();
    SpatialResolver genderResolver = new SpatialResolver() {
      @Override
      public Spatial parseSpatial(Space space, String spatialString) {
        return new EnumSpatial(genderSpace, Gender.valueOf(spatialString.toUpperCase()));
      }
    };
    
    PersonMeritSpace meritSpace = new PersonMeritSpace();

    SpaceResolver spaceResolver = MapSpaceResolver.Builder.newSpaceMap().addSpace("person", personSpace).addSpace("gender", genderSpace).addSpace("merit", meritSpace).build();
    SpatialResolver spatialResolvers = MapSpatialResolver.Builder.newMap().add(personSpace, personResolver).add(genderSpace, genderResolver).add(meritSpace, new PointResolver()).build();
    SpaceRelationProvider spaceRelationProviders = MapSpaceRelationProvider.Builder.newMap().add(personSpace, new SamePointRelationProvider()).add(genderSpace, new SamePointRelationProvider()).add(meritSpace, new SphereRelationProvider()).build();
    SpatialPermissionResolver resolver = new SpatialPermissionResolver(spaceResolver, spatialResolvers, spaceRelationProviders);

    // Rule 1 - all the girls may have a coffee
    // girls may have coffee
    // gender space is finite space, so SamePointRelation provider does it
    // the important thing is the "space" in which we place that template person
    Permission coffeSpatialPermission = resolver.resolvePermission("gender:female:coffee");
    Assert.assertEquals(false, coffeSpatialPermission.implies(resolver.resolvePermission("person:Jason:coffee")));
    Assert.assertEquals(false, coffeSpatialPermission.implies(resolver.resolvePermission("person:Thomas:coffee")));
    Assert.assertEquals(true, coffeSpatialPermission.implies(resolver.resolvePermission("person:Kristine:coffee")));
    Assert.assertEquals(false, coffeSpatialPermission.implies(resolver.resolvePermission("person:Damian:coffee")));
    Assert.assertEquals(false, coffeSpatialPermission.implies(resolver.resolvePermission("person:Toby:coffee")));
    Assert.assertEquals(true, coffeSpatialPermission.implies(resolver.resolvePermission("person:Linda:coffee")));

    // Rule 2 - all the boys may have a coke
    // gender space is finite space, so SamePointRelation provider does it
    Permission cokeSpatialPermission = resolver.resolvePermission("gender:male:coke");
    Assert.assertEquals(true, cokeSpatialPermission.implies(resolver.resolvePermission("person:Jason:coke")));
    Assert.assertEquals(true, cokeSpatialPermission.implies(resolver.resolvePermission("person:Thomas:coke")));
    Assert.assertEquals(false, cokeSpatialPermission.implies(resolver.resolvePermission("person:Kristine:coke")));
    Assert.assertEquals(true, cokeSpatialPermission.implies(resolver.resolvePermission("person:Damian:coke")));
    Assert.assertEquals(true, cokeSpatialPermission.implies(resolver.resolvePermission("person:Toby:coke")));
    Assert.assertEquals(false, cokeSpatialPermission.implies(resolver.resolvePermission("person:Linda:coke")));

    // Rule 3 - all the "merited" employees (having badgeNo less or equal 10) may have a beer
    // Merit space is non-finite space, and the "merit threshold" is defined as "10 or less", so we
    // need to do it like this:
    Permission beerSpatialPermission = resolver.resolvePermission("merit:10:beer:beer");

    Assert.assertEquals(true, beerSpatialPermission.implies(resolver.resolvePermission("person:Jason:beer")));
    Assert.assertEquals(true, beerSpatialPermission.implies(resolver.resolvePermission("person:Thomas:beer")));
    Assert.assertEquals(false, beerSpatialPermission.implies(resolver.resolvePermission("person:Kristine:beer")));
    Assert.assertEquals(true, beerSpatialPermission.implies(resolver.resolvePermission("person:Damian:beer")));
    Assert.assertEquals(false, beerSpatialPermission.implies(resolver.resolvePermission("person:Toby:beer")));
    Assert.assertEquals(false, beerSpatialPermission.implies(resolver.resolvePermission("person:Linda:beer")));
  }

  protected void check(boolean impliesExpected, SpatialPermission sp, Person person, String vendingMachineContentPermission) {
    // for checks, we have to "teleport" (using Avatar) from one space to another the person being compared, to
    // obey the space where the rule's spatial is defined. Also, employees are in personSpace, that is actually not
    // a space, since there is no distance defined.

    Assert.assertEquals(impliesExpected, sp.implies(new SpatialPermission(person, new SamePointRelationProvider(), vendingMachineContentPermission)));
  }

}
