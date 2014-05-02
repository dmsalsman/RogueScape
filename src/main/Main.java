package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;
import screens.Screen;
import screens.StartScreen;

public class Main extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -98622586310854677L;
	
	private AsciiPanel terminal;
	private Screen screen;
	
	private static final int WIDTH = 80;
	private static final int HEIGHT = 40;
	public Main() {
		super();
		terminal = new AsciiPanel(WIDTH, HEIGHT);
    	add(terminal);
    	pack();
    	screen = new StartScreen();
    	addKeyListener(this);
    	repaint();
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
	}
	
    public void repaint() {
    	terminal.clear();
    	screen.display(terminal);
    	super.repaint();
    }
    
    public void keyPressed(KeyEvent e) {
    	screen = screen.respond(e);
    	repaint();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
    	Main main = new Main();
    }
}
