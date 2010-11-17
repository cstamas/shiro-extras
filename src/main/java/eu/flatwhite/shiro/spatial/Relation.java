package eu.flatwhite.shiro.spatial;

/**
 * The possible "relations" in space, using example from 3d: inside (ie. a spere
 * being compared has less radius than the sphere compared to), touches (ie. the
 * two spheres being compared has equal radiuses), outside (ie. a sphere being
 * compared has greater radius than the sphere compared to), unrelated, the
 * relation cannot be determined, like asking for a spatial relation of a
 * tree-node and a 3d point, not-defined, not same space (not comparable) or the
 * space definition itself says "unrelated" (see NodeRelationProvider for
 * example). The actual interpretation of these depends on the space and how it
 * is defined.
 * 
 * @author cstamas
 */
public enum Relation {
    /**
     * "Less than" in common language.
     */
    INSIDE,

    /**
     * "Equals to" in common language.
     */
    TOUCHES,

    /**
     * "Larger than" in common language.
     */
    OUTSIDE,

    /**
     * "Relation undefined" in common language.
     */
    UNRELATED;
}
