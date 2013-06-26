package model.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import model.board.Board;
import model.cells.Cell;
import model.cells.Transporter;
import model.interfaces.KeyListener;

/**
 * A Player contains a location, knows a board and a level state
 */
public class Player {

	private Point location;
	private Board board;
	private KeyListener keyListener;
	private boolean levelFinished = false;
	private HashMap<Point, directions> dir;
	private directions direction;

	private enum directions {
		RIGHT("RIGHT"), LEFT("LEFT"), DOWN("DOWN"), UP("UP");

		String direction;

		private directions(String string) {
			this.direction = string;
		}

		private String getString() {
			return direction;
		}
	};

	/**
	 * Initializes the location, the board and dir
	 * 
	 * @param x
	 * @param y
	 */
	public Player(int x, int y, Board board) {
		this.location = new Point(x, y);
		this.board = board;
		dir = new HashMap<Point, directions>();
		dir.put(new Point(1, 0), directions.DOWN);
		dir.put(new Point(-1, 0), directions.UP);
		dir.put(new Point(0, 1), directions.RIGHT);
		dir.put(new Point(0, -1), directions.LEFT);
	}

	/**
	 * Sets the keyListener
	 * 
	 * @param keyListener
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	/**
	 * Returns the location
	 * 
	 * @return Point Indicating the Player's Location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Sets a new location
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		location.x = x;
		location.y = y;
	}

	/**
	 * Moves the player to a new location while it can pass and sets the
	 * transporters mark in false
	 * 
	 * @param rowMove
	 * @param colMove
	 * @return Point Corresponding to the new location
	 */
	public Point move(int rowMove, int colMove) {
		int playerRow = getLocation().x;
		int playerCol = getLocation().y;
		Point movePoint = new Point(rowMove, colMove);
		direction = dir.get(movePoint);
		Cell elem = board.getElemIn(playerRow + rowMove, playerCol + colMove);
		Point newLocation = null;
		while (elem.canPass() && !levelFinished) {
			playerRow = getLocation().x;
			playerCol = getLocation().y;
			playerRow += rowMove;
			playerCol += colMove;
			setLocation(playerRow, playerCol);
			newLocation = this.getLocation();
			keyListener.playerMoved(newLocation);
			elem.imOver();
			playerRow = getLocation().x;
			playerCol = getLocation().y;
			elem = board.getElemIn(playerRow + rowMove, playerCol + colMove);
		}
		ArrayList<Transporter> transporters = board.getTransporters();
		for (Transporter each : transporters)
			each.setMarkerFalse();
		return newLocation;
	}

	/**
	 * Returns the player direction
	 * 
	 * @return String With the direction
	 */
	public String getDirection() {
		return direction.getString();
	}

	/**
	 * Sets the level finished state in true
	 */
	public void setFinish() {
		this.levelFinished = true;
	}

}
