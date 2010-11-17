package eu.flatwhite.shiro.spatial.infinite;

import java.util.Random;

import junit.framework.TestCase;

import org.apache.shiro.authz.Permission;

import eu.flatwhite.shiro.spatial.SpatialPermissionResolver;
import eu.flatwhite.shiro.spatial.SphereRelationProvider;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace;
import eu.flatwhite.shiro.spatial.inifinite.PointResolver;

public class EuclideanSpacePermissionResolverTest extends TestCase {

    public void test1D() {
	SpatialPermissionResolver permissionResolver = new SpatialPermissionResolver(
		new EuclideanSpace(1), new PointResolver(),
		new SphereRelationProvider());

	// Allow anything <= 10.0 to be added
	Permission addThingsBelowTen = permissionResolver
		.resolvePermission("onedee:10:add:add");

	Random r = new Random();
	for (int i = 0; i < 100; i++) {
	    Double belowOrEqualToTen = r.nextDouble() * 10;
	    Permission addSomethingBelowTen = permissionResolver
		    .resolvePermission("onedee:" + belowOrEqualToTen + ":add");
	    super.assertTrue(addThingsBelowTen.implies(addSomethingBelowTen));
	}

    }

}
