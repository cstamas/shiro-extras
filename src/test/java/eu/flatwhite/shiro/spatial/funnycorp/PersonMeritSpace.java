package eu.flatwhite.shiro.spatial.funnycorp;

import eu.flatwhite.shiro.spatial.Spatial;
import eu.flatwhite.shiro.spatial.inifinite.EuclideanSpace;
import eu.flatwhite.shiro.spatial.inifinite.Point;

/**
 * Person space that uses person's merit for distance. This is a real euclidean space.
 * 
 * @author cstamas
 */
public class PersonMeritSpace
    extends EuclideanSpace
{
  
    public PersonMeritSpace() 
    {
      super(1);
    }

    @Override
    public Spatial project(Spatial spatial) 
    {
      // Project a person's badgeNo onto our single dimension
      if(spatial instanceof Person) {
        return new Point(this, ((Person)spatial).getBadgeNo());
      }
      return null;
    }
}
