package model.cells;

import java.awt.Point;

/**
 * A Wall is a Cell which a Player can't Pass
 * 
 */
public class Wall extends Cell {

	/**
	 * Initializes the location
	 * @param point
	 */
	public Wall(Point point) {
		super(point);
	}

	/**
	 * A Player cant' pass this Cell
	 */
	@Override
	public boolean canPass() {
		return false;
	}

	/**
	 * The player cann't be over a wall. This method shouldn't be invoked.
	 */
	@Override
	public void imOver(){};
	
}
