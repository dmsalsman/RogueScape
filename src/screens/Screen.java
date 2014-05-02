package screens;
 
import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
 
public interface Screen {
    public void display(AsciiPanel terminal);
 
    public Screen respond(KeyEvent key);
}