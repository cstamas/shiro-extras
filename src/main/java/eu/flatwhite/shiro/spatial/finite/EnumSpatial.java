package eu.flatwhite.shiro.spatial.finite;

import eu.flatwhite.shiro.spatial.AbstractSpatial;
import eu.flatwhite.shiro.spatial.Space;

/**
 * A point in a {@code EnumeratedSpace} (ie: one {@code Enum} constant)
 * 
 * @author philippe.laflamme@gmail.com
 */
public class EnumSpatial extends AbstractSpatial {

    private final int ordinal;

    public EnumSpatial(Space space, Enum<?> e) {
	super(space);
	this.ordinal = e.ordinal();
    }

    public EnumSpatial(Space space, int ordinal) {
	super(space);
	this.ordinal = ordinal;
    }

    public int getOrdinal() {
	return ordinal;
    }
}
