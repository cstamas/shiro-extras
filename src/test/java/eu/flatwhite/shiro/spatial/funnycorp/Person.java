package eu.flatwhite.shiro.spatial.funnycorp;

import eu.flatwhite.shiro.spatial.AbstractSpatial;
import eu.flatwhite.shiro.spatial.Space;

/**
 * A simple person abstraction.
 * 
 * @author cstamas
 */
public class Person
    extends AbstractSpatial
{
    private static final long serialVersionUID = 6259091445857892015L;

    public enum Gender
    {
        FEMALE, MALE
    };

    private final String name;

    private final Gender gender;

    private final int badgeNo;

    public Person( final Space space, final String name, final Gender gender, final int badgeNo )
    {
        super( space );

        this.name = name;

        this.gender = gender;

        this.badgeNo = badgeNo;
    }

    public String getName()
    {
        return name;
    }

    public Gender getGender()
    {
        return gender;
    }

    public int getBadgeNo()
    {
        return badgeNo;
    }
}
