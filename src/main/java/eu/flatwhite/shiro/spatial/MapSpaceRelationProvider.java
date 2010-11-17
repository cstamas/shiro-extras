package eu.flatwhite.shiro.spatial;

import java.util.HashMap;
import java.util.Map;

public class MapSpaceRelationProvider implements SpaceRelationProvider {

  private final SpatialProjector projector;
  private final Map<Space, RelationProvider> spaceToProvider = new HashMap<Space, RelationProvider>();

  public MapSpaceRelationProvider(SpatialProjector projector, Map<Space, RelationProvider> nameToSpace) {
    this.projector = projector;
    this.spaceToProvider.putAll(nameToSpace);
  }
  public MapSpaceRelationProvider(Map<Space, RelationProvider> nameToSpace) {
    this(null, nameToSpace);
  }

  @Override
  public RelationProvider getRelationProvider(Space space) {
    return new ProjectingRelationProvider(projector, this.spaceToProvider.get(space));
  }

  public final static class Builder {
    
    private SpatialProjector projector;
    
    private final Map<Space, RelationProvider> spaceToProvider = new HashMap<Space, RelationProvider>();

    public static Builder newMap() {
      return new Builder();
    }
    
    public Builder projector(SpatialProjector projector) {
      this.projector = projector;
      return this;
    }

    public Builder add(Space space, RelationProvider resolver) {
      spaceToProvider.put(space, resolver);
      return this;
    }

    public MapSpaceRelationProvider build() {
      return new MapSpaceRelationProvider(projector, spaceToProvider);
    }

  }
}
