package eu.flatwhite.shiro.spatial.finite;

import eu.flatwhite.shiro.spatial.Relation;
import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.SphereRelationProvider;

/**
 * Node relation provider extends the sphere notion on trees, in a way that it
 * enforces that the two provided spatial nodes must be comparable. The fact
 * they are both contained in same space is not enough, because how we defined
 * "space" on tree (see NodeSpace class).
 * 
 * @author cstamas
 */
public class NodeRelationProvider extends SphereRelationProvider {
    public Relation getRelation(Spatial s1, Spatial s2) {
	if (!Double.isNaN(s1.distance(s2))) {
	    return super.getRelation(s1, s2);
	} else {
	    return Relation.UNRELATED;
	}
    }
}
