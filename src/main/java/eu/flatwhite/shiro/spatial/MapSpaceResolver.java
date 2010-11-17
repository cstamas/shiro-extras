package eu.flatwhite.shiro.spatial;

import java.util.HashMap;
import java.util.Map;

/**
 * An immutable implementation of {@code SpaceResolver} that keeps a map of space names to space instances.
 * 
 * @author philippe.laflamme@gmail.com
 *
 */
public final class MapSpaceResolver implements SpaceResolver {

  private final Map<String, Space> nameToSpace = new HashMap<String, Space>();

  public MapSpaceResolver(Map<String, Space> nameToSpace) {
    this.nameToSpace.putAll(nameToSpace);
  }

  @Override
  public Space resolveSpace(String spaceString) {
    return nameToSpace.get(spaceString);
  }

  public final static class Builder {
    private final Map<String, Space> nameToSpace = new HashMap<String, Space>();

    public static Builder newSpaceMap() {
      return new Builder();
    }
    
    public Builder addSpace(String name, Space space) {
      nameToSpace.put(name, space);
      return this;
    }

    public MapSpaceResolver build() {
      return new MapSpaceResolver(nameToSpace);
    }
    
  }
}
