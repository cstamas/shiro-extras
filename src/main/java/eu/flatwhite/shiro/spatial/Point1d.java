package eu.flatwhite.shiro.spatial;

public class Point1d
    extends AbstractSpatial
{
    private final double x;

    public Point1d( final Space space, final double x )
    {
        super( space );

        this.x = x;
    }

    public double getX()
    {
        return x;
    }

    // ==

    public String toString()
    {
        return "(" + getX() + ")";
    }
}
