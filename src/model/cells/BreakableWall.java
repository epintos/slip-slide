package model.cells;

import java.awt.Point;

/**
 * A BreakableWall is a Wall where a Player can't pass until it's leftTries is equal to zero.
 */
public class BreakableWall extends Wall {

	
	private int leftTries;

	/**
	 * Initializes left tries.
	 * 
	 * @param leftTries
	 *            Represents the times, a Player can try passing before it
	 *            breaks.
	 */
	public BreakableWall(Point p, int leftTries) {
		super(p);
		this.leftTries = leftTries;
	}

	/**
	 * Returns left tries
	 * @return    Times left the wall can be "touch" before it brakes.
	 */
	public int getLeftTries() {
		return leftTries;
	}

	/**
	 * Returns if a Player can pass and if TRUE, removes it.
	 * 
	 * @return TRUE if it break and a Player can pass FALSE if hasn't broken.
	 */
	public boolean canPass() {
		if (leftTries > 0) {
			--leftTries;
			if (leftTries > 0)
				getKeyListener().changeBreakableWallLeftTries(getLocation(),
						leftTries);
			else
				getKeyListener().removeElement(getLocation());
			return false;
		} else {
			return true;
		}
	}

	/**
	 * When a player is over a breakable wall, nothing should change
	 */
	@Override
	public void imOver() {
	}
}
