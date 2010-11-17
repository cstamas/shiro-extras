package eu.flatwhite.shiro.spatial.finite;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.SpatialResolver;

/**
 * Resolves {@code Node} instances from a {@code String}. This implementation delegates to {@link Node#parseString(NodeSpace, String)}.
 * 
 * @author philippe.laflamme@gmail.com
 */
public class NodeResolver implements SpatialResolver {

  @Override
  public Spatial parseSpatial(Space space, String spatialString) {
    return Node.parseString((NodeSpace) space, spatialString);
  }

}
