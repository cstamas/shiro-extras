package eu.flatwhite.shiro.spatial.finite;

import eu.flatwhite.shiro.spatial.AbstractSpace;
import eu.flatwhite.shiro.spatial.Spatial;

/**
 * The finite space of a java {@code Enum}. Contains all the constants of an
 * enum class.
 * 
 * @author philippe.laflamme@gmail.com
 */
public class EnumeratedSpace extends AbstractSpace {

    private final Class<? extends Enum<?>> enumeration;

    private final int origin;

    public EnumeratedSpace(Class<? extends Enum<?>> enumeration) {
	assert enumeration != null : "enumeration cannot be null";

	this.enumeration = enumeration;
	int min = Integer.MAX_VALUE;
	for (Enum<?> e : enumeration.getEnumConstants()) {
	    min = Math.min(min, e.ordinal());
	}
	this.origin = min;
    }

    @Override
    public Spatial getOrigin() {
	return new EnumSpatial(this, origin);
    }

    @Override
    public boolean isContaining(Spatial spatial) {
	// FIXME: we should also test that the spaces are equivalent, but
	// spatial.getSpace() may return null when assertions are enabled (see
	// AbstractSpatial)
	return spatial instanceof EnumSpatial /*
					       * && this.enumeration.equals(((
					       * EnumeratedSpace)
					       * spatial.getSpace
					       * ()).getEnumClass())
					       */;
    }

    @Override
    protected double calculateDistance(Spatial s1, Spatial s2) {
	return Math.abs(((EnumSpatial) s1).getOrdinal()
		- ((EnumSpatial) s2).getOrdinal());
    }

    protected Object getEnumClass() {
	return enumeration;
    }
}
