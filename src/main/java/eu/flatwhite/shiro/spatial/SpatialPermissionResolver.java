package eu.flatwhite.shiro.spatial;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;

public class SpatialPermissionResolver
    implements PermissionResolver
{
    private final SpatialResolver spatialResolver;

    private final RelationProviderResolver relationProviderResolver;

    private final PermissionResolver permissionResolver;

    public SpatialPermissionResolver( final SpatialResolver spatialResolver )
    {
        this( spatialResolver, new DefaultRelationProviderResolver(), new WildcardPermissionResolver() );
    }

    public SpatialPermissionResolver( final SpatialResolver spatialResolver,
                                      final RelationProviderResolver relationProviderResolver,
                                      final PermissionResolver permissionResolver )
    {
        this.spatialResolver = spatialResolver;

        this.relationProviderResolver = relationProviderResolver;

        this.permissionResolver = permissionResolver;
    }

    public SpatialResolver getSpatialResolver()
    {
        return spatialResolver;
    }

    public RelationProviderResolver getRelationProviderResolver()
    {
        return relationProviderResolver;
    }

    public PermissionResolver getPermissionResolver()
    {
        return permissionResolver;
    }

    public Permission resolvePermission( String spatialPermissionString )
    {
        // < = > !
        // examples:
        // jason:point:(coffe)  means  jason:point:=(coffee)
        // jason:sphere:i(coffe):t(coffe)
        // TODO Auto-generated method stub

        // ==
        
        final String spatialString = null;

        final String relationProviderString = null;

        final String permissionString = null;

        final Spatial spatial = getSpatialResolver().resolveSpatial( spatialString );

        final RelationProvider relationProvider =
            getRelationProviderResolver().resolveRelationProvider( spatial, relationProviderString );

        final Permission permission = getPermissionResolver().resolvePermission( permissionString );

        return new SpatialPermission( spatial, relationProvider, permission );
    }
}
