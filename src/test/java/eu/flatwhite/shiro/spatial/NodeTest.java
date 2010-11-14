package eu.flatwhite.shiro.spatial;

import eu.flatwhite.shiro.spatial.finite.Node;
import eu.flatwhite.shiro.spatial.finite.NodeSpace;
import junit.framework.Assert;
import junit.framework.TestCase;

public class NodeTest
    extends TestCase
{
    protected NodeSpace space;

    protected void setUp()
        throws Exception
    {
        super.setUp();

        this.space = new NodeSpace();
    }

    public void testSimple()
    {
        // tree is: "/some/other"
        Node other = Node.parseString( space, "/some/other" );

        Assert.assertEquals( "The path string is wrong!", "/some/other", other.getPathString() );

        // again, tree is: "/some/other", but creating it differently
        Node other1 = Node.parseString( space, "/some/other/deeper" );

        Assert.assertEquals( "The path string is wrong!", "/some/other/deeper", other1.getPathString() );
    }

    public void testRobustnessParse()
    {
        Node n;

        n = Node.parseString( space, "" );

        Assert.assertEquals( "/", n.getPathString() );

        n = Node.parseString( space, "foo" );

        Assert.assertEquals( "/foo", n.getPathString() );

        n = Node.parseString( space, "foo/bar" );

        Assert.assertEquals( "/foo/bar", n.getPathString() );

        n = Node.parseString( space, "/foo/bar" );

        Assert.assertEquals( "/foo/bar", n.getPathString() );

        n = Node.parseString( space, "foo//bar" );

        Assert.assertEquals( "/foo/bar", n.getPathString() );

        n = Node.parseString( space, "//foo//bar//" );

        Assert.assertEquals( "/foo/bar", n.getPathString() );
    }

    public void testSpatial()
    {
        // tree is: "/l1/l2"
        Node l2 = Node.parseString( space, "/l1/l2" );

        // tree is: "/l1/l3"
        Node l3 = Node.parseString( space, "/l1/l3" );

        // tree is: "/l1"
        Node l1 = Node.parseString( space, "/l1" );

        Assert.assertEquals( 0.0, space.getOrigin().distance( space.getOrigin() ) );
        Assert.assertEquals( 0.0, space.getOrigin().distance( l2.getParent().getParent() ) );
        Assert.assertEquals( 0.0, l2.getParent().getParent().distance( space.getOrigin() ) );
        Assert.assertEquals( 1.0, space.getOrigin().distance( l2.getParent() ) );
        Assert.assertEquals( 1.0, l2.getParent().distance( space.getOrigin() ) );
        Assert.assertEquals( 2.0, space.getOrigin().distance( l2 ) );
        Assert.assertEquals( 2.0, l2.distance( space.getOrigin() ) );
        Assert.assertEquals( 1.0, l2.distance( l2.getParent() ) );
        Assert.assertEquals( 1.0, l2.getParent().distance( l2 ) );

        Assert.assertEquals( 0.0, space.getOrigin().distance( space.getOrigin() ) );
        Assert.assertEquals( 1.0, l1.distance( space.getOrigin() ) );
        Assert.assertEquals( 1.0, l2.getParent().distance( space.getOrigin() ) );
        Assert.assertEquals( 1.0, l3.getParent().distance( space.getOrigin() ) );
        Assert.assertEquals( 2.0, l2.distance( space.getOrigin() ) );
        Assert.assertEquals( 2.0, l3.distance( space.getOrigin() ) );

        // l2 and l3 are non-comparable as we defined "distance" on Tree
        Assert.assertEquals( Double.NaN, l2.distance( l3 ) );
        Assert.assertEquals( Double.NaN, l3.distance( l2 ) );

        // l1 is comparable with both l2 and l3
        Assert.assertEquals( 1.0, l2.distance( l1 ) );
        Assert.assertEquals( 1.0, l3.distance( l1 ) );
        Assert.assertEquals( 1.0, l1.distance( l2 ) );
        Assert.assertEquals( 1.0, l1.distance( l3 ) );
    }
}
