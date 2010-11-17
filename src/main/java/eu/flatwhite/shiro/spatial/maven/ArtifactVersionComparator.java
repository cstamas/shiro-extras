package eu.flatwhite.shiro.spatial.maven;

import java.util.Comparator;

public class ArtifactVersionComparator implements
	Comparator<ArtifactCoordinate> {
    public int compare(ArtifactCoordinate c1, ArtifactCoordinate c2) {
	final double d1 = c1.distance(new ArtifactCoordinate(c1.getSpace(), c1
		.getGroupId(), c1.getArtifactId(), ((ArtifactCoordinate) c1
		.getSpace().getOrigin()).getVersion()));

	final double d2 = c2.distance(new ArtifactCoordinate(c2.getSpace(), c2
		.getGroupId(), c2.getArtifactId(), ((ArtifactCoordinate) c2
		.getSpace().getOrigin()).getVersion()));

	final double cmp = d1 - d2;

	if (cmp < 0) {
	    return -1;
	} else if (cmp > 0) {
	    return 1;
	} else {
	    return 0;
	}
    }
}
