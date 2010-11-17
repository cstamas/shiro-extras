package eu.flatwhite.shiro.spatial;

/**
 * A space defines metric, the distance. Also, it determines what spatial are
 * actually belonging to that space. And it provide it's origin, a special point
 * of that space that may serve as reference point if needed.
 * 
 * @author cstamas
 */
public interface Space {
    /**
     * The origin point of the space.
     * 
     * @return
     */
    Spatial getOrigin();

    /**
     * Determines is the space containing the provided spatial.
     * 
     * @param spatial
     * @return
     */
    boolean isContaining(Spatial spatial);

    /**
     * Returns a projection of a {@code Spatial} of another {@code Space} onto
     * this space or null if the projection cannot be performed.
     * 
     * @param spatial
     * @return
     */
    Spatial project(Spatial spatial);

    /**
     * Calculates the distance between two spatial. Result will be Double.NaN,
     * if one or both spatial does not belong to this space, or if it is how
     * space is defined. To differentiate between those two cases, use
     * isContaining(Spatial) method.
     * 
     * @param s1
     * @param s2
     * @return
     */
    double distance(Spatial s1, Spatial s2);
}
