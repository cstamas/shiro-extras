package eu.flatwhite.shiro.spatial.inifinite;

import java.util.Arrays;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;

public class EuclideanSpace implements Space {
    private final Point origin;

    private final int dimensions;

    public EuclideanSpace(int dimensions) {
	this.dimensions = dimensions;
	double[] o = new double[dimensions];
	Arrays.fill(o, 0, dimensions, 0);
	this.origin = new Point(this, o);
    }

    public Point getOrigin() {
	return origin;
    }

    public boolean isContaining(Spatial spatial) {
	return spatial instanceof Point;
    }

    @Override
    public Spatial project(Spatial spatial) {
	if (spatial instanceof Point) {
	    Point p = (Point) spatial;
	    // The projected point coordinates
	    double[] projection = new double[dimensions];

	    // d holds the size of the smaller space
	    int d = Math.min(this.dimensions, p.getDimensions());

	    int i = 0;
	    // Project all "common" dimensions
	    for (; i < d; i++) {
		projection[i] = p.get(i);
	    }
	    // Any additional dimensions are set to this space's origin
	    for (; i < dimensions; i++) {
		projection[i] = getOrigin().get(i);
	    }
	    return new Point(this, dimensions);
	}
	return null;
    }

    public int getDimensions() {
	return this.dimensions;
    }

    public double distance(Spatial s1, Spatial s2) {
	if (isContaining(s1) == false) {
	    s1 = project(s1);
	}
	if (isContaining(s2) == false) {
	    s2 = project(s2);
	}
	if (s1 != null && s2 != null) {
	    Point p1 = (Point) s1;

	    Point p2 = (Point) s2;

	    double sum = 0.0;
	    for (int i = 0; i < dimensions; i++) {
		double d = p1.get(i) - p2.get(i);
		sum += d * d;
	    }
	    return Math.sqrt(sum);
	} else {
	    return Double.NaN;
	}
    }
}
