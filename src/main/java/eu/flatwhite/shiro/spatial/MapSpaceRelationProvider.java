package eu.flatwhite.shiro.spatial;

import java.util.HashMap;
import java.util.Map;

public class MapSpaceRelationProvider implements SpaceRelationProvider {

    private final Map<Space, RelationProvider> spaceToProvider = new HashMap<Space, RelationProvider>();

    public MapSpaceRelationProvider(Map<Space, RelationProvider> nameToSpace) {
	this.spaceToProvider.putAll(nameToSpace);
    }

    @Override
    public RelationProvider getRelationProvider(Space space) {
	return this.spaceToProvider.get(space);
    }

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
