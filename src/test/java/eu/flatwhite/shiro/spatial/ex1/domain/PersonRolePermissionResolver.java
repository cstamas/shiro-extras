package eu.flatwhite.shiro.spatial.ex1.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class PersonRolePermissionResolver implements RolePermissionResolver {

    private PersonRoleDao personRoleDao;

    public PersonRoleDao getPersonRoleDao() {
	return personRoleDao;
    }

    public void setPersonRoleDao(PersonRoleDao personRoleDao) {
	this.personRoleDao = personRoleDao;
    }

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
	String[] permStrings = personRoleDao.resolveRole(roleString);

	if (permStrings == null || personRoleDao == null) {
	    return Collections.emptySet();
	}

	Set<Permission> result = new HashSet<Permission>(permStrings.length);

	for (String permString : permStrings) {
	    result.add(new WildcardPermission(permString));
	}

	return result;
    }

}
