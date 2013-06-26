package model.parser;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.board.Board;
import model.cells.BlankCell;
import model.cells.Cell;
import model.level.Level;
import exceptions.ErrorWhileReadingFileException;
import exceptions.NoLevelsFoundOnFolderException;
import exceptions.NoSizeFoundOnFileException;
import exceptions.PlayerMustBeOnBlankCellException;
import exceptions.PointAllreadyContainsCellException;
import exceptions.RepeatedPasswordsException;

/**
 * This class is in charge of parsing the files and building up the level. It
 * will also check that the passwords do not repeat between files
 */
public class Parser extends GenericParser {

	private File myFile;
	private final String path = "." + File.separator + "levels";
	private HashMap<Integer, String> passwords = new HashMap<Integer, String>();
	private String[] levelsInPath;

	/**
	 * Builds a new instance of parser. Constructor gets all the files in the
	 * folder "path + levels", orders them in the corresponding order and
	 * initiates the parsing of each of them.
	 * 
	 * @throws FileNotFoundException
	 *             if the set path does not exist
	 * @throws IOException
	 *             if there was any error while reading the file
	 * @throws RepeatedPasswordsException
	 *             if passwords repeat amongst levels
	 * @throws PlayerMustBeOnBlankCellException
	 *             if the player is placed over a cell different than a
	 *             BlankCell
	 * @throws PointAllreadyContainsCellException
	 *             Exception thrown by the board if the file tries to add to
	 *             cells in the same position
	 * @throws NoLevelsFoundOnFolderException
	 * 
	 */
	public Parser() throws ErrorWhileReadingFileException, IOException,
			NoSizeFoundOnFileException, RepeatedPasswordsException,
			FileNotFoundException, PlayerMustBeOnBlankCellException,
			PointAllreadyContainsCellException, NoLevelsFoundOnFolderException {

		myFile = new File(path);

		if (!myFile.exists())
			throw new FileNotFoundException();

		levelsInPath = myFile.list();
		if (levelsInPath.length == 0)
			throw new NoLevelsFoundOnFolderException();

		levelsInPath = sortFilesArray(levelsInPath);
		for (int i = 0; i < levelsInPath.length && levelsInPath[i] != null; i++) {
			LevelPasswordParser pwp = new LevelPasswordParser();
			String pass = pwp.parsePassword(new BufferedReader(new FileReader(
					path + File.separatorChar + levelsInPath[i])));
			passwords.put(i + 1, pass);
		}
		if (!checkPasswordsDontRepeat())
			throw new RepeatedPasswordsException();
	}

	/* Checks if there are repeated passwords between the files */
	private boolean checkPasswordsDontRepeat() {
		Set<Integer> keys = new HashSet<Integer>();
		keys = passwords.keySet();

		for (int i = 0; i < keys.size(); i++)
			for (int j = i + 1; j < keys.size(); j++)
				if (passwords.get(i + 1).equals(passwords.get(j + 1)))
					return false;
		return true;

	}

	/**
	 * Returns a parsed level with its board, its password and its player
	 * 
	 * @param levelNumber
	 *            Level to be parsed
	 * @return Level
	 * @throws ErrorWhileReadingFileException
	 * @throws IOException
	 * @throws NoSizeFoundOnFileException
	 *             if the file doesn't contain the line specifying the size of
	 *             the board
	 * @throws PlayerMustBeOnBlankCellException
	 *             if the player is placed over another cell other than a blank
	 *             one
	 * @throws PointAllreadyContainsCellException
	 *             if a cell is placed over another one
	 */
	public Level parseALevel(int levelNumber)
			throws ErrorWhileReadingFileException, IOException,
			NoSizeFoundOnFileException, PlayerMustBeOnBlankCellException,
			PointAllreadyContainsCellException {
		if (levelNumber - 1 >= levelsInPath.length || levelNumber <= 0)
			throw new IllegalArgumentException();
		Level l = new Level();
		l.setPassword(passwords.get(levelNumber));
		String fileToParse = path + File.separatorChar
				+ levelsInPath[levelNumber - 1];
		parse(fileToParse, levelNumber, l);
		return l;

	}

