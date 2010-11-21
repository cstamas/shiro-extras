package eu.flatwhite.shiro.spatial.ex1.spatial;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SimpleSpatialPermissionDao implements SpatialPermissionDao {

    private final HashMap<Class<?>, HashSet<String>> spatialPermissions = new HashMap<Class<?>, HashSet<String>>();

    @Override
    public Collection<String> findAll(Class<?> domainClass) {
	Collection<String> result = spatialPermissions.get(domainClass);

	if (result != null) {
	    return Collections.unmodifiableCollection(result);
	} else {
	    return Collections.emptyList();
	}
    }

    @Override
    public void add(Class<?> domainClass, String permission) {
	if (!spatialPermissions.containsKey(domainClass)) {
	    spatialPermissions.put(domainClass, new HashSet<String>());
	}

	spatialPermissions.get(domainClass).add(permission);
    }

    @Override
    public void remove(Class<?> domainClass, String permission) {
	if (spatialPermissions.containsKey(domainClass)) {
	    spatialPermissions.get(domainClass).remove(permission);

	    if (spatialPermissions.get(domainClass).size() == 0) {
		spatialPermissions.remove(domainClass);
	    }
	}
    }

}
