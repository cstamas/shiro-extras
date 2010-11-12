package eu.flatwhite.shiro.spatial;

import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

public class SpatialPermissionTest
    extends TestCase
{
    public void testSphere()
    {
        // set up the space
        EuclideanSpace3d space = new EuclideanSpace3d();

        // Problem: define a sphere with radius of 1 in space. Air molecules within that sphere have permission
        // "inside", on the sphere surface has permission "touches" and outside it has permission "outside"
        Point3d myPoint = new Point3d( space, 1, 0, 0 );

        // create my permission
        HashMap<Relation, Permission> permissions = new HashMap<Relation, Permission>();
        permissions.put( Relation.TOUCHES, new WildcardPermission( "touches" ) );
        permissions.put( Relation.INSIDE, new WildcardPermission( "inside" ) );
        permissions.put( Relation.OUTSIDE, new WildcardPermission( "outside" ) );
        SpatialPermission permission = new SpatialPermission( myPoint, new SphereRelationProvider(), permissions );

        // we have a sphere with radius 1. all these below are touching it
        SpatialPermission touch1 =
            new SpatialPermission( new Point3d( space, 0, 1, 0 ), new SamePointRelationProvider(),
                new WildcardPermission( "touches" ) );
        SpatialPermission touch2 =
            new SpatialPermission( new Point3d( space, 0, 0, 1 ), new SamePointRelationProvider(),
                new WildcardPermission( "touches" ) );
        SpatialPermission touch3 =
            new SpatialPermission( new Point3d( space, 1, 0, 0 ), new SamePointRelationProvider(),
                new WildcardPermission( "touches" ) );

        // the origin is inside
        SpatialPermission inside =
            new SpatialPermission( space.getOrigin(), new SamePointRelationProvider(),
                new WildcardPermission( "inside" ) );

        // this point is outside
        SpatialPermission outside =
            new SpatialPermission( new Point3d( space, 2, 2, 2 ), new SamePointRelationProvider(),
                new WildcardPermission( "outside" ) );

        Assert.assertTrue( permission.implies( touch1 ) );
        Assert.assertTrue( permission.implies( touch2 ) );
        Assert.assertTrue( permission.implies( touch3 ) );
        Assert.assertTrue( permission.implies( inside ) );
        Assert.assertTrue( permission.implies( outside ) );

        // does not touch
        SpatialPermission wrong1 =
            new SpatialPermission( new Point3d( space, 2, 0, 0 ), new SamePointRelationProvider(),
                new WildcardPermission( "touches" ) );
        // is not outside
        SpatialPermission wrong2 =
            new SpatialPermission( space.getOrigin(), new SamePointRelationProvider(), new WildcardPermission(
                "outside" ) );
        // is not inside
        SpatialPermission wrong3 =
            new SpatialPermission( new Point3d( space, 2, 2, 2 ), new SamePointRelationProvider(),
                new WildcardPermission( "inside" ) );

        Assert.assertFalse( permission.implies( wrong1 ) );
        Assert.assertFalse( permission.implies( wrong2 ) );
        Assert.assertFalse( permission.implies( wrong3 ) );
    }

    public void testPhilippe()
    {
        // set up the space
        NodeSpace space = new NodeSpace();

        // my app has following menu structure:
        // EDIT
        // - Cut
        // - Copy
        // - Paste
        // -- as Text
        // -- as HTML
        //
        // I am enabling the "as Html" node to a user, hence I want all the "clickable path" be
        // allowed to user.

        Node mEdit = Node.parseString( space, "/edit" );
        Node mEditCut = new Node( mEdit, "Cut" );
        Node mEditCopy = new Node( mEdit, "Copy" );
        Node mEditPaste = new Node( mEdit, "Paste" );
        Node mEditPasteAsText = new Node( mEditPaste, "As Text" );
        Node mEditPasteAsHtml = new Node( mEditPaste, "As Html" );

        // granting "execute" permission on mEditPasteAsHtml to some user
        // but I want that user be able to "click the path" down to that menu
        HashMap<Relation, Permission> permissions = new HashMap<Relation, Permission>();
        permissions.put( Relation.TOUCHES, new WildcardPermission( "view,execute" ) );
        permissions.put( Relation.INSIDE, new WildcardPermission( "view" ) );
        SpatialPermission permission =
            new SpatialPermission( mEditPasteAsHtml, new NodeRelationProvider(), permissions );

        SpatialPermission viewEdit =
            new SpatialPermission( mEdit, new NodeRelationProvider(), new WildcardPermission( "view" ) );
        SpatialPermission executeEdit =
            new SpatialPermission( mEdit, new NodeRelationProvider(), new WildcardPermission( "execute" ) );
        SpatialPermission viewEditCut =
            new SpatialPermission( mEditCut, new NodeRelationProvider(), new WildcardPermission( "view" ) );
        SpatialPermission executeEditCut =
            new SpatialPermission( mEditCut, new NodeRelationProvider(), new WildcardPermission( "execute" ) );
        SpatialPermission viewEditPaste =
            new SpatialPermission( mEditPaste, new NodeRelationProvider(), new WildcardPermission( "view" ) );
        SpatialPermission executeEditPaste =
            new SpatialPermission( mEditPaste, new NodeRelationProvider(), new WildcardPermission( "execute" ) );
        SpatialPermission viewEditPasteAsText =
            new SpatialPermission( mEditPasteAsText, new NodeRelationProvider(), new WildcardPermission( "view" ) );
        SpatialPermission executeEditPasteAsText =
            new SpatialPermission( mEditPasteAsText, new NodeRelationProvider(), new WildcardPermission( "execute" ) );
        SpatialPermission viewEditPasteAsHtml =
            new SpatialPermission( mEditPasteAsHtml, new NodeRelationProvider(), new WildcardPermission( "view" ) );
        SpatialPermission executeEditPasteAsHtml =
            new SpatialPermission( mEditPasteAsHtml, new NodeRelationProvider(), new WildcardPermission( "execute" ) );

        // first check Philippe's assumption: we should be able to "click through" (whatever it means) up to the menu we
        // want to execute
        Assert.assertTrue( permission.implies( viewEdit ) );
        Assert.assertTrue( permission.implies( viewEditPaste ) );
        Assert.assertTrue( permission.implies( viewEditPasteAsHtml ) );
        Assert.assertTrue( permission.implies( executeEditPasteAsHtml ) );

        // now negative test: we should not be able to see anything else
        Assert.assertFalse( permission.implies( executeEdit ) );
        Assert.assertFalse( permission.implies( viewEditCut ) );
        Assert.assertFalse( permission.implies( executeEditCut ) );
        Assert.assertFalse( permission.implies( executeEditPaste ) );
        Assert.assertFalse( permission.implies( viewEditPasteAsText ) );
        Assert.assertFalse( permission.implies( executeEditPasteAsText ) );
    }
}
