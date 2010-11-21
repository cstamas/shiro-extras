package eu.flatwhite.shiro.spatial.ex1.spatial;

import java.util.Collection;
import java.util.HashMap;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import eu.flatwhite.shiro.spatial.MapSpaceResolver;
import eu.flatwhite.shiro.spatial.MapSpatialResolver;
import eu.flatwhite.shiro.spatial.PointRelationProvider;
import eu.flatwhite.shiro.spatial.SingleSpaceRelationProvider;
import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.SpaceRelationProvider;
import eu.flatwhite.shiro.spatial.SpaceResolver;
import eu.flatwhite.shiro.spatial.SpatialPermission;
import eu.flatwhite.shiro.spatial.SpatialPermissionResolver;
import eu.flatwhite.shiro.spatial.SpatialResolver;
import eu.flatwhite.shiro.spatial.SphereRelationProvider;
import eu.flatwhite.shiro.spatial.ex1.domain.Person;
import eu.flatwhite.shiro.spatial.ex1.domain.PersonDao;
import eu.flatwhite.shiro.spatial.finite.EnumSpatialResolver;
import eu.flatwhite.shiro.spatial.inifinite.PointResolver;

public class SpatialPersonRealm extends AuthorizingRealm {

    private PersonDao personDao;

    private SpatialPermissionDao spatialPermissionDao;

    private PersonSpace personSpace;

    public SpatialPersonRealm() {
	setCachingEnabled(false);

	this.personSpace = new PersonSpace();

	PersonGenderSpace genderSpace = new PersonGenderSpace();
	PersonMeritSpace meritSpace = new PersonMeritSpace();
	HashMap<String, Space> spaces = new HashMap<String, Space>();
	spaces.put("gender", genderSpace);
	spaces.put("merit", meritSpace);

	SpaceResolver spaceResolver = new MapSpaceResolver(spaces);

	HashMap<Space, SpatialResolver> spatialResolvers = new HashMap<Space, SpatialResolver>();
	spatialResolvers.put(genderSpace, new EnumSpatialResolver(
		Person.Gender.class));
	spatialResolvers.put(meritSpace, new PointResolver());
	SpatialResolver spatialResolver = new MapSpatialResolver(
		spatialResolvers);

	SpaceRelationProvider spaceRelationProvider = new SingleSpaceRelationProvider(
		new SphereRelationProvider());

	SpatialPermissionResolver permissionResolver = new SpatialPermissionResolver(
		spaceResolver, spatialResolver, spaceRelationProvider);

	setPermissionResolver(permissionResolver);
    }

    @Override
    public boolean supports(AuthenticationToken t) {
	return false;
    }

    public PersonDao getPersonDao() {
	return personDao;
    }

    public void setPersonDao(PersonDao personDao) {
	this.personDao = personDao;
    }

    public SpatialPermissionDao getSpatialPermissionDao() {
	return spatialPermissionDao;
    }

    public void setSpatialPermissionDao(
	    SpatialPermissionDao spatialPermissionDao) {
	this.spatialPermissionDao = spatialPermissionDao;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
	    AuthenticationToken token) throws AuthenticationException {
	return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
	    PrincipalCollection principals) {

	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

	Person person = personDao
		.findByUsername((String) getAvailablePrincipal(principals));

	Collection<String> spatialPermissions = spatialPermissionDao
		.findAll(Person.class);

	if (spatialPermissions != null) {
	    for (String spatialPermission : spatialPermissions) {

		SpatialPermission global = (SpatialPermission) getPermissionResolver()
			.resolvePermission(spatialPermission);

		SpatialPermission personal = new SpatialPermission(global
			.getSpatial().getSpace()
			.project(new SpatialPerson(personSpace, person)),
			new PointRelationProvider(), new WildcardPermission(
				"foo"));

		Permission perm = global.getPermissionFor(personal);

		if (perm != null) {
		    info.addObjectPermission(perm);
		}
	    }
	}

	return info;
    }
}
