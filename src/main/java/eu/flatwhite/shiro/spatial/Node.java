package eu.flatwhite.shiro.spatial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Node
    extends AbstractSpatial
{
    private static final long serialVersionUID = 5730382523050068642L;

    private static final String PATH_SEPARATOR = "/";

    private final Node parent;

    private final String pathElem;

    // special constructor for ROOT
    protected Node( final Space space )
    {
        super( space );

        this.parent = null;
        this.pathElem = "";
    }

    public Node( final Node parent, final String pathElem )
    {
        super( parent.getSpace() );

        assert parent != null : "Parent cannot be null!";
        assert pathElem != null && pathElem.trim().length() > 0 : "Path element may not be empty!";

        this.parent = parent;
        this.pathElem = pathElem;
    }

    public Node getParent()
    {
        return parent;
    }

    public String getPathElem()
    {
        return pathElem;
    }

    public List<Node> getPath()
    {
        Node curNode = this;

        ArrayList<Node> path = new ArrayList<Node>();

        while ( curNode != null )
        {
            path.add( curNode );

            curNode = curNode.getParent();
        }

        Collections.reverse( path );

        return Collections.unmodifiableList( path );
    }

    public String getPathString()
    {
        List<Node> path = getPath();

        Iterator<Node> pathIter = path.iterator();

        // consume root to make assembly simpler
        pathIter.next();

        StringBuilder sb = new StringBuilder( PATH_SEPARATOR );

        if ( pathIter.hasNext() )
        {
            sb.append( pathIter.next().getPathElem() );
        }

        for ( ; pathIter.hasNext(); )
        {
            sb.append( PATH_SEPARATOR ).append( pathIter.next().getPathElem() );
        }

        return sb.toString();
    }

    // ==

    public static Node parseString( final NodeSpace space, final String string )
    {
        assert string != null : "String to parse cannot be null!";

        String pathString = string;

        while ( pathString.startsWith( PATH_SEPARATOR ) )
        {
            pathString = pathString.substring( PATH_SEPARATOR.length() );
        }

        String[] pathElems = pathString.split( PATH_SEPARATOR );

        Node curNode = space.getOrigin();

        for ( String elem : pathElems )
        {
            if ( elem.length() > 0 )
            {
                curNode = new Node( curNode, elem );
            }
        }

        return curNode;
    }

    // ==

    public String toString()
    {
        return getPathString();
    }
}
