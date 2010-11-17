package eu.flatwhite.shiro.spatial.inifinite;

import org.apache.shiro.authz.permission.InvalidPermissionStringException;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.SpatialResolver;

/**
 * Resolves {@code Point} instances from a {@code String}. This implementation parses a comma-separated list of {@code
 * Double}.
 * 
 * @author philippe.laflamme@gmail.com
 */
public class PointResolver implements SpatialResolver {

  @Override
  public Spatial resolveSpatial(Space space, String spatialString) {
    String[] points = spatialString.split(",");
    EuclideanSpace es = (EuclideanSpace)space;
    if(es.getDimensions() != points.length) throw new InvalidPermissionStringException("invalid number of points", spatialString);
    double[] coords = new double[points.length];
    for(int i = 0; i < points.length; i++) {
      try {
        coords[i] = Double.parseDouble(points[i]);
      } catch(NumberFormatException e) {
        throw new InvalidPermissionStringException("cannot parse point", spatialString);
      }
    }
    return new Point(es, coords);
  }

}
