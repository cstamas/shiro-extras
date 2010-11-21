package eu.flatwhite.shiro.spatial.ex1.spatial;

import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace;
import eu.flatwhite.shiro.spatial.inifinite.Point;

/**
 * Person space that uses person's merit for distance. This is a real euclidean
 * space.
 * 
 * @author cstamas
 */
public class PersonMeritSpace extends EuclideanSpace {

    public PersonMeritSpace() {
	super(1);
    }

    @Override
    public Spatial project(Spatial spatial) {
	// Project a person's badgeNo onto our single dimension
	if (spatial instanceof SpatialPerson) {
	    return new Point(this, ((SpatialPerson) spatial).getBadgeNo());
	}
	return null;
    }
}
