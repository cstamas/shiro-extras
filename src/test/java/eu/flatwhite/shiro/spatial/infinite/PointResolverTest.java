package eu.flatwhite.shiro.spatial.infinite;

import org.apache.shiro.authz.permission.InvalidPermissionStringException;

import junit.framework.TestCase;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace1d;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace2d;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace3d;
import eu.flatwhite.shiro.spatial.inifinite.Point1d;
import eu.flatwhite.shiro.spatial.inifinite.Point2d;
import eu.flatwhite.shiro.spatial.inifinite.Point3d;
import eu.flatwhite.shiro.spatial.inifinite.PointResolver;

public class PointResolverTest extends TestCase {

  public void testResolve1D() {
    EuclideanSpace1d space = new EuclideanSpace1d();
    PointResolver pointResolver = new PointResolver();
    Spatial point1d = pointResolver.parseSpatial(space, "1.0");
    assertEquals(space, point1d.getSpace());
    assertEquals(1.0, ((Point1d)point1d).getX());
  }

  public void testResolve2D() {
    EuclideanSpace2d space = new EuclideanSpace2d();
    PointResolver pointResolver = new PointResolver();
    Spatial point2d = pointResolver.parseSpatial(space, "1.0,2.0");
    assertEquals(space, point2d.getSpace());
    assertEquals(1.0, ((Point2d)point2d).getX());
    assertEquals(2.0, ((Point2d)point2d).getY());
  }
  
  public void testResolve3D() {
    EuclideanSpace3d space = new EuclideanSpace3d();
    PointResolver pointResolver = new PointResolver();
    Spatial point3d = pointResolver.parseSpatial(space, "1.0,2.0,3.0");
    assertEquals(space, point3d.getSpace());
    assertEquals(1.0, ((Point3d)point3d).getX());
    assertEquals(2.0, ((Point3d)point3d).getY());
    assertEquals(3.0, ((Point3d)point3d).getZ());
  }

  public void testThrowsInvalidPermissionStringWhenNotANumber() {
    String invalidString = "1.0,NotANumber";
    PointResolver pointResolver = new PointResolver();
    try {
      pointResolver.parseSpatial(new EuclideanSpace2d(), invalidString);
      fail("Expected exception");
    } catch(InvalidPermissionStringException e) {
      assertEquals(invalidString, e.getPermissionString());
    }
  }
}
