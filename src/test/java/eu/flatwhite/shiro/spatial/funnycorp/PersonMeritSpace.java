package eu.flatwhite.shiro.spatial.funnycorp;

/**
 * Person space that uses person's merit for distance. This is a real euclidean space.
 * 
 * @author cstamas
 */
public class PersonMeritSpace
    extends PersonSpace
{
    @Override
    protected double calculateDistance( Person p1, Person p2 )
    {
        return Math.abs( p1.getBadgeNo() - p2.getBadgeNo() );
    }
}
