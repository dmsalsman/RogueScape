package entities;

public abstract class LivingEntity extends Entity
{
	Inventory inventory = new Inventory();
	public int x, y;
	public int health;

	LivingEntity(char ASCII, String name) {
		super(ASCII, name);
	}
	
	abstract public void interact(LivingEntity entity);
	public boolean isAlive()
	{
		return health > 0;
	}
	
	public int meleeDamage() {
		return 1;
	}
	
	public void dealDamage(int damage) {
		this.health -= damage;
	}
	public boolean attack(LivingEntity entity) {
		entity.dealDamage(this.meleeDamage());
		return entity.isAlive();
	}
}
