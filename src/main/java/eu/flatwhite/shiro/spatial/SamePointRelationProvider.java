package eu.flatwhite.shiro.spatial;

/**
 * "Same point" relation provider just checks is the distance between two spatial zero, hence, they are (in 2d for
 * example) same points. As points has no spatial extension, they may only related as TOUCH (two points overlays) or
 * UNRELATED (two points does not overlays).
 * 
 * @author cstamas
 */
public class SamePointRelationProvider
    extends AbstractRelationProvider
{
    public Relation relate( Spatial s1, Spatial s2 )
    {
        if ( s1.distance( s2 ) == 0.0 )
        {
            return Relation.TOUCHES;
        }
        else
        {
            return Relation.UNRELATED;
        }
    }
}
