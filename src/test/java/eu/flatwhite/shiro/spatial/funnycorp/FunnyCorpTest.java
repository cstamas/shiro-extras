package eu.flatwhite.shiro.spatial.funnycorp;

import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

import eu.flatwhite.shiro.spatial.Relation;
import eu.flatwhite.shiro.spatial.SamePointRelationProvider;
import eu.flatwhite.shiro.spatial.SpatialPermission;
import eu.flatwhite.shiro.spatial.SphereRelationProvider;
import eu.flatwhite.shiro.spatial.funnycorp.Person.Gender;

public class FunnyCorpTest
    extends TestCase
{
    public void testFunnyCorp()
    {
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

        Person jason = new Person( personSpace, "Jason", Gender.MALE, 1 );
        Person thomas = new Person( personSpace, "Thomas", Gender.MALE, 5 );
        Person kristine = new Person( personSpace, "Kristine", Gender.FEMALE, 12 );
        Person damian = new Person( personSpace, "Damian", Gender.MALE, 10 );
        Person toby = new Person( personSpace, "Toby", Gender.MALE, 15 );
        Person linda = new Person( personSpace, "Linda", Gender.FEMALE, 22 );

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
        WildcardPermission coffePermission = new WildcardPermission( "coffee" );

        SpatialPermission coffeSpatialPermission =
            new SpatialPermission( new Person( genderSpace, "template", Gender.FEMALE, 0 ),
                new SamePointRelationProvider(), coffePermission );

        check( false, coffeSpatialPermission, jason, coffePermission );
        check( false, coffeSpatialPermission, thomas, coffePermission );
        check( true, coffeSpatialPermission, kristine, coffePermission );
        check( false, coffeSpatialPermission, damian, coffePermission );
        check( false, coffeSpatialPermission, toby, coffePermission );
        check( true, coffeSpatialPermission, linda, coffePermission );

        // Rule 2 - all the boys may have a coke
        // gender space is finite space, so SamePointRelation provider does it
        WildcardPermission cokePermission = new WildcardPermission( "coke" );

        SpatialPermission cokeSpatialPermission =
            new SpatialPermission( new Person( genderSpace, "template", Gender.MALE, 0 ),
                new SamePointRelationProvider(), cokePermission );

        check( true, cokeSpatialPermission, jason, cokePermission );
        check( true, cokeSpatialPermission, thomas, cokePermission );
        check( false, cokeSpatialPermission, kristine, cokePermission );
        check( true, cokeSpatialPermission, damian, cokePermission );
        check( true, cokeSpatialPermission, toby, cokePermission );
        check( false, cokeSpatialPermission, linda, cokePermission );

        // Rule 3 - all the "merited" employees (having badgeNo less or equal 10) may have a beer
        // Merit space is non-finite space, and the "merit threshold" is defined as "10 or less", so we
        // need to do it like this:
        WildcardPermission beerPermission = new WildcardPermission( "beer" );

        HashMap<Relation, Permission> permissions = new HashMap<Relation, Permission>();
        permissions.put( Relation.TOUCHES, beerPermission );
        permissions.put( Relation.INSIDE, beerPermission );

        SpatialPermission beerSpatialPermission =
            new SpatialPermission( new Person( meritSpace, "template", null, 10 ), new SphereRelationProvider(),
                permissions );

        check( true, beerSpatialPermission, jason, beerPermission );
        check( true, beerSpatialPermission, thomas, beerPermission );
        check( false, beerSpatialPermission, kristine, beerPermission );
        check( true, beerSpatialPermission, damian, beerPermission );
        check( false, beerSpatialPermission, toby, beerPermission );
        check( false, beerSpatialPermission, linda, beerPermission );
    }

    protected void check( boolean impliesExpected, SpatialPermission sp, Person person,
                          Permission vendingMachineContentPermission )
    {
        // for checks, we have to "teleport" (using Avatar) from one space to another the person being compared, to
        // obey the space where the rule's spatial is defined. Also, employees are in personSpace, that is actually not
        // a space, since there is no distance defined.

        Assert.assertEquals( impliesExpected, sp.implies( new SpatialPermission( new Avatar(
            sp.getSpatial().getSpace(), person ), new SamePointRelationProvider(), vendingMachineContentPermission ) ) );
    }
}
