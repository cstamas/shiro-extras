package eu.flatwhite.shiro.spatial.ex1.domain;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class PersonRealm extends AuthorizingRealm {

    private PersonDao personDao;

    private PersonRoleDao personRoleDao;

    public PersonRealm() {
	setAuthenticationTokenClass(UsernamePasswordToken.class);
	setCachingEnabled(false);
    }

    public PersonDao getPersonDao() {
	return personDao;
    }

    public void setPersonDao(PersonDao personDao) {
	this.personDao = personDao;
    }

    public PersonRoleDao getPersonRoleDao() {
	return personRoleDao;
    }

    public void setPersonRoleDao(PersonRoleDao personRoleDao) {
	this.personRoleDao = personRoleDao;
	setRolePermissionResolver(new PersonRolePermissionResolver(
		personRoleDao));
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
	    AuthenticationToken token) throws AuthenticationException {
	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
	Person person = personDao.findByUsername(upToken.getUsername());
	if (person != null) {
	    return new SimpleAuthenticationInfo(person.getUsername(),
		    person.getPassword(), getName());
	} else {
	    return null;
	}
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
	    PrincipalCollection principals) {
	String username = (String) getAvailablePrincipal(principals);

	Person person = personDao.findByUsername(username);
	if (person != null) {
	    return new SimpleAuthorizationInfo(person.getRoles());
	} else {
	    return null;
	}
    }
}
