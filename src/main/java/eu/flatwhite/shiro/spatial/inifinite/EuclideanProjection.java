package eu.flatwhite.shiro.spatial.inifinite;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.SpatialProjector;

public class EuclideanProjection implements SpatialProjector {

  @Override
  public Spatial project(Spatial spatial, Space space) {
    // identity
    if(space == spatial.getSpace()) return spatial;

    if(space instanceof EuclideanSpace1d) {
      // Project to a 1d space
      Space otherSpace = spatial.getSpace();
      if(otherSpace instanceof EuclideanSpace2d) {
        Point2d point2d = (Point2d)spatial;
        return new Point1d(space, point2d.getX());
      } else
      if(otherSpace instanceof EuclideanSpace3d) {
        Point3d point3d = (Point3d)spatial;
        return new Point1d(space, point3d.getX());
      }
    }
    if(space instanceof EuclideanSpace2d) {
      // Project to a 2d space
      Space otherSpace = spatial.getSpace();
      if(otherSpace instanceof EuclideanSpace3d) {
        Point3d point3d = (Point3d)spatial;
        return new Point2d(space, point3d.getX(), point3d.getY());
      }
    }
    return null;
  }

}
