package eu.flatwhite.shiro.spatial;

/**
 * Sphere relation provider uses space origin of the supplied spatial together with each provided spatial to define a
 * sphere (in 3d, circle in 2d, hyper-shere in 4d, etc), and it just checks for the relation of two spheres: if s2 is
 * "smaller", and is hence contained in bigger s1 sphere, it is INSIDE. If the two spheres has equal radius, they
 * TOUCHES, otherwise OUTSIDE is returned.
 * 
 * @author cstamas
 */
public class SphereRelationProvider
    implements RelationProvider
{
    public Relation getRelation( final Spatial s1, final Spatial s2 )
    {
        // are they "compatible" at all? (their spaces mutually contains themselves)
        if ( s1.getSpace().isContaining( s2 ) && s2.getSpace().isContaining( s1 ) )
        {
            final double d1 = s1.distance( s1.getSpace().getOrigin() );

            final double d2 = s2.distance( s2.getSpace().getOrigin() );

            Relation relation;

            if ( d1 < d2 )
            {
                // is outside
                relation = Relation.OUTSIDE;
            }
            else if ( d1 == d2 )
            {
                // touches, or better, "is on the surface" (if we use 3d analogy)
                relation = Relation.TOUCHES;
            }
            else
            {
                // is inside
                relation = Relation.INSIDE;
            }

            return relation;
        }
        else
        {
            return Relation.UNRELATED;
        }
    }
}
