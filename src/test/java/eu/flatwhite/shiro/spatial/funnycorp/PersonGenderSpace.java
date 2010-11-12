package eu.flatwhite.shiro.spatial.funnycorp;

/**
 * A person space using peron's gender for distance. This is a finite space, actually having two distinct points
 * defined: MALE and FEMALE. The distances is either 1 (you are comparing two different genders) or 0 (you compare two
 * same genders).
 * 
 * @author cstamas
 */
public class PersonGenderSpace
    extends PersonSpace
{
    @Override
    protected double calculateDistance( Person p1, Person p2 )
    {
        return Math.abs( p1.getGender().ordinal() - p2.getGender().ordinal() );
    }
}
