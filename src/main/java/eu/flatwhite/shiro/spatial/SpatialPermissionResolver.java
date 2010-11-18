package eu.flatwhite.shiro.spatial;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.InvalidPermissionStringException;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.PermissionResolverAware;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;

import eu.flatwhite.shiro.spatial.finite.NodeSpace;

/**
 * Resolves permission strings with the following format
 * 
 * <pre>{space}:{spatial}:{touches}[:{inside}/{outside}]</pre>
 * where
 * <ul>
 *   <li><code>{space}</code> defines the space the permission belongs to</li>
 *   <li><code>{spatial}</code> defines the spatial (point in the space) the permission belongs to</li>
 *   <li><code>{touches}</code> defines the permission for the {@link Relation#TOUCHES} relation</li>
 *   <li><code>{inside}/{outside}</code> defines the permission(s) for the {@link Relation#INSIDE} and {@link Relation#OUTSIDE} relation(s)</li>
 * </ul>
 * <p>
 * An example using a {@link NodeSpace}
 * <table>
 * <tr>
 *  <td>Permission String</td>
 *  <td>Meaning</td>
 * </tr>
 * <tr>
 *  <td><code>menu:/Edit/Delete:execute</code></td>
 *  <td>Allows 'execute' on /Edit/Delete</td>
 * </tr>
 * <tr>
 *  <td><code>menu:/Edit/Delete:execute:view</code></td>
 *  <td>Allows 'execute' on /Edit/Delete and 'view' on nodes INSIDE</td>
 * </tr>
 * </table>
 * <p>
 * This implementation relies on {@link SpaceResolver} to resolve the <code>{space}</code> part, a {@link SpatialResolver} to resolve the <code>{spatial}</code> 
 * part and a {@code PermissionResolver} to resolve the relation permissions (i.e.: {touches}, {inside} and {outside}). By default this {@code PermissionResolver} is a {@link WildcardPermissionResolver}.
 *
 * @author philippe.laflamme@gmail.com
 */
public class SpatialPermissionResolver implements PermissionResolver,
	PermissionResolverAware {

    private final SpaceResolver spaceResolver;

    private final SpatialResolver spatialResolver;

    private final SpaceRelationProvider spaceRelationProvider;

    private PermissionResolver relationPermissionResolver = new WildcardPermissionResolver();

    public SpatialPermissionResolver(Space space,
	    SpatialResolver spatialResolver, RelationProvider relationProvider) {
	this(new SingleSpaceResolver(space), spatialResolver,
		new SingleSpaceRelationProvider(relationProvider));
    }

    public SpatialPermissionResolver(SpaceResolver spaceResolver,
	    SpatialResolver spatialResolver,
	    SpaceRelationProvider spaceRelationProvider) {
	assert spaceResolver != null : "spaceResolver cannot be null";
	assert spatialResolver != null : "spatialResolver cannot be null";
	assert spaceRelationProvider != null : "spaceRelationProvider cannot be null";

	this.spaceResolver = spaceResolver;
	this.spatialResolver = spatialResolver;
	this.spaceRelationProvider = spaceRelationProvider;
    }

    @Override
    public void setPermissionResolver(
	    PermissionResolver relationPermissionResolver) {
	setRelationPermissionResolver(relationPermissionResolver);
    }

    public void setRelationPermissionResolver(
	    PermissionResolver relationPermissionResolver) {
	this.relationPermissionResolver = relationPermissionResolver;
    }

    @Override
    public Permission resolvePermission(String permissionString) {
	String[] parts = permissionString.split(":");

	if (parts.length < 3) {
	    throw new InvalidPermissionStringException(
		    "Expected at least 3 parts '{space}:{spatial}:{touches}'",
		    permissionString);
	}

	Map<Relation, Permission> permissions = new HashMap<Relation, Permission>();

	Space space = resolveSpace(parts[0]);
	if (space == null) {
	    throw new InvalidPermissionStringException("unknown space '"
		    + parts[0] + "'", permissionString);
	}
	Spatial spatial = resolveSpatial(space, parts[1]);

	resolveAndAddRelationPermission(permissions, Relation.TOUCHES, parts[2]);

	if (parts.length > 3) {
	    String[] perms = parts[3].split("/");
	    switch (perms.length) {
	    case 2:
		resolveAndAddRelationPermission(permissions, Relation.OUTSIDE,
			perms[1]);
	    case 1:
		resolveAndAddRelationPermission(permissions, Relation.INSIDE,
			perms[0]);
		break;
	    default:
		throw new InvalidPermissionStringException(
			"invalid inside/outside permissions", permissionString);
	    }
	}

	return new SpatialPermission(spatial,
		spaceRelationProvider.getRelationProvider(space), permissions);
    }

    protected Space resolveSpace(String spaceString) {
	return spaceResolver.resolveSpace(spaceString);
    }

    protected Spatial resolveSpatial(Space space, String spatialString) {
	return spatialResolver.resolveSpatial(space, spatialString);
    }

    protected Permission resolveRelationPermission(Relation relation,
	    String permissionString) {
	return relationPermissionResolver.resolvePermission(permissionString);
    }

    protected void resolveAndAddRelationPermission(
	    Map<Relation, Permission> permissions, Relation relation,
	    String permissionString) {
	Permission relationPermission = resolveRelationPermission(relation,
		permissionString);
	permissions.put(relation, relationPermission);
    }
}
