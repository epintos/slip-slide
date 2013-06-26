package model.cells;

import java.awt.Point;
import java.util.HashMap;

import model.board.Board;
import model.interfaces.KeyListener;
import model.interfaces.Paintable;
import model.player.Player;

/**
 * A Transporter is a Cell which has a destiny, where the Player will end if it goes through it
 */
public class Transporter extends Cell implements Paintable {

	private Transporter destiny;
	private int id;
	private KeyListener keyListener;
	private HashMap<String, Point> directions = new HashMap<String, Point>();
	private boolean marker = false;

	/**
	 * Initializes the ID, location, destiny ,destiny's ID and the directions
	 * 
	 * @param point
	 *            Point representing the location of the transporter cell
	 * @param destiny
	 */
	public Transporter(Point point, Transporter destiny) {
		super(point);
		this.destiny = destiny;
		this.destiny.destiny = this;
		this.destiny.setID(id);
		initializeDirections();

	}

	/**
	 * Initializes the location and directions.
	 * 
	 * @param point
	 *            Point representing the location of the transporter cell
	 */
	public Transporter(Point point) {
		super(point);
		initializeDirections();

	}

	private void initializeDirections() {
		directions.put("RIGHT", new Point(0, 1));
		directions.put("LEFT", new Point(0, -1));
		directions.put("DOWN", new Point(1, 0));
		directions.put("UP", new Point(-1, 0));
	}

	/**
	 * Sets the marker in true
	 */
	public void setMarkerTrue() {
		marker = true;
		destiny.marker = true;
	}

	/**
	 * Sets the marker in false
	 */
	public void setMarkerFalse() {
		marker = false;
		destiny.marker = false;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void setID(int id) {
		this.id = id;
		destiny.id = id;
	}

	/**
	 * Returns a Point that corresponds to the destiny.
	 * 
	 * @return Point
	 */
	public Point getDestinyLocation() {
		return destiny.getLocation();
	}

	/**
	 * Returns the destiny
	 * @return    Transporter
	 */
	public Transporter getDestiny() {
		return destiny;
	}

	/**
	 * Sets the KeyListener
	 * @param  keyListener
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	/**
	 * Evaluates if the player can pass through this cell. The player can pass
	 * through this cell only if it can also pass through the adyacent cell of
	 * the destiny, moving in the same direction as when it entered this cell.
	 */
	@Override
	public boolean canPass() {
		Player player = getLevel().getPlayer();
		String dir = player.getDirection();
		Point point = directions.get(dir);
		Point location = destiny.getLocation();
		Board board = getLevel().getBoard();
		if (board.getElemIn(location.x + point.x, location.y + point.y)
				.canPass())
			return true;
		return false;
	}

	/**
	 * Moves the Player to the destiny's location
	 * 
	 * @throws LoopException
	 *             If the Player enters in a transporter which ends in a loop
	 *             with it's destiny. If this happens, the level is finished.
	 */
	@Override
	public void imOver() {
		KeyListener keyListener = getKeyListener();
		if (marker) {
			keyListener.transporterLoop();
			getLevel().setFinish();
		} else {
			Player player = getLevel().getPlayer();
			Point location = destiny.getLocation();
			player.setLocation(location.x, location.y);
			keyListener.playerTeleport(location);
			setMarkerTrue();
		}
	}
}
