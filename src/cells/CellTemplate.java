package cells;

public class CellTemplate extends Cell
{
	
	CellTemplate(char ASCII, String name) {
		super(ASCII, name);
	}
	
	public Cell makeInstance() {
		Cell instance = new Cell(this.DEFAULT_REPRESENTATION.ASCII, this.name);
		instance.solid = this.solid;
		instance.liquid = this.liquid;
		instance.walkable = this.walkable;
		return instance;
	}
}
