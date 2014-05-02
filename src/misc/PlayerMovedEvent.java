package misc;

import java.util.EventObject;

import main.Game;
import cells.Cell;
import entities.Player;

public class PlayerMovedEvent extends EventObject
{
	private static final long	serialVersionUID	= -858587957424354533L;
	
	Game game;
	Player player;
	Cell cell;
	
	public PlayerMovedEvent(Game game, Player player, Cell cellContainingPlayer)
	{
		super(game);
		this.game = game;
		this.player = player;
		this.cell = cellContainingPlayer;
	}
	
	public Cell getCell() {
		return cell;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Game getGame() {
		return game;
	}
}
