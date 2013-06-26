package model.cells;

import java.awt.Point;

/**
 * 
 * A BlankCell is a Cell that a Player can pass always.
 */
public class BlankCell extends Cell {

	/**
	 * Initializes cell location
	 * 
	 * @param point
	 *            Point representing the location
	 */
	public BlankCell(Point point) {
		super(point);
	}

	@Override
	public boolean canPass() {
		return true;
	}

	/**
	 * When the player is over a blank cell, nothing must change
	 */
	@Override
	public void imOver() {
	};

}
