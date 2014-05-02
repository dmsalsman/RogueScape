package misc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import main.Game;

public class CursorCoordinater extends JComponent
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6429142110696820464L;
	public int					x, y;
	private boolean				enabled				= false;

	public CursorCoordinater(JFrame container)
	{
		JLayeredPane layeredPane = container.getLayeredPane();
		layeredPane.add(this, JLayeredPane.DRAG_LAYER);
		this.setBounds(0, 0, container.getWidth(), container.getHeight());
		
		container.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent me)
			{
				if (enabled)
				{
					x = me.getX();
					y = me.getY();
					repaint();
				}
			}
		});
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(enabled) {
		String s = x/Game.UNIT_SIZE + ", " + y/Game.UNIT_SIZE;
		g.setColor(Color.RED);
		g.drawString(s, x, y);
		}
	}

	public void showCoordinates()
	{
		enabled = true;
	}

	public void hideCoordinates()
	{
		enabled = false;
	}

	public void toggleCoodinates()
	{
		enabled = !enabled;
	}
}
