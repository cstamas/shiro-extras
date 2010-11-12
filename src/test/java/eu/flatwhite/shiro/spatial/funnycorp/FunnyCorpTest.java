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
        // 3 - all the "merit" employees having badge less than 10 may have a beer
        //
        // Note: this is an example of "misuse" of spatiality, since we do not have a real "space" defined here
        // in it's Euclidean way, but still, by cheating with relation providers we can do even this.
        // This is actually a finite space, etc.

        PersonSpace personSpace = new PersonSpace();

        Person jason = new Person( personSpace, "Jason", Gender.MALE, 1 );
        Person thomas = new Person( personSpace, "Thomas", Gender.MALE, 5 );
        Person kristine = new Person( personSpace, "Kristine", Gender.FEMALE, 12 );
        Person damian = new Person( personSpace, "Damian", Gender.MALE, 10 );
        Person toby = new Person( personSpace, "Toby", Gender.MALE, 15 );
        Person linda = new Person( personSpace, "Linda", Gender.FEMALE, 22 );

        PersonGenderSpace genderSpace = new PersonGenderSpace();
        PersonMeritSpace meritSpace = new PersonMeritSpace();

        // since we "cheat" with relation provider, it is completely indifferent who we provide in permission as spatial
        // so we use a "template" person, that actually provides "coordinates" to relate to.

        // girls may have coffee
        // gender space is finite space, so SamePointRelation provider does it
        SpatialPermission sp1 =
            new SpatialPermission( new Person( genderSpace, "template", Gender.FEMALE, 0 ),
                new SamePointRelationProvider(), new WildcardPermission( "coffee" ) );

        // boys may have coke
        // gender space is finite space, so SamePointRelation provider does it
        SpatialPermission sp2 =
            new SpatialPermission( new Person( genderSpace, "template", Gender.MALE, 0 ),
                new SamePointRelationProvider(), new WildcardPermission( "coke" ) );

        // people with merit may have beer
        // merit space is non-finite space, and the "merit threshold" is defined as "10 or less", so we
        // need to do it like this:
        HashMap<Relation, Permission> permissions = new HashMap<Relation, Permission>();
        permissions.put( Relation.TOUCHES, new WildcardPermission( "beer" ) );
        permissions.put( Relation.INSIDE, new WildcardPermission( "beer" ) );
        SpatialPermission sp3 =
            new SpatialPermission( new Person( meritSpace, "template", null, 10 ), new SphereRelationProvider(),
                permissions );

        // going in order
        Assert.assertEquals( false, sp1.implies( new SpatialPermission( new Avatar( genderSpace, jason ),
            new SamePointRelationProvider(), new WildcardPermission( "coffee" ) ) ) );
        Assert.assertEquals( false, sp1.implies( new SpatialPermission( new Avatar( genderSpace, thomas ),
            new SamePointRelationProvider(), new WildcardPermission( "coffee" ) ) ) );
        Assert.assertEquals( true, sp1.implies( new SpatialPermission( new Avatar( genderSpace, kristine ),
            new SamePointRelationProvider(), new WildcardPermission( "coffee" ) ) ) );
        Assert.assertEquals( false, sp1.implies( new SpatialPermission( new Avatar( genderSpace, damian ),
            new SamePointRelationProvider(), new WildcardPermission( "coffee" ) ) ) );
        Assert.assertEquals( false, sp1.implies( new SpatialPermission( new Avatar( genderSpace, toby ),
            new SamePointRelationProvider(), new WildcardPermission( "coffee" ) ) ) );
        Assert.assertEquals( true, sp1.implies( new SpatialPermission( new Avatar( genderSpace, linda ),
            new SamePointRelationProvider(), new WildcardPermission( "coffee" ) ) ) );

        // sp2
        Assert.assertEquals( true, sp2.implies( new SpatialPermission( new Avatar( genderSpace, jason ),
            new SamePointRelationProvider(), new WildcardPermission( "coke" ) ) ) );
        Assert.assertEquals( true, sp2.implies( new SpatialPermission( new Avatar( genderSpace, thomas ),
            new SamePointRelationProvider(), new WildcardPermission( "coke" ) ) ) );
        Assert.assertEquals( false, sp2.implies( new SpatialPermission( new Avatar( genderSpace, kristine ),
            new SamePointRelationProvider(), new WildcardPermission( "coke" ) ) ) );
        Assert.assertEquals( true, sp2.implies( new SpatialPermission( new Avatar( genderSpace, damian ),
            new SamePointRelationProvider(), new WildcardPermission( "coke" ) ) ) );
        Assert.assertEquals( true, sp2.implies( new SpatialPermission( new Avatar( genderSpace, toby ),
            new SamePointRelationProvider(), new WildcardPermission( "coke" ) ) ) );
        Assert.assertEquals( false, sp2.implies( new SpatialPermission( new Avatar( genderSpace, linda ),
            new SamePointRelationProvider(), new WildcardPermission( "coke" ) ) ) );

        // sp3
        Assert.assertEquals( true, sp3.implies( new SpatialPermission( new Avatar( meritSpace, jason ),
            new SamePointRelationProvider(), new WildcardPermission( "beer" ) ) ) );
        Assert.assertEquals( true, sp3.implies( new SpatialPermission( new Avatar( meritSpace, thomas ),
            new SamePointRelationProvider(), new WildcardPermission( "beer" ) ) ) );
        Assert.assertEquals( false, sp3.implies( new SpatialPermission( new Avatar( meritSpace, kristine ),
            new SamePointRelationProvider(), new WildcardPermission( "beer" ) ) ) );
        Assert.assertEquals( true, sp3.implies( new SpatialPermission( new Avatar( meritSpace, damian ),
            new SamePointRelationProvider(), new WildcardPermission( "beer" ) ) ) );
        Assert.assertEquals( false, sp3.implies( new SpatialPermission( new Avatar( meritSpace, toby ),
            new SamePointRelationProvider(), new WildcardPermission( "beer" ) ) ) );
        Assert.assertEquals( false, sp3.implies( new SpatialPermission( new Avatar( meritSpace, linda ),
            new SamePointRelationProvider(), new WildcardPermission( "beer" ) ) ) );
    }
}
