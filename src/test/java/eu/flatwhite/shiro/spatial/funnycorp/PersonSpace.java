package eu.flatwhite.shiro.spatial.funnycorp;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.funnycorp.Person.Gender;

public class PersonSpace
    implements Space
{
    public Spatial getOrigin()
    {
        return new Person( this, "Adam", Gender.MALE, 0 );
    }

    public boolean isContaining( Spatial spatial )
    {
        return spatial instanceof Person;
    }

    public double distance( Spatial s1, Spatial s2 )
    {
        if ( isContaining( s1 ) && isContaining( s2 ) )
        {
            return calculateDistance( (Person) s1, (Person) s2 );
        }
        else
        {
            return Double.NaN;
        }
    }

    protected double calculateDistance( Person p1, Person p2 )
    {
        return Double.NaN;
    }
}
