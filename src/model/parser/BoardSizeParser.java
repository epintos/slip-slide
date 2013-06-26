package model.parser;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;

import exceptions.ErrorWhileReadingFileException;
import exceptions.NoSizeFoundOnFileException;


/**
 * This class is the one in charge of parsing the size of the board in the file
 * 
 */
public class BoardSizeParser extends GenericParser {

	/**
	 * Reads the first line of the file that is not a comment and searches for
	 * the size of the board. In the text, the size must respond to: N,M where N
	 * and M are positive numbers.
	 * 
	 * @param readFrom
	 *            Is a BufferedReader from where the method will read to extract
	 *            the size of the board
	 * @return A point indicating the size in ROWS,COLS.
	 * @throws IOException
	 * @throws EndOfFileReachedException
	 * @throws NoSizeFoundOnFileException
	 * @throws ErrorWhileReadingFileException
	 */
	public Point parseBoardSize(BufferedReader readFrom) throws IOException,
			NoSizeFoundOnFileException, ErrorWhileReadingFileException {

		String read;
		read = super.readLineAvoidComments(readFrom);
		// size expected to be in read
		Point size = getSize(read);
		return size;
	}

	private Point getSize(String read) throws NoSizeFoundOnFileException,
			ErrorWhileReadingFileException {
		read = read.replaceAll(" ", "");
		read = read.replaceAll("\t", "");
		String[] points = read.split(",");
		if (points.length != 2)
			throw new NoSizeFoundOnFileException();
		int x = Integer.parseInt(points[0]);
		int y = Integer.parseInt(points[1]);
		if (x <= 0 || y <= 0)
			throw new ErrorWhileReadingFileException();
		Point p = new Point(x, y);

		return p;
	}

}
