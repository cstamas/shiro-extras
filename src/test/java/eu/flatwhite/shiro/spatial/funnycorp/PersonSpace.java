package eu.flatwhite.shiro.spatial.funnycorp;

import eu.flatwhite.shiro.spatial.AbstractSpace;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.funnycorp.Person.Gender;

/**
 * A person "space" that is actually not a space, since all distances are
 * Double.NaN. This class is just used as base for other spaces that defines how
 * to "measure" the persons.
 * 
 * @author cstamas
 */
public class PersonSpace extends AbstractSpace {
    // The origin of all Person is necessarily a woman
    private final Person origin = new Person(this, "Eve", Gender.FEMALE, 0);

    public Spatial getOrigin() {
	return origin;
    }

    public boolean isContaining(Spatial spatial) {
	return spatial instanceof Person;
    }

    protected double calculateDistance(Spatial s1, Spatial s2) {
	return Double.NaN;
    }

}
