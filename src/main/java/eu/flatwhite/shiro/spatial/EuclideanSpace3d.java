package eu.flatwhite.shiro.spatial;

public class EuclideanSpace3d
    implements Space
{
    public Point3d getOrigin()
    {
        return new Point3d( this, 0, 0, 0 );
    }

    public boolean isContaining( Spatial spatial )
    {
        return spatial instanceof Point3d;
    }

    public double distance( Spatial s1, Spatial s2 )
    {
        if ( isContaining( s1 ) && isContaining( s2 ) )
        {
            Point3d p1 = (Point3d) s1;

            Point3d p2 = (Point3d) s2;

            return Math.sqrt( Math.pow( ( p1.getX() - p2.getX() ), 2 ) + Math.pow( ( p1.getY() - p2.getY() ), 2 )
                + Math.pow( ( p1.getZ() - p2.getZ() ), 2 ) );
        }
        else
        {
            return Double.NaN;
        }
    }

}
