package eu.flatwhite.shiro.spatial.inifinite;

import org.apache.shiro.authz.permission.InvalidPermissionStringException;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.SpatialResolver;

/**
 * Resolves {@code Point} instances from a {@code String}. This implementation parses a comma-separated list of {@code
 * Double}. It will parse at most 3 points.
 * 
 * @author philippe.laflamme@gmail.com
 */
public class PointResolver implements SpatialResolver {

  @Override
  public Spatial parseSpatial(Space space, String spatialString) {
    String[] points = spatialString.split(",");
    try {
      switch(points.length) {
      case 1:
        return new Point1d(space, Double.parseDouble(points[0]));
      case 2:
        return new Point2d(space, Double.parseDouble(points[0]), Double.parseDouble(points[1]));
      case 3:
        return new Point3d(space, Double.parseDouble(points[0]), Double.parseDouble(points[1]), Double.parseDouble(points[2]));
      default:
        throw new InvalidPermissionStringException("too many points", spatialString);
      }
    } catch(NumberFormatException e) {
      throw new InvalidPermissionStringException("cannot parse point", spatialString);
    }
  }

}
