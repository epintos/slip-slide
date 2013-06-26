package model.game;

import java.io.IOException;
import java.util.HashMap;

import exceptions.ErrorWhileReadingFileException;
import exceptions.NoLevelsFoundOnFolderException;
import exceptions.NoSizeFoundOnFileException;
import exceptions.NoSuchPasswordException;
import exceptions.PlayerMustBeOnBlankCellException;
import exceptions.PointAllreadyContainsCellException;
import exceptions.RepeatedPasswordsException;

import model.interfaces.KeyListener;
import model.level.Level;
import model.parser.Parser;
import model.saveGame.MovementsLog;

/**
 * A Game contains the actualLevel an it's number
 */
public class Game {

	private int actualLevelNumber = 1;
	private Level actualLevel;
	private KeyListener keyListener;
	private Parser parser;

	/**
	 * Initializes the Game with an ArrayList of Levels and sets actualLevel
	 * with the level Nº1
	 * 
	 * @throws RepeatedPasswordsException
	 *             If two or more levels have the same password
	 * @throws NoSizeFoundOnFileException
	 *             If a level has an error in it's size
	 * @throws IOException
	 *             If there was a problem reading a level
	 * @throws ErrorWhileReadingFileException
	 *             If the file is corrupt
	 * @throws PlayerMustBeOnBlankCellException
	 *             If the Player is over an incorrect Cell
	 * @throws PointAllreadyContainsCellException
	 *             If two or more cells are in the same place
	 * @throws NoLevelsFoundOnFolderException
	 *             If there are no levels
	 * 
	 */
	public Game() throws ErrorWhileReadingFileException, IOException,
			NoSizeFoundOnFileException, RepeatedPasswordsException,
			NumberFormatException, PlayerMustBeOnBlankCellException,
			PointAllreadyContainsCellException, NoLevelsFoundOnFolderException {
		this.parser = new Parser();
	}

	/**
	 * Returns a String with the next level's password.
	 * 
	 * @return String Contains the next level's password
	 */
	public String getNextLevelPassword() {
		return parser.getPasswords().get(actualLevelNumber + 1);
	}

	/**
	 * Pass the game to the next level, increasing actualLevelNumber and setting
	 * actualLevel with the following level
	 * 
	 * @throws PointAllreadyContainsCellException
	 *             If two or more cells are in the same place
	 * @throws PlayerMustBeOnBlankCellException
	 *             If the Player is over an incorrect Cell
	 * @throws NoSizeFoundOnFileException
	 *             If a level has an error in it's size
	 * @throws IOException
	 *             If there was a problem reading a level
	 * @throws ErrorWhileReadingFileException
	 *             If the file is corrupt
	 */
	public void passToNextLevel() throws ErrorWhileReadingFileException,
			IOException, NoSizeFoundOnFileException,
			PlayerMustBeOnBlankCellException,
			PointAllreadyContainsCellException {
		++actualLevelNumber;
		actualLevel = parser.parseALevel(actualLevelNumber);
	}

	/**
	 * Returns if the game is over or not
	 * 
	 * @return TRUE if the game is over FALSE if the game isn't over
	 */
	public boolean isOver() {
		return actualLevelNumber == parser.getPasswords().size();
	}

	/**
	 * Searches if a level contains an specific password
	 * 
	 * @param inserted
	 * @return boolean TRUE if the password was found FALSE if no password was
	 *         found
	 */
	public boolean searchPasswordExistance(String inserted) {
		boolean found = false;
		HashMap<Integer, String> levels = parser.getPasswords();
		for (Integer level : parser.getPasswords().keySet()) {
			if (inserted != null && inserted.equals(levels.get(level))) {
				found = true;
			}
		}
		return found;
	}

	/**
	 * Returns the level number that matches with the password inserted.
	 * 
	 * @param inserted
	 * @return int Contains the level number
	 * @throws NoSuchPasswordException
	 *             If the password wasn't found
	 */
	public int searchPassword(String inserted) throws NoSuchPasswordException {
		HashMap<Integer, String> levels = parser.getPasswords();
		for (Integer level : parser.getPasswords().keySet()) {
			if (inserted != null && inserted.equals(levels.get(level))) {
				return level;
			}
		}
		throw new NoSuchPasswordException();
	}

	/**
	 * Sets the actualLevelNumber and the actualLevel
	 * 
	 * @param actualLevelNumber
	 * @throws PointAllreadyContainsCellException
	 *             If two or more cells are in the same place
	 * @throws PlayerMustBeOnBlankCellException
	 *             If the Player is over an incorrect Cell
	 * @throws NoSizeFoundOnFileException
	 *             If a level has an error in it's size
	 * @throws IOException
	 *             If there was a problem reading a level
	 * @throws ErrorWhileReadingFileException
	 *             If the file is corrupt
	 */
	public void setActualLevel(int actualLevelNumber)
			throws ErrorWhileReadingFileException, IOException,
			NoSizeFoundOnFileException, PlayerMustBeOnBlankCellException,
			PointAllreadyContainsCellException {

		this.actualLevelNumber = actualLevelNumber;
		this.actualLevel = parser.parseALevel(actualLevelNumber);
		actualLevel
				.setMovementsLog(new MovementsLog(actualLevel.getPassword()));
	}

	/**
	 * Returns the actual level number
	 * @return  int Represents the actual level's number
	 */
	public int getActualLevelNumber() {
		return actualLevelNumber;
	}

	/**
	 * Returns the actual level
	 * @return    Level
	 */
	public Level getActualLevel() {
		return actualLevel;
	}

	/**
	 * Sets the move log with the level time
	 */
	public void save() {
		actualLevel.getMoveLog().setTime(
				System.currentTimeMillis() - actualLevel.getTime());
	}

	/**
	 * Sets the key listener
	 * @param  keyListener
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
}
