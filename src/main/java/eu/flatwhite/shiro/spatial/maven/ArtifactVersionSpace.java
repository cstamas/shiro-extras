package eu.flatwhite.shiro.spatial.maven;

import eu.flatwhite.utilities.comparison.AlphanumComparator;

public class ArtifactVersionSpace extends ArtifactSpace {
    private final AlphanumComparator alphanumComparator = new AlphanumComparator();

    @Override
    protected double calculateDistance(ArtifactCoordinate a1,
	    ArtifactCoordinate a2) {
	if (equals(a1.getGroupId(), a2.getGroupId())
		&& equals(a1.getArtifactId(), a2.getArtifactId())) {
	    return Math.abs(alphanumComparator.compare(a1.getVersion(),
		    a2.getVersion()));
	} else {
	    return Double.NaN;
	}
    }
}
