package entities;

import java.util.ArrayList;

public class Inventory extends ArrayList<ItemEntity>
{
	public ItemEntity getFirst() {
		return this.get(0);
	}
}
