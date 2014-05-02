package screens;

import java.awt.DisplayMode;
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {
	
	@Override
	public void display(AsciiPanel console) {
		console.writeCenter("--- press [enter] to begin ---", 22);
	}

	@Override
	public Screen respond(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}

}
