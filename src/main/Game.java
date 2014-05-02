package main;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import misc.CellInfoComponent;
import misc.CursorCoordinater;
import misc.PlayerMovedEventSource;
import misc.Util;
import cells.Cell;
import cells.Dungeon;
import entities.Player;


public class Game extends JPanel
{
	public static final int VIEW_DISTANCE = 8;
	public static final int PLAYER_INFO_WIDTH = 10;
	public static final int GRID_START = PLAYER_INFO_WIDTH + 1;
	public static final int CELL_INFO_START = GRID_START + (2*VIEW_DISTANCE) + 2;
	
	public static final int UNIT_SIZE = 24;
	private static final long	serialVersionUID	= -824413027067294540L;
	
	private static  CursorCoordinater cursor;
	private static PlayerMovedEventSource movementHandler;
	
	Dungeon d = new Dungeon();
	Player p = new Player();
	Game() {
		init();
	}
	
	private void init() {
		//init Game variables
		d = new Dungeon();
		p.x = d.getStartX();
		p.y = d.getStartY();
		d.cells[p.x][p.y].setLivingEntity(p);
		
		//init Event stuff
		movementHandler = new PlayerMovedEventSource(this);
		
		//init GUI stuff
		JFrame container = new JFrame("RogueScape");
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container.setSize(1024, 768);
		container.setPreferredSize(new Dimension(container.getWidth(), container.getHeight()));
		container.pack();
		container.setLocationRelativeTo(null);
		
		container.addKeyListener(new GameKeyHandler());
		container.setContentPane(this);
		
		CellInfoComponent cellInfo = new CellInfoComponent();
		container.getLayeredPane().add(cellInfo, Integer.MAX_VALUE);
		cellInfo.setBounds(0, 0, container.getWidth(), container.getHeight());
		movementHandler.addEventListener(cellInfo);
		
		cursor = new CursorCoordinater(container);
		cursor.showCoordinates();
		container.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		container.setVisible(true);
		
		

	    // make the cursor a crosshair shape
	    container.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	    
	    // display the jframe
	    container.setVisible(true);
	}
	
	public void movePlayer(int direction) {
		//uses helper functions in Util class to determin new location
		int x = p.x + Util.getDX(direction);
		int y = p.y + Util.getDY(direction);
		//checks if cell is in array bounds
		// 		 if cell is walkable
		//		 and if cell isn't already occupied
		if(Util.isValidLocation(x, y, d.size)) {
			// && d.cells[x][y].isWalkable() && d.cells[x][y].setLivingEntity(p)
			boolean moved = d.cells[x][y].interact(p);
			if(moved) {
			d.cells[p.x][p.y].clearLivingEntity();
			p.x = x;
			p.y = y;
			movementHandler.playerMoved();
			}
		}
		repaint();
	}
	
	//duh.. gets dat der cell dat has duh p'ayer init
	public Cell getCellContainingPlayer() {
		return d.cells[p.x][p.y];
	}
	
	//Prints from VIEW_DISTANCE cells north-west of the player to 
	//VIEW_DISTANCE cells south-east, accounting for array bounds
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font backup = g.getFont();
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, UNIT_SIZE));
		int x = p.x - VIEW_DISTANCE;
		int y = p.y - VIEW_DISTANCE;
		for(int i=1; i<(VIEW_DISTANCE*2 + 2); i++)
			for(int j=1; j<(VIEW_DISTANCE*2 + 2); j++)
			{
				if((x+i) < 0 || (y+j) < 0 || (x+i) >= d.size || (y+j) >= d.size)
				{	
					g.drawString("~", (i+GRID_START)*UNIT_SIZE, j*UNIT_SIZE);
					
				}
				else
					g.drawString(""+d.cells[x+i][y+j].getRepresentation(), (i+GRID_START)*UNIT_SIZE, j*UNIT_SIZE);
			}
		g.setFont(backup);
		this.repaint();
	}
	
	//Handles all keyboard input
	private class GameKeyHandler extends KeyAdapter {
		public GameKeyHandler()
		{
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_H)
				movePlayer(Util.WEST);
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_L)
				movePlayer(Util.EAST);
			else if(e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_J)
				movePlayer(Util.SOUTH);
			else if(e.getKeyCode() == KeyEvent.VK_UP  || e.getKeyCode() == KeyEvent.VK_K)
				movePlayer(Util.NORTH);
			else if(e.getKeyCode() == KeyEvent.VK_PERIOD)
				descendFloor();
			else if(e.getKeyCode() == KeyEvent.VK_COMMA)
				ascendFloor();
			else if(e.getKeyCode() == KeyEvent.VK_C)
				cursor.toggleCoodinates();
		}
	}
	
	public static void main(String[] args) {
		Game instance = new Game();
	}

	private void descendFloor()
	{
		if(getCellContainingPlayer().getDefaultRepresentation() == '>')
		{
			d = new Dungeon();
			p.x = d.getStartX();
			p.y = d.getStartY();
			getCellContainingPlayer().setLivingEntity(p);
			repaint();
		}
	}

	private void ascendFloor()
	{
		if(getCellContainingPlayer().getDefaultRepresentation() == '<')
		{
			d = new Dungeon();
			p.x = d.getEndX();
			p.y = d.getEndY();
			getCellContainingPlayer().setLivingEntity(p);
			repaint();
		}
	}
	
	public Player getPlayer()
	{
		return p;
	}
}
