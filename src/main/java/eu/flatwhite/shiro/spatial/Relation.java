package eu.flatwhite.shiro.spatial;

/**
 * The possible "relations" in space: inside (ie. a point is inside a sphere), touches (ie. a point lies on the surface
 * of something), outside (ie. a point is outside a sphere), unrelated (the relation cannot be determined, like asking
 * for a spatial relation of a tree node and a 3d point, not-defined). The actual interpretation of these depends on the
 * space and how it is defined.
 * 
 * @author cstamas
 */
public enum Relation
{
    INSIDE,

    TOUCHES,

    OUTSIDE,

    UNRELATED;
}
