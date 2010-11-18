package eu.flatwhite.shiro.spatial.inifinite;

import java.util.Arrays;

import eu.flatwhite.shiro.spatial.AbstractSpatial;

/**
 * A point in a {@link EuclideanSpace}.
 */
public class Point extends AbstractSpatial {

    private final double[] coordinates;

    private transient String toString;

    public Point(final EuclideanSpace space, final double... coordinates) {
	super(space);
	assert coordinates != null : "coordinates cannot be null";
	assert space.getDimensions() == coordinates.length : "invalid number of dimensions";
	this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
    }

    public double get(int i) {
	assert i >= 0 : "invalid coordinate index";
	assert i < coordinates.length : "invalid coordinate index";

	return coordinates[i];
    }

    public int getDimensions() {
	return coordinates.length;
    }

    // ==

    public String toString() {
	if (toString == null) {
	    StringBuilder sb = new StringBuilder();
	    sb.append('(');
	    for (int i = 0; i < coordinates.length; i++) {
		sb.append(coordinates[i]);
		if (i > 0)
		    sb.append(',');
	    }
	    toString = sb.append(')').toString();
	}
	return toString;
    }
}
