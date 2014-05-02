package cells;

import misc.Openable;
import entities.LivingEntity;

//An example of a cell type with greater complexity
public class DoorTemplate extends CellTemplate
{

	DoorTemplate(char ASCII, String name)
	{
		super(ASCII, name);
	}

	@Override
	public Cell makeInstance()
	{
		if (this.DEFAULT_REPRESENTATION.ASCII == ',')
			return new Door(true);
		return new Door(false);
	}

	private class Door extends Cell implements Openable
	{
		private boolean	isOpen	= false;

		Door(boolean isOpen)
		{
			super('+', "DOOR");
			this.isOpen = isOpen;
			this.solid = true;
			this.liquid = false;
			if (isOpen)
				this.open();
			else
				this.close();
		}

		@Override
		public boolean interact(LivingEntity entity)
		{
			if (!isOpen)
			{
				this.open();
				return false;
			} else
				return super.interact(entity);
		}

		@Override
		public boolean isWalkable()
		{
			return this.isOpen();
		}

		@Override
		public boolean isOpen()
		{
			return isOpen;
		}

		@Override
		public String open()
		{
			this.isOpen = true;
			this.DEFAULT_REPRESENTATION.ASCII = ',';
			return "The door swings open.";
		}

		@Override
		public String close()
		{
			this.isOpen = false;
			this.DEFAULT_REPRESENTATION.ASCII = '+';
			return "The door slams shut.";
		}
	}
}