	/*
	 * This method is the one parses one file. Builds the board, adds the
	 * password to the level-password map and creates the player, although this
	 * isn't done directly by him. Uses the classes ParseCell and ParsePlayer.
	 * 
	 * @param file This is the path of the file to be read. Since the path was
	 * checked by the calling method, THER SHOULDN'T BE IOException.
	 * 
	 * @throws NoSizeFoundOnFile If the size of the board is not found, or read
	 * incorrectly.
	 */
	private void parse(String file, int levelNumber, Level l)
			throws ErrorWhileReadingFileException, IOException,
			NoSizeFoundOnFileException, PlayerMustBeOnBlankCellException,
			PointAllreadyContainsCellException {

		Board retBoard;
		Point dim;

		BufferedReader readFrom = new BufferedReader(new FileReader(file));
		// Read Board size
		BoardSizeParser p = new BoardSizeParser();
		dim = p.parseBoardSize(readFrom);
		retBoard = new Board(dim.x, dim.y);

		// Steps over the password
		if (!readLineAvoidComments(readFrom).equals(passwords.get(levelNumber)))
			throw new ErrorWhileReadingFileException();

		// Read all element lines
		String read;
		boolean playerExists = false;
		while ((read = readLineAvoidComments(readFrom)) != null) {

			ParseCell pc = new ParseCell();
			read = read.replaceAll(" ", "");
			read = read.replaceAll("\t", "");
			if (read.startsWith("1") && !playerExists) {
				playerExists = true;
				ParsePlayer pp = new ParsePlayer(retBoard);
				pp.parsePlayer(read);
				l.setPlayer((pp.getPlayer()));
			} else {
				pc.parseCell(read, l);
				Cell c = pc.getCell();
				c.setLevel(l);
				retBoard.add((pc.getLocation().x), (pc.getLocation().y), c);
			}
		}
		if (!playerExists)
			throw new ErrorWhileReadingFileException();
		fillNulls(retBoard);
		Point pLoc = l.getPlayer().getLocation();
		if (retBoard.getElemIn(pLoc.x, pLoc.y).getClass() != BlankCell.class)
			throw new PlayerMustBeOnBlankCellException();
		readFrom.close();
		l.setBoard(retBoard);
	}

	/*
	 * This method will fill with BlankCells the empty spaces left on the board
	 * once all the file is parsed. Although this method has the throws clause,
	 * this exception should never be thrown. This is because this exception is
	 * thrown by Board if and only if its trying to add a cell where there is
	 * another one. Since this class is asking if the position in which it will
	 * add is null, this SHOUD NEVER HAPPEN.
	 */
	private void fillNulls(Board b) throws PointAllreadyContainsCellException {
		for (int i = 0; i < b.getRows(); i++)
			for (int j = 0; j < b.getCols(); j++) {
				if (b.getElemIn(i, j) == null)
					try {
						b.add(i, j, new BlankCell(new Point(i, j)));
					} catch (PointAllreadyContainsCellException e) {
						throw e;
					}
			}
	}

	/*
	 * Sorts an array of Strings in ascending order. The strings represent the
	 * names of the files. Note that the name of the files must respond to the
	 * following pattern N.txt, where N is positive number.
	 */
	private String[] sortFilesArray(String[] array) {
		String[] levels = array;
		ArrayList<String> onlyLevels = new ArrayList<String>();
		for (int i = 0; i < levels.length; i++) {
			int j = 0;
			if (levels[i].matches("[0-9]*.txt"))
				onlyLevels.add(j++, levels[i]);
		}
		String[] ret = new String[onlyLevels.size()];
		onlyLevels.toArray(ret);
		int length = ret.length;
		for (int i = 0; i < length && ret[i] != null; ++i) {
			for (int j = length - 1; j >= i && ret[j] != null; j--) {
				String s = ret[j].substring(0, ret[j].length() - 4);
				String s2 = ret[i].substring(0, ret[i].length() - 4);
				Integer num1 = Integer.parseInt(s);
				Integer num2 = Integer.parseInt(s2);
				if (num1 < num2) {
					String aux = ret[j];
					ret[j] = ret[i];
					ret[i] = aux;
				}
			}
		}
		return ret;
	}

	/**
	 * Gets the passwords map.
	 * 
	 * @return HashMap<Integer,String> with passwords associated with level
	 *         numbers
	 */
	public HashMap<Integer, String> getPasswords() {
		return passwords;
	}
}
