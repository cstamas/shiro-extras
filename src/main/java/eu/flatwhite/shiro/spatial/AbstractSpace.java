package eu.flatwhite.shiro.spatial;

public abstract class AbstractSpace implements Space {

    @Override
    public Spatial project(Spatial spatial) {
	return null;
    }

    @Override
    public double distance(Spatial s1, Spatial s2) {
	if (isContaining(s1) == false) {
	    s1 = project(s1);
	}
	if (s1 != null && isContaining(s2) == false) {
	    s2 = project(s2);
	}
	if (s1 != null && s2 != null) {
	    return calculateDistance(s1, s2);
	}
	return Double.NaN;
    }

    protected abstract double calculateDistance(Spatial s1, Spatial s2);

}
