package eu.flatwhite.shiro.spatial.ex1.domain;

/**
 * A simple person role DAO. This class would be part of Shiro integrator's EIS
 * implementation. This is "below" Shiro, and is just helping interfacing with
 * it.
 * 
 * @author cstamas
 */
public interface PersonRoleDao {

    String[] resolveRole(String roleName);

    void addRole(String roleName, String[] permissions);

    void removeRole(String roleName);

}
