package eu.flatwhite.shiro.spatial;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.Permission;

public class SpatialPermission
    implements Permission, Serializable
{
    private static final long serialVersionUID = 3000350723688688027L;

    private final Spatial spatial;

    private final RelationProvider relationProvider;

    private final Map<Relation, Permission> permissions;

    private SpatialPermission( final Spatial spatial, final RelationProvider relationProvider )
    {
        assert spatial != null : "Spatial 'null' cannot be passed to constructor!";
        assert relationProvider != null : "Relation provider 'null' cannot be passed to constructor!";

        this.spatial = spatial;

        this.relationProvider = relationProvider;

        this.permissions = new HashMap<Relation, Permission>( Relation.values().length );
    }

    public SpatialPermission( final Spatial spatial, final RelationProvider relationProvider,
                              final Permission permission )
    {
        this( spatial, relationProvider );

        assert permission != null : "Permission cannot be null!";

        this.permissions.put( Relation.TOUCHES, permission );
    }

    public SpatialPermission( final Spatial spatial, final RelationProvider relationProvider,
                              final Map<Relation, Permission> permissions )
    {
        this( spatial, relationProvider );

        assert permissions != null : "Permissions map cannot be null!";
        assert permissions.containsKey( Relation.TOUCHES ) : "Permission for Touches has to be given!";

        this.permissions.putAll( permissions );
    }

    public Spatial getSpatial()
    {
        return spatial;
    }

    public RelationProvider getRelationProvider()
    {
        return relationProvider;
    }

    public Map<Relation, Permission> getPermissions()
    {
        return permissions;
    }

    public boolean implies( Permission p )
    {
        // By default only supports comparisons with other WildcardPermissions, oops, SpatialPermissions
        if ( !( p instanceof SpatialPermission ) )
        {
            return false;
        }

        SpatialPermission wp = (SpatialPermission) p;

        Permission myPerm = getPermission( wp.getSpatial() );

        if ( myPerm != null )
        {
            return myPerm.implies( wp.getPermission(getSpatial(), Relation.TOUCHES));
        }
        else
        {
            return false;
        }
    }

    // ==

    protected Permission getPermission( Spatial spatial )
    {
        Relation relation = getRelationProvider().getRelation( getSpatial(), spatial );

        return getPermission(spatial, relation);
    }
    
    protected Permission getPermission( Spatial spatial, Relation relation )
    {
       return getPermissions().get(relation);
    }

}
