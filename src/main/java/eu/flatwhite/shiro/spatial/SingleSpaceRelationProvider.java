package eu.flatwhite.shiro.spatial;

public class SingleSpaceRelationProvider implements SpaceRelationProvider {
  private RelationProvider relationProvider;

  SingleSpaceRelationProvider(RelationProvider relationProvider) {
    this.relationProvider = relationProvider;
  }

  @Override
  public RelationProvider getRelationProvider(Space space) {
    return relationProvider;
  }

}
