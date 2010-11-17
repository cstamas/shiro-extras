package eu.flatwhite.shiro.spatial;

/**
 * Resolves a {@link Spatial} instance from a string.
 * 
 * @see {@link SpatialPermissionResolver}
 * @author philippe.laflamme@gmail.com
 */
public interface SpatialResolver {

    public Spatial resolveSpatial(Space space, String spatialString);
}
