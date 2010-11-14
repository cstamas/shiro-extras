package eu.flatwhite.shiro.spatial.maven;

public class ArtifactVersionSpace
    extends ArtifactSpace
{
    @Override
    protected double calculateDistance( ArtifactCoordinate a1, ArtifactCoordinate a2 )
    {
        if ( equals( a1.getGroupId(), a2.getGroupId() ) && equals( a1.getArtifactId(), a2.getArtifactId() ) )
        {
            // TODO: Use ArtifactVersion and compare by version?
            return 0;
        }
        else
        {
            return Double.NaN;
        }
    }
}
