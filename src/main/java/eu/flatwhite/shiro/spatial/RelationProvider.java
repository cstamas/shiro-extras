package eu.flatwhite.shiro.spatial;

/**
 * A strategy to provide relations of two spatial. It is up to implementation
 * how it does. Also, the implementation does not have to use all of the
 * possible Relations. One example is the SamePointRelationProvider, that
 * returns only TOUCHES (meaning, that the two spatials represent same point in
 * space, "equals" in common language) or UNRELATED (not same point) "unrelated"
 * (points has no extent in space).
 * 
 * @author cstamas
 */
public interface RelationProvider {
    /**
     * Returns the relation of two spatial.
     * 
     * @param s1
     * @param s2
     * @return
     */
    Relation getRelation(Spatial s1, Spatial s2);
}
