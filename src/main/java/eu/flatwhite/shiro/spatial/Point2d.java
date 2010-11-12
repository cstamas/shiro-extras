package eu.flatwhite.shiro.spatial;

public class Point2d
    extends AbstractSpatial
{
    private static final long serialVersionUID = 7979396130649378335L;

    private final double x;

    private final double y;

    public Point2d( final Space space, final double x, final double y )
    {
        super( space );

        this.x = x;

        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    // ==

    public String toString()
    {
        return "(" + getX() + "," + getY() + ")";
    }
}
