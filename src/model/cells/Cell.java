package model.cells;


import java.awt.Point;

import model.interfaces.KeyListener;
import model.level.Level;

public abstract class Cell {

	private Level level;
	private Point location;

	/**
	 * Initializes the location
	 * 
	 * @param point
	 *            Point representing the location.
	 */
	public Cell(Point point) {
		this.location = point;
	}

	/**
	 * Returns a boolean indicating if a Player can pass this cell
	 * 
	 * @return True if it can. False if it can not
	 */
	public abstract boolean canPass();

	/**
	 * This method will make the necessary changes and decisions when the player
	 * steps over this cell.
	 */
	public abstract void imOver();

	/**
	 * Initializes level
	 * @param level
	 * @return    Cell
	 */
	public Cell setLevel(Level level) {
		this.level = level;
		return this;
	}

	/**
	 * Returns the level KeyListener
	 * 
	 * @return KeyListener
	 */
	public KeyListener getKeyListener() {
		return level.getKeyListener();
	}

	/**
	 * Returns the level
	 * @return    level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Returns the Cell location
	 * @return    location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Sets the location
	 * @param location    Point representing the location
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

}
