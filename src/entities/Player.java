package entities;

public class Player extends LivingEntity
{
	public Player() {
		super('@', "Player");
		health = 10;
	}

	@Override
	public void interact(LivingEntity entity)
	{
		
	}
	
	class PlayerBehavior extends EntityBehavior {
		
	}
}
