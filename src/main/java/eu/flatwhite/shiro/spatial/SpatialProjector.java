package eu.flatwhite.shiro.spatial;

public interface SpatialProjector {

  /**
   * Returns a {@code Spatial} instance that is the projection of {@code spatial} into {@code space}.
   * 
   * @param spatial
   * @param space
   * @return
   */
  public Spatial project(Spatial spatial, Space space);
  
}
