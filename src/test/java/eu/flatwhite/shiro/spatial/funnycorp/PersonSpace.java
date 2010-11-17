package eu.flatwhite.shiro.spatial.funnycorp;

import eu.flatwhite.shiro.spatial.AbstractSpace;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.funnycorp.Person.Gender;

/**
 * A person "space" that is actually not a space, since all distances are Double.NaN. This class is just used as base
 * for other spaces that defines how to "measure" the persons.
 * 
 * @author cstamas
 */
public class PersonSpace
    extends AbstractSpace
{
    private final Person origin = new Person( this, "Adam", Gender.MALE, 0 );

    public Spatial getOrigin()
    {
        return origin;
    }

    public boolean isContaining( Spatial spatial )
    {
        return spatial instanceof Person;
    }

    @Override
    protected double calculateDistance(Spatial s1, Spatial s2) {
      return calculateDistance((Person)s1, (Person)s2);
    }
    
    protected double calculateDistance( Person p1, Person p2 )
    {
        return Double.NaN;
    }
}
