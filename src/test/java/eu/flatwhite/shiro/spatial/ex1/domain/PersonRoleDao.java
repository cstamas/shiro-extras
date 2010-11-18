package eu.flatwhite.shiro.spatial.ex1.domain;

public interface PersonRoleDao {

    String[] resolveRole(String roleName);

    void addRole(String roleName, String[] permissions);

    void removeRole(String roleName);

}
