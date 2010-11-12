package eu.flatwhite.shiro.spatial.funnycorp;

public class PersonGenderSpace
    extends PersonSpace
{
    @Override
    protected double calculateDistance( Person p1, Person p2 )
    {
        return Math.abs( p1.getGender().ordinal() - p2.getGender().ordinal() );
    }
}
