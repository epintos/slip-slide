package model.cells;

import java.awt.Point;

/**
 * A Coin is a Collectable Cell that adds points only the first time it's
 * collected, then it turns it's state to NOTVISIBLE
 */
public class Coin extends Collectable {

	private int value;

	/**
	 * Initializes the Coin with it's value and location
	 * 
	 * @param value
	 */
	public Coin(int value, Point p) {
		super(p);
		this.value = value;
	}

	/**
	 * Sets the state to NOTVISIBLE, adds it's corresponding points to the level,
	 * and removes it.
	 */
	@Override
	public void imOver() {
		if (getState() == states.VISIBLE) {
			getKeyListener().removeElement(getLocation());
			getLevel().addPoints(value);
		}
		super.imOver();
	}
}
