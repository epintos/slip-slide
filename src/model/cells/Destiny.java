package model.cells;

import java.awt.Point;

/**
 * Represents the Cell where the Player needs to reach in order to win
 *
 */
public class Destiny extends Cell {

	/**
	 * Initializes it's location
	 * 
	 * @param point
	 */
	public Destiny(Point point) {
		super(point);
	}

	/**
	 * A Player can always pass through the destiny
	 */
	@Override
	public boolean canPass() {
		return true;
	}

	/**
	 * When the player is over the target, the level must end
	 */
	@Override
	public void imOver() {
		getKeyListener().levelIsOver();
		getLevel().setFinish();
	}

}
