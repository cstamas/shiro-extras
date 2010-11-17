package eu.flatwhite.shiro.spatial;

public class ProjectingRelationProvider implements RelationProvider {

  private final SpatialProjector projector;

  private final RelationProvider provider;

  public ProjectingRelationProvider(SpatialProjector projector, RelationProvider provider) {
    this.projector = projector;
    this.provider = provider;
  }

  @Override
  public Relation getRelation(Spatial s1, Spatial s2) {
    // project s2 in s1's space
    Spatial projection = projector.project(s2, s1.getSpace());
    if(projection != null) {
      return getProjectedRelation(s1, projection);
    }
    // project s1 in s2's space
    projection = projector.project(s1, s2.getSpace());
    if(projection != null) {
      return getProjectedRelation(projection, s2);
    }
    // Try without any projection
    return getProjectedRelation(s1, s2);
  }

  protected Relation getProjectedRelation(Spatial s1, Spatial s2) {
    return provider.getRelation(s1, s2);
  }

}
