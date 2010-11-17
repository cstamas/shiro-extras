package eu.flatwhite.shiro.spatial.inifinite;

import java.util.Arrays;

import eu.flatwhite.shiro.spatial.AbstractSpatial;

public class Point extends AbstractSpatial {
    private final double[] coords;

    public Point(final EuclideanSpace space, final double... p) {
	super(space);
	assert p != null : "points cannot be null";
	assert space.getDimensions() == p.length : "invalid number of dimensions";
	this.coords = Arrays.copyOf(p, p.length);
    }

    public double get(int i) {
	return coords[i];
    }

    public int getDimensions() {
	return coords.length;
    }

    // ==

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append('(');
	for (int i = 0; i < coords.length; i++) {
	    sb.append(coords[i]);
	    if (i > 0)
		sb.append(',');
	}
	return sb.append(')').toString();
    }
}
