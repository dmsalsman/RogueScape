package entities;

import interfaces.Updatable;

public class EntityBehavior implements Updatable {
	protected static final EntityBehavior DEFAULT = getDefault();
	
	public EntityBehavior() {
		
	}
	
	public void update(Object ... args) {
		
	}
	
	private static EntityBehavior getDefault() {
		return new EntityBehavior();
	}
}
