package eu.flatwhite.shiro.spatial.finite;

import org.apache.shiro.authz.permission.InvalidPermissionStringException;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.SpatialResolver;

/**
 * Resolves {@code EnumSpatial} instances by comparing the enum constant's name
 * against the permission string. This implementation is case insensitive.
 * 
 * @author philippe.laflamme@gmail.com
 */
public class EnumSpatialResolver implements SpatialResolver {

    private final Class<? extends Enum<?>> enumeration;

    public EnumSpatialResolver(Class<? extends Enum<?>> enumeration) {
	this.enumeration = enumeration;
    }

    @Override
    public Spatial resolveSpatial(Space space, String spatialString) {
	for (Enum<?> e : enumeration.getEnumConstants()) {
	    if (e.toString().equalsIgnoreCase(spatialString)) {
		return new EnumSpatial(space, e);
	    }
	}
	throw new InvalidPermissionStringException("no such enum",
		spatialString);
    }

}
