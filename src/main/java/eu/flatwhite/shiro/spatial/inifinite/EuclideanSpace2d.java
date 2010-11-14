package eu.flatwhite.shiro.spatial.inifinite;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;

public class EuclideanSpace2d
    implements Space
{
    public Point2d getOrigin()
    {
        return new Point2d( this, 0, 0 );
    }

    public boolean isContaining( Spatial spatial )
    {
        return spatial instanceof Point2d;
    }

    public double distance( Spatial s1, Spatial s2 )
    {
        if ( isContaining( s1 ) && isContaining( s2 ) )
        {
            Point2d p1 = (Point2d) s1;

            Point2d p2 = (Point2d) s2;

            return Math.sqrt( Math.pow( ( p1.getX() - p2.getX() ), 2 ) + Math.pow( ( p1.getY() - p2.getY() ), 2 ) );
        }
        else
        {
            return Double.NaN;
        }
    }
}
