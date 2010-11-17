package eu.flatwhite.shiro.spatial.infinite;

import junit.framework.TestCase;

import org.apache.shiro.authz.permission.InvalidPermissionStringException;

import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace;
import eu.flatwhite.shiro.spatial.inifinite.Point;
import eu.flatwhite.shiro.spatial.inifinite.PointResolver;

public class PointResolverTest extends TestCase {

  public void testResolve1D() {
    EuclideanSpace space = new EuclideanSpace(1);
    PointResolver pointResolver = new PointResolver();
    Spatial point = pointResolver.resolveSpatial(space, "1.0");
    assertEquals(space, point.getSpace());
    assertEquals(1.0, ((Point)point).get(0));
  }

  public void testResolve2D() {
    EuclideanSpace space = new EuclideanSpace(2);
    PointResolver pointResolver = new PointResolver();
    Spatial point = pointResolver.resolveSpatial(space, "1.0,2.0");
    assertEquals(space, point.getSpace());
    assertEquals(1.0, ((Point)point).get(0));
    assertEquals(2.0, ((Point)point).get(1));
  }
  
  public void testResolve3D() {
    EuclideanSpace space = new EuclideanSpace(3);
    PointResolver pointResolver = new PointResolver();
    Spatial point = pointResolver.resolveSpatial(space, "1.0,2.0,3.0");
    assertEquals(space, point.getSpace());
    assertEquals(1.0, ((Point)point).get(0));
    assertEquals(2.0, ((Point)point).get(1));
    assertEquals(3.0, ((Point)point).get(2));
  }

  public void testThrowsInvalidPermissionStringWhenNotANumber() {
    String invalidString = "1.0,NotANumber";
    PointResolver pointResolver = new PointResolver();
    try {
      pointResolver.resolveSpatial(new EuclideanSpace(2), invalidString);
      fail("Expected exception");
    } catch(InvalidPermissionStringException e) {
      assertEquals(invalidString, e.getPermissionString());
    }
  }
}
