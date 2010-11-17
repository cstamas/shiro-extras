package eu.flatwhite.shiro.spatial.maven;

import java.util.ArrayList;
import java.util.Collections;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MavenArtifactVersionSpaceTest extends TestCase {
    public void testSimple() {
	// set up space
	ArtifactVersionSpace space = new ArtifactVersionSpace();

	ArtifactCoordinate v1_0_snapshot = new ArtifactCoordinate(space,
		"eu.flatwhite.shiro", "shiro-extras", "1.0-SNAPSHOT");
	ArtifactCoordinate v1_0_alpha = new ArtifactCoordinate(space,
		"eu.flatwhite.shiro", "shiro-extras", "1.0-alpha");
	ArtifactCoordinate v1_0_beta = new ArtifactCoordinate(space,
		"eu.flatwhite.shiro", "shiro-extras", "1.0-beta");
	ArtifactCoordinate v1_0 = new ArtifactCoordinate(space,
		"eu.flatwhite.shiro", "shiro-extras", "1.0");
	ArtifactCoordinate v1_1_snapshot = new ArtifactCoordinate(space,
		"eu.flatwhite.shiro", "shiro-extras", "1.1-SNAPSHOT");
	ArtifactCoordinate v1_1 = new ArtifactCoordinate(space,
		"eu.flatwhite.shiro", "shiro-extras", "1.1");

	Assert.assertEquals(0.0, v1_0.distance(v1_0));
	Assert.assertEquals(0.0, v1_0.distance(v1_0));

	ArrayList<ArtifactCoordinate> coordinates = new ArrayList<ArtifactCoordinate>();
	coordinates.add(v1_0_beta);
	coordinates.add(v1_1);
	coordinates.add(v1_0);
	coordinates.add(v1_0_alpha);
	coordinates.add(v1_1_snapshot);
	coordinates.add(v1_0_snapshot);

	Collections.sort(coordinates, new ArtifactVersionComparator());

	System.out.println(coordinates);
    }
}
