package model.saveGame;

import java.util.ArrayList;

/**
 * This class is in charge of keeping a log of all the actions taken by the player as well as the time taken till the time it was saved and the password of the level so, when the file is read, the level can be identified
 */
public class MovementsLog {

	private ArrayList<String> log;
	private long time;
	private String password;

	/**
	 * Creates an instance of this class that will be in charge of keeping a log
	 * of all the movements done by the player
	 * 
	 * @param password
	 */
	public MovementsLog(String password) {
		this.password = password;
		log = new ArrayList<String>();
	}

	/**
	 * Adds the movement "UP"
	 */
	public void addMovementUP() {
		log.add("UP");
	}

	/**
	 * Adds the movement "DOWN"
	 */
	public void addMovementDOWN() {
		log.add("DOWN");
	}

	/**
	 * Adds the movement "RIGHT"
	 */
	public void addMovementRIGHT() {
		log.add("RIGHT");
	}

	/**
	 * Adds the movement "LEFT"
	 */
	public void addMovementLEFT() {
		log.add("LEFT");
	}

	/**
	 * Returns the password of the level that its saving
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns an array list containing in each position a string representing the action taken by the player. These actions can only be "UP", "DOWN", "LEFT" or "RIGHT"
	 * @return    ArrayList<String> representing all the actions the player did  during the playing of a level
	 */
	public ArrayList<String> getLog() {
		return log;
	}

	/**
	 * Sets the time that the player has been playing the level
	 * @param l    a long representing the time the player has been playing
	 */
	public void setTime(long l) {
		this.time = l;
	}

	/**
	 * Returns the time that was saved in the log
	 */
	public long getTime() {
		return time;
	}

}
