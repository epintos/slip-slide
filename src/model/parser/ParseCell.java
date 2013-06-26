package model.parser;

import java.awt.Point;

import exceptions.ErrorWhileReadingFileException;

import model.cells.BreakableWall;
import model.cells.Cell;
import model.cells.Coin;
import model.cells.Destiny;
import model.cells.Door;
import model.cells.Key;
import model.cells.Transporter;
import model.cells.Wall;
import model.level.Level;

public class ParseCell extends GenericParser {

	private Point location;
	private Cell cell;
	private final char WALL = '2';
	private final char BREAKWALL = '3';
	private final char COIN = '4';
	private final char KEYDOOR = '5';
	private final char TRANSCELL = '6';
	private final char DEST = '7';

	/**
	 * Receives a string which must be a line read from the File. The line must
	 * respond to this format: A,B,C,D,E,F where the letters represent numbers.
	 * There can be spaces or tabs before and after each number. The method will
	 * first verify that the string responds to that pattern.
	 * 
	 * @param str
	 *            String from where to read the numbers
	 * @throws ErrorWhileReadingFileException
	 * 
	 * @throws IllegalArgumentException
	 *             If the string does not respond to the pattern mentioned above
	 */
	public void parseCell(String str, Level l)
			throws ErrorWhileReadingFileException {
		
		String[] array = str.split(",");
		int x = Integer.parseInt(array[1]) + 1;
		int y = Integer.parseInt(array[2]) + 1;
		if (x < 0 || y < 0 || array.length != 6)
			throw new ErrorWhileReadingFileException();
		location = new Point(x, y);
		switch (str.charAt(0)) {
		case WALL:
			parseWall(array);
			cell = new Wall(location);
			break;
		case BREAKWALL:
			parseBWall(array);
			break;
		case COIN:
			parseCoin(array);
			break;
		case KEYDOOR:
			parseKD(array);
			break;
		case TRANSCELL:
			parseTC(array, l);
			break;
		case DEST:
			parseWall(array);
			cell = new Destiny(location);
			break;
		}

	}

	/**
	 * Returns the cell
	 * @return Cell
	 */
	public Cell getCell() {
		return cell;
	}

	/**
	 * Returns the location
	 * @return Point
	 */
	public Point getLocation() {
		return location;
	}

	private void parseTC(String[] str, Level l)
			throws ErrorWhileReadingFileException {
		int x = Integer.parseInt(str[3]) + 1;
		int y = Integer.parseInt(str[4]) + 1;
		if (y < 0 || x < 0 || Integer.parseInt(str[5]) != 0)
			throw new ErrorWhileReadingFileException();
		Point associatedCell = new Point(x, y);
		Point p = new Point(location.x, location.y);
		Transporter cell2 = new Transporter(associatedCell);
		cell2.setLevel(l);
		Transporter t = new Transporter(p, cell2);
		cell = t;

	}

	/*
	 * Checks that after the 3 numbers indicating the game element and the
	 * location, come 3 consecutive 0s
	 */
	private void parseWall(String[] str) throws ErrorWhileReadingFileException {
		if (!str[3].equals("0") || !str[4].equals("0") || !str[5].equals("0"))
			throw new ErrorWhileReadingFileException();
	}

	private void parseKD(String[] str) throws ErrorWhileReadingFileException {
		int x = Integer.parseInt(str[3]) + 1;
		int y = Integer.parseInt(str[4]) + 1;
		if( x < 0 || y < 0 || !str[5].equals("0"))
			throw new ErrorWhileReadingFileException();
		Point associatedDoor = new Point(x,y);
		Door d = new Door(associatedDoor);
		Point p = new Point(location.x, location.y);
		cell = new Key(p, d);
	}

	private void parseBWall(String[] str) throws ErrorWhileReadingFileException {
		if (!str[3].equals("0") || !str[4].equals("0"))
			throw new ErrorWhileReadingFileException();
		cell = new BreakableWall(location, Integer.parseInt(str[5]));
	}

	private void parseCoin(String[] str) throws ErrorWhileReadingFileException {
		if (!str[3].equals("0") || !str[4].equals("0"))
			throw new ErrorWhileReadingFileException();
		cell = new Coin(Integer.parseInt(str[5]), location);
	}

}
