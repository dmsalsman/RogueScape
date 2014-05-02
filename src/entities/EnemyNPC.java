package entities;

public class EnemyNPC extends LivingEntity
{
	EnemyNPC(char ASCII, String name) {
		super(ASCII, name);
	}
	
	@Override
	public void interact(LivingEntity entity)
	{
		if(entity instanceof Player)
		{
			((Player)entity).attack(this);
		}
	}
	
}
