package eu.flatwhite.shiro.spatial;

/**
 * Immutable implementation of {@code SpaceResolver} that always resolves to the same space, regardeless of the provided string.
 *  
 * @author philippe.laflamme@gmail.com
 */
public class SingleSpaceResolver implements SpaceResolver {

  private final Space space;

  public SingleSpaceResolver(Space space) {
    this.space = space;
  }

  @Override
  public Space resolveSpace(String spaceString) {
    return space;
  }

}
