package eu.flatwhite.shiro.spatial;

public class Point3d
    extends Point2d
{
    private static final long serialVersionUID = -3589143537868501810L;

    private final double z;

    public Point3d( final Space space, final double x, final double y, final double z )
    {
        super( space, x, y );

        this.z = z;
    }

    public double getZ()
    {
        return z;
    }

    // ==

    public String toString()
    {
        return "(" + getX() + "," + getY() + "," + getZ() + ")";
    }
}
