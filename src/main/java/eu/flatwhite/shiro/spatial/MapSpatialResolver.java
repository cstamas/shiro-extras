package eu.flatwhite.shiro.spatial;

import java.util.HashMap;
import java.util.Map;

/**
 * An immutable implementation of {@code SpatialResolver} that keeps a map of
 * {@code Space} instances to {@code SpatialResolver}.
 * 
 * @author philippe.laflamme@gmail.com
 */
public final class MapSpatialResolver implements SpatialResolver {

    private final Map<Space, SpatialResolver> spaceToResolver = new HashMap<Space, SpatialResolver>();

    public MapSpatialResolver(Map<Space, SpatialResolver> nameToSpace) {
	this.spaceToResolver.putAll(nameToSpace);
    }

    @Override
    public Spatial resolveSpatial(Space space, String spatialString) {
	return spaceToResolver.get(space).resolveSpatial(space, spatialString);
    }

    /**
     * Builder pattern for {@code MapSpatialResolver}
     * 
     * @author philippe.laflamme@gmail.com
     */
    public final static class Builder {
	private final Map<Space, SpatialResolver> spaceToResolver = new HashMap<Space, SpatialResolver>();

	public static Builder newMap() {
	    return new Builder();
	}

	public Builder add(Space space, SpatialResolver resolver) {
	    spaceToResolver.put(space, resolver);
	    return this;
	}

	public MapSpatialResolver build() {
	    return new MapSpatialResolver(spaceToResolver);
	}

    }
}
