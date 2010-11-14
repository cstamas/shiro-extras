package eu.flatwhite.shiro.spatial.maven;

import eu.flatwhite.shiro.spatial.AbstractSpatial;
import eu.flatwhite.shiro.spatial.Space;

public class ArtifactCoordinate
    extends AbstractSpatial
{
    private final String groupId;

    private final String artifactId;

    private final String version;

    public ArtifactCoordinate( final Space space, final String groupId, final String artifactId, final String version )
    {
        super( space );

        this.groupId = groupId;

        this.artifactId = artifactId;

        this.version = version;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    // ==

    public String toString()
    {
        return String.format( "%s:%s:%s", getGroupId(), getArtifactId(), getVersion() );
    }
}
