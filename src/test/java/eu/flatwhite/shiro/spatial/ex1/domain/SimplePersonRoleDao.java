package eu.flatwhite.shiro.spatial.ex1.domain;

import java.util.HashMap;

public class SimplePersonRoleDao implements PersonRoleDao {

    private final HashMap<String, String[]> roles = new HashMap<String, String[]>();

    @Override
    public String[] resolveRole(String roleName) {
	return roles.get(roleName);
    }

    @Override
    public void addRole(String roleName, String[] permissions) {
	roles.put(roleName, permissions);
    }

    @Override
    public void removeRole(String roleName) {
	roles.remove(roleName);
    }

}
