package entities;
import java.awt.Color;

import interfaces.Updatable;
import misc.Representable;
import misc.Representation;


public abstract class Entity implements Representable, Updatable
{
	protected Representation DEFAULT_REPRESENTATION;
	protected EntityBehavior entityBehavior;
	public String name;
	public String description;
	
	protected Entity(char ASCII, String name) {
		this.DEFAULT_REPRESENTATION = new Representation(ASCII);
		this.name = name;
		this.entityBehavior = EntityBehavior.DEFAULT;
	}
	
	@Override
	public char getRepresentation()
	{
		return DEFAULT_REPRESENTATION.ASCII;
	}
	
	public char getDefaultRepresentation() {
		return DEFAULT_REPRESENTATION.ASCII;
	}
	
	public void setRepresentation(char ASCII)
	{
		this.DEFAULT_REPRESENTATION.setASCII(ASCII);
	}

	protected void setBehavior(EntityBehavior entityBehavior) {
		// TODO Auto-generated method stub
	}
	
	public void update(Object ... args) {
		entityBehavior.update(this, args);
	}
	
	public Color getColor() {
		return DEFAULT_REPRESENTATION.foreground;
	}
}
