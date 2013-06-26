package model.cells;

import java.awt.Point;

import model.interfaces.Paintable;

/**
 * A Door is a Cell which a Player can pass only when an associated Key was
 * found, and its open.
 */
public class Door extends Cell implements Paintable {

	private enum states {
		VISIBLE, NOTVISIBLE
	};
	private states state;
	private int id;

	/**
	 * Initializes the Door's location and it's state to VISIBLE
	 * 
	 * @param location
	 */
	public Door(Point location) {
		super(location);
		state = states.VISIBLE;
	}

	/**
	 * Returns the door's state
	 * 
	 * @return TRUE if it's state is NOTVISIBLE. FALSE if it's state is VISIBLE.
	 */
	@Override
	public boolean canPass() {
		return state == states.NOTVISIBLE;
	}

	/**
	 * If a player is over the door, this means that it was opened. Therefore,
	 * nothing must change in the level.
	 */
	@Override
	public void imOver() {
	};

	/**
	 * Sets the state of the Door to NOTVISIBLE
	 */
	public void open() {
		state = states.NOTVISIBLE;
	}

	@Override
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public int getID() {
		return id;
	}
}
