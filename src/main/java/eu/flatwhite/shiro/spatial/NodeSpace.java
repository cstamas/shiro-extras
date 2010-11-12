package eu.flatwhite.shiro.spatial;

public class NodeSpace
    implements Space
{
    public Node getOrigin()
    {
        return new Node( this );
    }

    public boolean isContaining( Spatial spatial )
    {
        return spatial instanceof Node;
    }

    public double distance( Spatial s1, Spatial s2 )
    {
        if ( isContaining( s1 ) && isContaining( s2 ) )
        {
            Node p1 = (Node) s1;

            Node p2 = (Node) s2;

            // I am going the "easy" way ;)
            // not this http://www.topcoder.com/tc?module=Static&d1=tutorials&d2=lowestCommonAncestor

            // so, my assumption is, that this and spatial should be on same chain (chain := a list of nodes from root
            // to the node of Node having longer path, meaning, either 'this' is included in spatial, or spatial is
            // included in 'this'). For nodes lying on different chains, i will say NaN for now.

            final String thisPathString = p1.getPathString();
            final String thatPathString = p2.getPathString();

            // both begins with ROOT, so check for inclusion from one side
            if ( thisPathString.startsWith( thatPathString ) || thatPathString.startsWith( thisPathString ) )
            {
                // this path includes spatial path
                return Math.abs( p1.getPath().size() - p2.getPath().size() );
            }
            else
            {
                return Double.NaN;
            }
        }
        else
        {
            return Double.NaN;
        }
    }
}
