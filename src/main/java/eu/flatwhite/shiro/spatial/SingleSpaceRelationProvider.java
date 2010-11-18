package eu.flatwhite.shiro.spatial;

/**
 * Immutable implementation of {@code SpaceRelationProvider} that always returns
 * the same {@code RelationProvider} instance regardless of the {@code Space}
 * instance.
 * 
 * @author philippe.laflamme@gmail.com
 * 
 */
public class SingleSpaceRelationProvider implements SpaceRelationProvider {

    private final RelationProvider relationProvider;

    public SingleSpaceRelationProvider(RelationProvider relationProvider) {
	this.relationProvider = relationProvider;
    }

    @Override
    public RelationProvider getRelationProvider(Space space) {
	return relationProvider;
    }

}
