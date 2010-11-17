package eu.flatwhite.shiro.spatial.funnycorp;

import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.finite.EnumSpatial;
import eu.flatwhite.shiro.spatial.finite.EnumeratedSpace;
import eu.flatwhite.shiro.spatial.funnycorp.Person.Gender;

/**
 * A person space using peron's gender for distance. This is a finite space,
 * actually having two distinct points defined: MALE and FEMALE. The distances
 * is either 1 (you are comparing two different genders) or 0 (you compare two
 * same genders).
 * 
 * @author cstamas
 */
public class PersonGenderSpace extends EnumeratedSpace {
    public PersonGenderSpace() {
	super(Gender.class);
    }

    @Override
    public Spatial project(Spatial spatial) {
	if (spatial instanceof Person) {
	    return new EnumSpatial(this, ((Person) spatial).getGender());
	}
	return null;
    }
}
