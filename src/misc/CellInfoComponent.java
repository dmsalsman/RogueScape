package misc;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import main.Game;
import cells.Cell;
import entities.Player;

public class CellInfoComponent extends JComponent implements
		PlayerMovementListener
{
	private static final long	serialVersionUID	= 7921196753816177005L;

	private Player				player;
	private Cell				cell;
	private int					x, y;
	private boolean				enabled				= true;

	public CellInfoComponent()
	{
		x = Game.CELL_INFO_START * Game.UNIT_SIZE;
		y = Game.UNIT_SIZE;
	}

	@Override
	public void playerMoved(PlayerMovedEvent pme)
	{
		player = pme.getPlayer();
		cell = pme.getCell();
		repaint();
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (player != null)
		{
			int start_y = y;
			g.setColor(Color.BLACK);

			String s = "Location: " + player.x + ", " + player.y;
			g.drawString(s, x, y);

			y += Game.UNIT_SIZE;
			s = "Type: " + cell.name;
			g.drawString(s, x, y);

			y += Game.UNIT_SIZE;
			s = "Walkable: " + cell.isWalkable();
			g.drawString(s, x, y);

			y = start_y;
		}
	}
}
