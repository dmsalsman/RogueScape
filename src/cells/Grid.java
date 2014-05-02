package cells;


public class Grid
{
	public final static int DEFAULT_SIZE = 128;
	public int size = DEFAULT_SIZE;
	
	public int width() {return size;}
	public int height() {return size;}
	
	public Cell[][] cells;
	
	Grid() {
		cells = new Cell[size][size];
	}
	
	Grid(int size) {
		this.size = size;
		cells = new Cell[size][size];
	}
	
	public static void clearCells(Grid grid) {
		for(int i=0;i<grid.size;i++)
			for(int j=0;j<grid.size;j++)
				grid.cells[i][j] = Cells.AIR.makeInstance();
	}
	
	public static void fillCells(Grid grid, CellTemplate cellType)
	{
		for(int i=0;i<grid.size;i++)
			for(int j=0;j<grid.size;j++)
				grid.cells[i][j] = cellType.makeInstance();
	}
	/*
	public static Grid getGridFromFile(String url)
	{
		return getGridFromFile(new File(url));
	}
	
	public static Grid getGridFromFile(File file) {
		Grid grid = new Grid();
		try {
			Scanner in = new Scanner(file);
			grid.size = in.nextInt();
			for(Cell[] row: grid.cells)
				for(Cell cell: row)
					cell = Cells.getTemplate(in.nextInt());
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return grid;
	}*/
}
