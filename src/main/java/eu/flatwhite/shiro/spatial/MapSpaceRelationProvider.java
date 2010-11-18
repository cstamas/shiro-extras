package eu.flatwhite.shiro.spatial;

import java.util.HashMap;
import java.util.Map;

/**
 * An immutable in-memory map implementation of {@code SpaceRelationProvider}. This class
 * uses a {@code Map} to lookup {@code RelationProvider} instances. Note that
 * the key in the {@code Map} is the {@code Space} instance, as such it is
 * required they properly implement {@code equals} and {@code hashcode} methods.
 * 
 * @author philippe.laflamme@gmail.com
 * 
 */
public class MapSpaceRelationProvider implements SpaceRelationProvider {

    private final Map<Space, RelationProvider> spaceToProvider = new HashMap<Space, RelationProvider>();

    public MapSpaceRelationProvider(Map<Space, RelationProvider> nameToSpace) {
	this.spaceToProvider.putAll(nameToSpace);
    }

    @Override
    public RelationProvider getRelationProvider(Space space) {
	return this.spaceToProvider.get(space);
    }

    /**
     * Builder pattern for {@code MapSpaceRelationProvider}.
     * 
     * @author philippe.laflamme@gmail.com
     */
    public final static class Builder {

	private final Map<Space, RelationProvider> spaceToProvider = new HashMap<Space, RelationProvider>();

	public static Builder newMap() {
	    return new Builder();
	}

	public Builder add(Space space, RelationProvider resolver) {
	    spaceToProvider.put(space, resolver);
	    return this;
	}

	public MapSpaceRelationProvider build() {
	    return new MapSpaceRelationProvider(spaceToProvider);
	}

    }
}
