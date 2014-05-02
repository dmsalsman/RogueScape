package misc;

import java.util.Random;

public class Util
{
	
	private static long lastSeed = System.currentTimeMillis();
	private static Random random = new Random(lastSeed);
	public static final int SOUTH = 2;
	public static final int WEST = 4;
	public static final int EAST = 6;
	public static final int NORTH = 8;
	public static final int SOUTH_WEST = 1;
	public static final int SOUTH_EAST = 3;
	public static final int NORTH_WEST = 7;
	public static final int NORTH_EAST = 9;
	
	public static int randomInt(int min, int max) {
		lastSeed += System.currentTimeMillis();
		random.setSeed(lastSeed);
		random.nextInt();
		
		int n = random.nextInt(max-min+1);
		return n+min;
	}

	public static int getDX(int direction)
	{
		if(direction == WEST || direction == SOUTH_WEST || direction == NORTH_WEST)
			return -1;
		if(direction == EAST || direction == SOUTH_EAST || direction == NORTH_EAST)
			return 1;
		return 0;
	}

	public static int getDY(int direction)
	{
		if(direction == NORTH || direction == NORTH_WEST || direction == NORTH_EAST)
			return -1;
		if(direction == SOUTH || direction == SOUTH_WEST || direction == SOUTH_EAST)
			return 1;
		return 0;
	}

	public static boolean isValidLocation(int x, int y, int SIZE)
	{
		if(x < 0 || y < 0 || x >= SIZE || y >= SIZE)
			return false;
		return true;
	}
}
