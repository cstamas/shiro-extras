package eu.flatwhite.shiro.spatial.ex1.spatial;

import eu.flatwhite.shiro.spatial.AbstractSpatial;
import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.ex1.domain.Person;
import eu.flatwhite.shiro.spatial.ex1.domain.Person.Gender;

/**
 * A simple person abstraction.
 * 
 * @author cstamas
 */
public class SpatialPerson extends AbstractSpatial {
    private static final long serialVersionUID = 6259091445857892015L;

    private final Person person;

    public SpatialPerson(final Space space, final Person person) {
	super(space);

	this.person = person;
    }

    public Person getPerson() {
	return person;
    }

    public Gender getGender() {
	return person.getGender();
    }

    public int getBadgeNo() {
	return person.getBadgeNo();
    }
}
