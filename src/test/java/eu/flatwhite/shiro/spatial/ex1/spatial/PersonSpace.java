package eu.flatwhite.shiro.spatial.ex1.spatial;

import eu.flatwhite.shiro.spatial.AbstractSpace;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.ex1.domain.Person;
import eu.flatwhite.shiro.spatial.ex1.domain.Person.Gender;

/**
 * A person "space" that is actually not a space, since all distances are
 * Double.NaN. This class is just used as base for other spaces that defines how
 * to "measure" the persons.
 * 
 * @author cstamas
 */
public class PersonSpace extends AbstractSpace {
    // The origin of all Person is necessarily a woman
    private final SpatialPerson origin = new SpatialPerson(this, new Person( "", "", "Eve", Gender.FEMALE, 0, null));

    public Spatial getOrigin() {
	return origin;
    }

    public boolean isContaining(Spatial spatial) {
	return spatial instanceof SpatialPerson;
    }

    protected double calculateDistance(Spatial s1, Spatial s2) {
	return Double.NaN;
    }

}
