package model.cells;

import java.awt.Point;

public abstract class Collectable extends Cell {

	protected enum states {
		VISIBLE, NOTVISIBLE
	};

	private states state;

	/**
	 * Sets the state to VISIBLE and it's location
	 */
	public Collectable(Point p) {
		super(p);
		state = states.VISIBLE;
	}

	/**
	 * Returns the state of the cell.
	 * 
	 * @return VISIBLE if it is visible. NOTVISIBLE if it isn't.
	 */
	protected states getState() {
		return state;
	}

	/**
	 * A Player can always pass through a Collectable Cell
	 */
	@Override
	public boolean canPass() {
		return true;
	}

	/**
	 * If the player is over a collectable element, this must disappear from the
	 * map.
	 */
	@Override
	public void imOver() {
		state = states.NOTVISIBLE;
	}
}
