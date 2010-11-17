package eu.flatwhite.shiro.spatial;

public abstract class AbstractRelationProvider implements RelationProvider {

    @Override
    public Relation getRelation(Spatial s1, Spatial s2) {
	// If s1 and s2 don't share a Space, we must project one onto the other
	if (s1.getSpace().isContaining(s2) == false) {
	    s2 = s1.getSpace().project(s2);
	}
	if (s1 != null && s2 != null) {
	    return relate(s1, s2);
	}
	return Relation.UNRELATED;
    }

    protected abstract Relation relate(Spatial s1, Spatial s2);

}
