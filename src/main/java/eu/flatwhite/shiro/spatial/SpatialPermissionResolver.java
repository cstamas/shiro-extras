package eu.flatwhite.shiro.spatial;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.InvalidPermissionStringException;
import org.apache.shiro.authz.permission.PermissionResolver;

import eu.flatwhite.shiro.spatial.finite.NodeSpace;

/**
 * Resolves permission strings with the following format <pre>{space}:{spatial}:{touches}[:{inside}/{outside}]<pre> where
 * <ul>
 *   <li><pre>{space}</pre> defines the space the permission belongs to</li>
 *   <li><pre>{spatial}</pre> defines the spatial (point in the space) the permission belongs to</li>
 *   <li><pre>{touches}</pre> defines the permission for the {@link Relation#TOUCHES} relation</li>
 *   <li><pre>{inside}/{outside}</pre> defines the permission(s) for the {@link Relation#INSIDE} and {@link Relation#OUTSIDE} relation(s)</li>
 * </ul>
 * <p>
 * An example using a {@link NodeSpace}
 * <pre>
 * menu:/Edit/Delete:execute (allows 'execute' on /Edit/Delete)
 * menu:/Edit/Delete:execute:view (allows 'execute' on /Edit/Delete and 'view' on nodes INSIDE)
 * </pre>
 * <p>
 * This implementation relies on {@link SpaceResolver} to resolve the <pre>{space}</pre> part, a {@link SpatialResolver} to resolve the <pre>{spatial}</pre> part.
 */
public class SpatialPermissionResolver implements PermissionResolver {

  private final SpaceResolver spaceResolver;

  private final SpatialResolver spatialResolver;

  private final SpaceRelationProvider spaceRelationProvider;
  
  public SpatialPermissionResolver(Space space, SpatialResolver spatialResolver, RelationProvider relationProvider) {
    this(new SingleSpaceResolver(space), spatialResolver, new SingleSpaceRelationProvider(relationProvider));
  }

  public SpatialPermissionResolver(SpaceResolver spaceResolver, SpatialResolver spatialResolver, SpaceRelationProvider spaceRelationProvider) {
    assert spaceResolver != null : "spaceResolver cannot be null";
    assert spatialResolver != null : "spatialResolver cannot be null";
    assert spaceRelationProvider != null : "spaceRelationProvider cannot be null";
    
    this.spaceResolver = spaceResolver;
    this.spatialResolver = spatialResolver;
    this.spaceRelationProvider = spaceRelationProvider;
  }

  @Override
  public Permission resolvePermission(String permissionString) {
    String[] parts = permissionString.split(":");

    if(parts.length < 3) {
      throw new InvalidPermissionStringException("Expected at least 3 parts '{space}:{spatial}:{touches}'", permissionString);
    }

    Map<Relation, String> permissions = new HashMap<Relation, String>();

    Space space = getSpace(parts[0]);
    if(space == null) {
      throw new InvalidPermissionStringException("unknown space '" + parts[0] + "'", permissionString);
    }
    Spatial spatial = parseSpatial(space, parts[1]);
    permissions.put(Relation.TOUCHES, parts[2]);
    if(parts.length > 3) {
      String[] perms = parts[3].split("/");
      switch(perms.length) {
      case 2:
        permissions.put(Relation.OUTSIDE, perms[1]);
      case 1:
        permissions.put(Relation.INSIDE, perms[0]);
        break;
      default:
        throw new InvalidPermissionStringException("invalid inside/outside permissions", permissionString);
      }
    }

    return new SpatialPermission(spatial, spaceRelationProvider.getRelationProvider(space), permissions);
  }

  protected Space getSpace(String spaceString) {
    return this.spaceResolver.resolveSpace(spaceString);
  }

  protected Spatial parseSpatial(Space space, String spatialString) {
    return this.spatialResolver.parseSpatial(space, spatialString);
  }

}
