package eu.flatwhite.shiro.spatial.funnycorp;

public class PersonMeritSpace
    extends PersonSpace
{
    @Override
    protected double calculateDistance( Person p1, Person p2 )
    {
        return Math.abs( p1.getBadgeNo() - p2.getBadgeNo() );
    }
}
