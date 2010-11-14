package eu.flatwhite.shiro.spatial.inifinite;

import eu.flatwhite.shiro.spatial.Space;

public class Point2d
    extends Point1d
{
    private static final long serialVersionUID = 7979396130649378335L;

    private final double y;

    public Point2d( final Space space, final double x, final double y )
    {
        super( space, x );

        this.y = y;
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
