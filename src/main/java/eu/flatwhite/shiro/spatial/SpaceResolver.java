package eu.flatwhite.shiro.spatial;

/**
 * Resolves a {@link Space} instance when parsing a permission string. 
 * @see {@link SpatialPermissionResolver}
 * @author philippe.laflamme@gmail.com
 */
public interface SpaceResolver {

  public Space resolveSpace(String spaceString);

}
