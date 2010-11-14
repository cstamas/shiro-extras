package eu.flatwhite.shiro.spatial.inifinite;

import eu.flatwhite.shiro.spatial.Space;
import eu.flatwhite.shiro.spatial.Spatial;

public class EuclideanSpace1d
    implements Space
{
    public Point1d getOrigin()
    {
        return new Point1d( this, 0 );
    }

    public boolean isContaining( Spatial spatial )
    {
        return spatial instanceof Point1d;
    }

    public double distance( Spatial s1, Spatial s2 )
    {
        if ( isContaining( s1 ) && isContaining( s2 ) )
        {
            Point1d p1 = (Point1d) s1;

            Point1d p2 = (Point1d) s2;

            return Math.sqrt( Math.pow( ( p1.getX() - p2.getX() ), 2 ) );
        }
        else
        {
            return Double.NaN;
        }
    }
}
