package eu.flatwhite.shiro.spatial;

public abstract class AbstractSpatial implements Spatial {
    private static final long serialVersionUID = 7653768756376754250L;

    private final Space space;

    public AbstractSpatial(final Space space) {
	assert space != null : "Space cannot be null!";
	assert space.isContaining(this);

	this.space = space;
    }

    public Space getSpace() {
	return space;
    }

    public double distance(Spatial spatial) {
	return getSpace().distance(this, spatial);
    }
}
