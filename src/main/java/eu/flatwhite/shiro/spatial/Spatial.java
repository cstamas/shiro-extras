package eu.flatwhite.shiro.spatial;

public interface Spatial
{
    Space getSpace();

    double distance( Spatial spatial );
}
