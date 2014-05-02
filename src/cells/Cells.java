package cells;

import java.util.HashMap;

public class Cells
{
	private static HashMap<String, CellTemplate> cells = new HashMap<String, CellTemplate>();
	
	public static final CellTemplate AIR = makeCellTemplate(' ', "AIR", true, false, false);
	public static final CellTemplate GRANITE_WALL = makeCellTemplate((char)219, "GRANITE_WALL", false, true, false);
	
	public static final CellTemplate STAIRS_UP = makeCellTemplate('<', "STAIRS_UP", true, false, false);
	public static final CellTemplate STAIRS_DOWN = makeCellTemplate('>', "STAIRS_DOWN", true, false, false);

	public static final CellTemplate OPEN_DOOR = initOPEN_DOOR();
	public static final CellTemplate CLOSED_DOOR = initCLOSED_DOOR();
	
	public static CellTemplate makeCellTemplate(char representation, String name, boolean walkable, boolean solid, boolean liquid) {
		CellTemplate template = new CellTemplate(representation, name);
		template.walkable = walkable;
		template.solid = solid;
		template.liquid = liquid;
		cells.put(template.name, template);
		return template;
	}
	
	private static final CellTemplate initOPEN_DOOR() {
		CellTemplate template = new DoorTemplate(',', "OPEN_DOOR");
		cells.put("OPEN_DOOR", template);
		return template;
	}
	
	private static final CellTemplate initCLOSED_DOOR() {
		CellTemplate template = new DoorTemplate('+', "CLOSED_DOOR");
		cells.put("CLOSED_DOOR", template);
		return template;
	}
	
	public static CellTemplate getTemplate(String cellName)
	{
		return cells.get(cellName);	
	}
	
	public boolean contains(int TEMPLATE_ID) {
		if(cells.containsKey(TEMPLATE_ID))
			return true;
		return false;
	}
}
