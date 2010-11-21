package eu.flatwhite.shiro.spatial.ex1;

import eu.flatwhite.shiro.spatial.ex1.domain.Person;
import eu.flatwhite.shiro.spatial.ex1.spatial.SpatialPermissionDao;

/**
 * Example1 "spatial" implementation: FunnyCorp (with one vending machine and
 * strange manager)
 * 
 * Solution: more intrusive to application (needs resource perms change), but is
 * simpler on Shiro side.
 * 
 * @author cstamas
 * 
 */
public class Example1SpatialTest extends Example1Base {

    protected SpatialPermissionDao spatialPermissionDao;

    protected void setUp() throws Exception {
	super.setUp();

	spatialPermissionDao = (SpatialPermissionDao) config.getBeans().get(
		"spatialPermissionDao");
    }

    @Override
    protected String getIniPath() {
	return "classpath:shiro-ex1-spatial.ini";
    }

    @Override
    protected void populateInitialRoles() {
	spatialPermissionDao.add(Person.class, "gender:FEMALE:coffee");
	spatialPermissionDao.add(Person.class, "gender:MALE:coke");
	spatialPermissionDao.add(Person.class, "merit:10:beer:beer");
    }

    @Override
    protected void applyManagerDecrete() {
	// nil
    }

}
