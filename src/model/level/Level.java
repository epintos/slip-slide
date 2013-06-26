package model.level;

import java.awt.Point;

import model.board.Board;
import model.interfaces.KeyListener;
import model.player.Player;
import model.saveGame.MovementsLog;
import model.parser.GenericParser;

/**
 * A Level is a class that contains a player, a board, a password, points, time, a key listener and a movements log that saves the player's movements and level's data
 */
public class Level {

	private Player player;
	private Board board;
	private String password;
	private int points = 0;
	private long time;
	private int cellsCrossed = 0;

	private static final int MOVEUP = -1;
	private static final int MOVEDOWN = 1;
	private static final int MOVERIGHT = 1;
	private static final int MOVELEFT = -1;
	private static final int NOTMOVE = 0;
	private KeyListener keyListener;
	private MovementsLog movesLog;

	/**
	 * Initiliazes the Player, Board and password
	 * 
	 * @param board
	 * @param player
	 * @param password
	 */
	public Level(Board board, Player player, String password) {
		this.player = player;
		this.board = board;
		this.password = password;
	}

	/**
	 * Default constructor
	 */
	public Level() {

	}

	/**
	 * Increments in one unit the amount of cells crossed
	 */
	public void incrementCellsCrossed() {
		this.cellsCrossed++;
	}

	/**
	 * Returns the amount of cells crossed
	 * @return    int
	 */
	public int getCellsCrossed() {
		return this.cellsCrossed;
	}

	/**
	 * Resets in zero the amount of cells crossed
	 */
	public void resetCellsCrossed() {
		this.cellsCrossed = 0;
	}

	/**
	 * Sets the password
	 * @param  password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns password
	 * @return    password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the board
	 * @param  board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Sets the level finished state
	 */
	public void setFinish() {
		player.setFinish();
	}

	/**
	 * Sets the player
	 * @param  player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Initializes the time with the actual time in milliseconds
	 */
	public void initializeTime() {
		this.time = System.currentTimeMillis();
	}

	/**
	 * Changes the time
	 * 
	 * @param time
	 */
	public void addSavedTime(long time) {
		this.time -= time;
	}

	/**
	 * Returns the time
	 * @return    time
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * Sets the KeyListener to this and the Player
	 * @param  keyListener
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
		if (player != null) {
			player.setKeyListener(keyListener);
		}
	}

	/**
	 * Returns the KeyListener
	 * @return    KeyListener
	 */
	public KeyListener getKeyListener() {
		return keyListener;
	}

	/**
	 * Moves the Player up.
	 * 
	 * @return Point, which corresponds to the Player's new position after being
	 *         moved.
	 */
	public Point moveUp() {
		movesLog.addMovementUP();
		return player.move(MOVEUP, NOTMOVE);
	}

	/**
	 * Moves the Player down.
	 * 
	 * @return Point, which corresponds to the Player's new position after being
	 *         moved.
	 */
	public Point moveDown() {
		movesLog.addMovementDOWN();
		return player.move(MOVEDOWN, NOTMOVE);
	}

	/**
	 * Moves the Player right.
	 * 
	 * @return Point, which corresponds to the Player's new position after being
	 *         moved.
	 */
	public Point moveRight() {
		movesLog.addMovementRIGHT();
		return player.move(NOTMOVE, MOVERIGHT);
	}

	/**
	 * Moves the Player left.
	 * 
	 * @return Point, which corresponds to the Player's new position after being
	 *         moved.
	 */
	public Point moveLeft() {
		movesLog.addMovementLEFT();
		return player.move(NOTMOVE, MOVELEFT);
	}

	/**
	 * Returns the Player
	 * @return    Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the Board
	 * @return    Board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the points
	 * @return    points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Add points
	 * 
	 * @param points
	 */
	public void addPoints(int points) {
		this.points += points;
	}

	/**
	 * Sets movemetnsLog
	 * 
	 * @param movementsLog
	 */
	public void setMovementsLog(MovementsLog movementsLog) {
		this.movesLog = movementsLog;
	}

	/**
	 * Returns movementsLog
	 * 
	 * @return mvementsLog
	 */
	public MovementsLog getMoveLog() {
		return movesLog;
	}

	private GenericParser genericParser;

	/**
	 * Getter of the property <tt>genericParser</tt>
	 * @return   Returns the genericParser.
	 */
	public GenericParser getGenericParser() {
		return genericParser;
	}

	/**
	 * Setter of the property <tt>genericParser</tt>
	 * @param genericParser   The genericParser to set.
	 */
	public void setGenericParser(GenericParser genericParser) {
		this.genericParser = genericParser;
	}
}
