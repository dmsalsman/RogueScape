package misc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.Game;
import entities.Player;

//https://castever.wordpress.com/2008/07/31/how-to-create-your-own-events-in-java/
public class PlayerMovedEventSource
{
	private Game game;
	private Player player;
	public PlayerMovedEventSource(Game game) {
		this.game = game;
		this.player = game.getPlayer();
	}
	
	private List<PlayerMovementListener> _listeners = new ArrayList<PlayerMovementListener>();
	
	public synchronized void addEventListener(PlayerMovementListener listener) {
		_listeners.add(listener);
	}
	public synchronized void removeEventListener(PlayerMovementListener listener) {
		_listeners.add(listener);
	}
	
	public synchronized void playerMoved() {
		PlayerMovedEvent event = new PlayerMovedEvent(game, player, game.getCellContainingPlayer());
		Iterator<PlayerMovementListener> i = _listeners.iterator();
		while(i.hasNext())
			((PlayerMovementListener) i.next()).playerMoved(event);
	}
}
