package eu.flatwhite.shiro.spatial;

import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.shiro.authz.Permission;

import eu.flatwhite.shiro.spatial.finite.Node;
import eu.flatwhite.shiro.spatial.finite.NodeRelationProvider;
import eu.flatwhite.shiro.spatial.finite.NodeResolver;
import eu.flatwhite.shiro.spatial.finite.NodeSpace;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace3d;
import eu.flatwhite.shiro.spatial.inifinite.Point3d;

public class SpatialPermissionTest
    extends TestCase
{
    public void testSphere()
    {
        // set up the 3d euclidean space
        EuclideanSpace3d space = new EuclideanSpace3d();

        // Problem: define a sphere with radius of 1 in space. Air molecules within that sphere have permission
        // "inside", on the sphere surface has permission "touches" and outside it has permission "outside"
        Point3d myPoint = new Point3d( space, 1, 0, 0 );

        // create my permission
        HashMap<Relation, String> permissions = new HashMap<Relation, String>();
        permissions.put( Relation.TOUCHES, "touches" );
        permissions.put( Relation.INSIDE, "inside" );
        permissions.put( Relation.OUTSIDE, "outside" );
        SpatialPermission permission = new SpatialPermission( myPoint, new SphereRelationProvider(), permissions );

        // we have a sphere with radius 1. all these below are touching it
        SpatialPermission touch1 =
            new SpatialPermission( new Point3d( space, 0, 1, 0 ), new SamePointRelationProvider(),
                "touches" );
        SpatialPermission touch2 =
            new SpatialPermission( new Point3d( space, 0, 0, 1 ), new SamePointRelationProvider(),
                "touches" );
        SpatialPermission touch3 =
            new SpatialPermission( new Point3d( space, 1, 0, 0 ), new SamePointRelationProvider(),
                "touches" );

        // the origin is inside
        SpatialPermission inside =
            new SpatialPermission( space.getOrigin(), new SamePointRelationProvider(),
                "inside" );

        // this point is outside
        SpatialPermission outside =
            new SpatialPermission( new Point3d( space, 2, 2, 2 ), new SamePointRelationProvider(),
                "outside" );

        Assert.assertTrue( permission.implies( touch1 ) );
        Assert.assertTrue( permission.implies( touch2 ) );
        Assert.assertTrue( permission.implies( touch3 ) );
        Assert.assertTrue( permission.implies( inside ) );
        Assert.assertTrue( permission.implies( outside ) );

        // point does not touch
        SpatialPermission wrong1 =
            new SpatialPermission( new Point3d( space, 2, 0, 0 ), new SamePointRelationProvider(),
                "touches" );
        // origin is not outside
        SpatialPermission wrong2 =
            new SpatialPermission( space.getOrigin(), new SamePointRelationProvider(), 
                "outside"  );
        // point is not inside
        SpatialPermission wrong3 =
            new SpatialPermission( new Point3d( space, 2, 2, 2 ), new SamePointRelationProvider(),
                "inside" );

        Assert.assertFalse( permission.implies( wrong1 ) );
        Assert.assertFalse( permission.implies( wrong2 ) );
        Assert.assertFalse( permission.implies( wrong3 ) );
    }

    public void testPhilippe()
    {
        // set up the space, a node space (this space is not complete euclidean space, you can have two points in space
        // but with non-defined distance, see NodeSpace implementation)
        NodeSpace space = new NodeSpace();

        // my app has following menu structure:
        // EDIT
        // - Cut
        // - Copy
        // - Paste
        // -- as Text
        // -- as HTML
        //
        // I am granting "execute" permission for the "as Html" node to a user, but I want all the "clickable path" (to
        // get to that node) be implicitly allowed to user also.

        Node mEdit = Node.parseString( space, "/edit" );
        Node mEditCut = new Node( mEdit, "Cut" );
        Node mEditCopy = new Node( mEdit, "Copy" );
        Node mEditPaste = new Node( mEdit, "Paste" );
        Node mEditPasteAsText = new Node( mEditPaste, "As Text" );
        Node mEditPasteAsHtml = new Node( mEditPaste, "As Html" );

        // granting "execute" permission on mEditPasteAsHtml to some user
        // but I want that user be able to "click the path" down to that menu
        // in NodeSpace, TOUCHES means same node, INSIDE means node between root and current node (it must be on this
        // path), OUTSIDE means node below current node. Nodes on different paths are UNRELATED.
        HashMap<Relation, String> permissions = new HashMap<Relation, String>();
        permissions.put( Relation.TOUCHES, "view,execute" );
        permissions.put( Relation.INSIDE, "view" );
        SpatialPermission permission =
            new SpatialPermission( mEditPasteAsHtml, new NodeRelationProvider(), permissions );

        SpatialPermission viewEdit =
            new SpatialPermission( mEdit, new NodeRelationProvider(), "view" );
        SpatialPermission executeEdit =
            new SpatialPermission( mEdit, new NodeRelationProvider(), "execute" );
        SpatialPermission viewEditCut =
            new SpatialPermission( mEditCut, new NodeRelationProvider(), "view" );
        SpatialPermission executeEditCut =
            new SpatialPermission( mEditCut, new NodeRelationProvider(), "execute" );
        SpatialPermission viewEditPaste =
            new SpatialPermission( mEditPaste, new NodeRelationProvider(), "view" );
        SpatialPermission executeEditPaste =
            new SpatialPermission( mEditPaste, new NodeRelationProvider(), "execute" );
        SpatialPermission viewEditPasteAsText =
            new SpatialPermission( mEditPasteAsText, new NodeRelationProvider(), "view" );
        SpatialPermission executeEditPasteAsText =
            new SpatialPermission( mEditPasteAsText, new NodeRelationProvider(), "execute" );
        SpatialPermission viewEditPasteAsHtml =
            new SpatialPermission( mEditPasteAsHtml, new NodeRelationProvider(), "view" );
        SpatialPermission executeEditPasteAsHtml =
            new SpatialPermission( mEditPasteAsHtml, new NodeRelationProvider(), "execute" );

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
    
    public void testPhilippeNodeParsing() {
      
      // set up the space, a node space (this space is not complete euclidean space, you can have two points in space
      // but with non-defined distance, see NodeSpace implementation)
      SpatialPermissionResolver permissionResolver = new SpatialPermissionResolver(new NodeSpace(), new NodeResolver(), new NodeRelationProvider());
      
      Permission viewEdit = permissionResolver.resolvePermission("menu:/edit:view");
      Permission executeEdit = permissionResolver.resolvePermission("menu:/edit:execute");
      Permission viewEditCut = permissionResolver.resolvePermission("menu:/edit/Cut:view");
      Permission executeEditCut = permissionResolver.resolvePermission("menu:/edit/Cut:execute");
      Permission viewEditPaste = permissionResolver.resolvePermission("menu:/edit/Paste:view");
      Permission executeEditPaste = permissionResolver.resolvePermission("menu:/edit/Paste:execute");
      Permission viewEditPasteAsText = permissionResolver.resolvePermission("menu:/edit/Paste/As Text:view");
      Permission executeEditPasteAsText = permissionResolver.resolvePermission("menu:/edit/Paste/As Text:execute");
      Permission viewEditPasteAsHtml = permissionResolver.resolvePermission("menu:/edit/Paste/As HTML:view");
      Permission executeEditPasteAsHtml = permissionResolver.resolvePermission("menu:/edit/Paste/As HTML:execute:view");

      Permission permission = permissionResolver.resolvePermission("menu:/edit/Paste/As HTML:view,execute:view");
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
