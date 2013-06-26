package model.parser;

import java.awt.Point;

import exceptions.ErrorWhileReadingFileException;

import model.board.Board;
import model.player.Player;

public class ParsePlayer extends GenericParser {

	private Player player;
	private Board b;

	public ParsePlayer(Board b) {
		this.b = b;
	}

	/**
	 * Parses a player. Saves the instance of Player in the variable player
	 * 
	 * @param str
	 * @throws ErrorWhileReadingFileException 
	 */
	public void parsePlayer(String str) throws ErrorWhileReadingFileException {
		String []elems = str.split(",");
		if(elems.length != 6)
			throw new ErrorWhileReadingFileException();
		Point p = new Point(Integer.parseInt(elems[1]), Integer.parseInt(elems[2]));
		player = new Player(p.x+1, p.y+1, b);

	}

	/**
	 * Returns the player
	 * @return Player
	 * 
	 */
	public Player getPlayer() {
		return player;
	}
}
