package eu.flatwhite.shiro.spatial;

public interface Space
{
    Spatial getOrigin();

    boolean isContaining( Spatial spatial );

    double distance( Spatial s1, Spatial s2 );
}
