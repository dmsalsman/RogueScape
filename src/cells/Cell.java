package cells;

import entities.Entity;
import entities.Inventory;
import entities.ItemEntity;
import entities.LivingEntity;


public class Cell extends Entity
{
	protected Inventory items = new Inventory();
	protected LivingEntity livingEntity = null;
	protected boolean solid = false;
	protected boolean liquid = false;
	protected boolean walkable = true;
	
	Cell(char ASCII, String name) {
		super(ASCII, name);
	}
	
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}
	
	public boolean setLivingEntity(LivingEntity e) {
		if(livingEntity != null)
			return false;
		livingEntity = e;
		return true;
	}
	
	public ItemEntity getItem() {
		return items.get(0);
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	@Override
	public char getRepresentation() {
		if(livingEntity != null)
			return livingEntity.getRepresentation();
		if(items.size() > 0)
			return items.getFirst().getRepresentation();
		return this.DEFAULT_REPRESENTATION.ASCII;
	}
	
	public void setRepresentation(char ASCII) {
		DEFAULT_REPRESENTATION.setASCII(ASCII);
	}
	
	public void addItem(ItemEntity item) {
		items.add(item);
	}

	public void clearLivingEntity()
	{
		this.livingEntity = null;
		
	}

	public boolean interact(LivingEntity entity)
	{
		if(livingEntity != null) {
			livingEntity.interact(entity);
			if(!livingEntity.isAlive())
				livingEntity = null;
			return false;
		}else if(walkable) {
			livingEntity = entity;
			return true;
		}
		return false;
	}
}
