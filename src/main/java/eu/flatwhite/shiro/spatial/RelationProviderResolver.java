package eu.flatwhite.shiro.spatial;

public interface RelationProviderResolver {
    RelationProvider resolveRelationProvider(Spatial spatial,
	    String relationProviderString);
}
