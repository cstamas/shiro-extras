package eu.flatwhite.shiro.spatial.ex1.spatial;

import java.util.Collection;

public interface SpatialPermissionDao {

    Collection<String> findAll(Class<?> domainClass);

    void add(Class<?> domainClass, String permission);

    void remove(Class<?> domainClass, String permission);

}
