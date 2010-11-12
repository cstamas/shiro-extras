package eu.flatwhite.shiro.spatial;

import java.io.Serializable;

public abstract class AbstractSpatial
    implements Spatial, Serializable
{
    private static final long serialVersionUID = 7653768756376754250L;

    private final Space space;

    public AbstractSpatial( final Space space )
    {
        assert space != null : "Space cannot be null!";

        this.space = space;
    }

    public Space getSpace()
    {
        return space;
    }

    public double distance( Spatial spatial )
    {
        return getSpace().distance( this, spatial );
    }
}
