package model.interfaces;

import java.awt.Point;

public interface KeyListener {

	/**
	 * Moves the player to a new location
	 * 
	 * @param newLocation
	 */
	public void playerMoved(Point newLocation);

	/**
	 * Teleports the player to the new location.
	 * 
	 * @param newLocation
	 */
	public void playerTeleport(Point newLocation);

	/**
	 * Removes an element in a point
	 * 
	 * @param point
	 */
	public void removeElement(Point point);

	/**
	 * The next level is initialize and if there is no other level the game
	 * finishes with a victory
	 */
	public void levelIsOver();

	/**
	 * If leftTries > 1 it changes it's number, otherwise it removes it.
	 * 
	 * @param location
	 * @param leftTries
	 */
	public void changeBreakableWallLeftTries(Point location, int leftTries);

	/**
	 * Shows an error message and restarts the level when a player enters on a
	 * loop
	 */
	public void transporterLoop();
}
