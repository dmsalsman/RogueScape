/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package screens;

import asciiPanel.AsciiPanel;
import misc.Constants;
import misc.Util;

import java.awt.event.KeyEvent;

import cells.Cell;
import cells.Dungeon;
import entities.Player;

public class PlayScreen implements Screen {
	
	
	private Dungeon dungeon;
	private Player player;
	private int centerX;
	private int centerY;
	private int screenWidth;
	private int screenHeight;
	
	public PlayScreen() {
		screenWidth = Constants.DEFAULT_WIDTH;
		screenHeight = Constants.DEFAULT_HEIGHT;
		createFloor();
		dungeon = createFloor();
		player = new Player();
		player.x = dungeon.getStartX();
		player.y = dungeon.getStartY();
	}
	
	private Dungeon createFloor() {
		return new Dungeon();
	}
	
	public int getScrollX() {
		return Math.max(0, Math.min(player.x - screenWidth/2, dungeon.width() - screenWidth));
	}
	
	public int getScrollY() {
		return Math.max(0, Math.min(player.y - screenHeight/2, dungeon.height() - screenHeight));
	}
	
	
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for(int x=0;x<screenWidth;x++)
			for(int y=0;y<screenHeight;y++){
				int wx = x + left;
				int wy = y + top;
				Cell cell = dungeon.cells[wx][wy];
				terminal.write(cell.getRepresentation(), x, y, cell.getColor());
			}
	}
	
	private void scrollBy(int mx, int my) {
		centerX = Math.max(0,  Math.min(centerX + mx, dungeon.width() -1 ));
		centerY = Math.max(0, Math.min(centerY + my, dungeon.height() - 1));
	}
	
    @Override
    public void display(AsciiPanel terminal) {
    	int left = getScrollX();
    	int top = getScrollY();
    	
    	displayTiles(terminal, left, top);
    	
    }
    
    
	public void movePlayer(int direction) {
		//uses helper functions in Util class to determine new location
		int x = player.x + Util.getDX(direction);
		int y = player.y + Util.getDY(direction);
		//checks if cell is in array bounds
		// 		 if cell is walkable
		//		 and if cell isn't already occupied
		if(Util.isValidLocation(x, y, dungeon.size)) {
			// && d.cells[x][y].isWalkable() && d.cells[x][y].setLivingEntity(p)
			boolean moved = dungeon.cells[x][y].interact(player);
			if(moved) {
			dungeon.cells[player.x][player.y].clearLivingEntity();
			player.x = x;
			player.y = y;
			}
		}
	}
	
	private void ascendFloor()
	{
		if(getCellContainingPlayer().getDefaultRepresentation() == '<')
		{
			dungeon = new Dungeon();
			player.x = dungeon.getEndX();
			player.y = dungeon.getEndY();
			getCellContainingPlayer().setLivingEntity(player);
		}
	}
	
	
	private void descendFloor()
	{
		if(getCellContainingPlayer().getDefaultRepresentation() == '>')
		{
			dungeon = new Dungeon();
			player.x = dungeon.getStartX();
			player.y = dungeon.getStartY();
			getCellContainingPlayer().setLivingEntity(player);
		}
	}
	
	public Cell getCellContainingPlayer() {
		return dungeon.cells[player.x][player.y];
	}
	
    @Override
    public Screen respond(KeyEvent key) {
        switch (key.getKeyCode()) {
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_H: movePlayer(Util.WEST); break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_L: movePlayer(Util.EAST); break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_K: movePlayer(Util.NORTH); break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_J:	movePlayer(Util.SOUTH); break;
        case KeyEvent.VK_Y: movePlayer(Util.NORTH_WEST); break;
        case KeyEvent.VK_U: movePlayer(Util.NORTH_EAST); break;
        case KeyEvent.VK_B: movePlayer(Util.SOUTH_WEST); break;
        case KeyEvent.VK_N: movePlayer(Util.SOUTH_EAST); break;
        case KeyEvent.VK_PERIOD: descendFloor(); break;
        case KeyEvent.VK_COMMA: ascendFloor(); break;
        }
        

        
        return this;
    }
    
}
