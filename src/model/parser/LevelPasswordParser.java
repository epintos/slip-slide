package model.parser;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

import exceptions.ErrorWhileReadingFileException;


public class LevelPasswordParser extends GenericParser {

	/**
	 * Reads the second line of the File that is not a comment. This second line
	 * is taken as the password, no matter what it contains.
	 * 
	 * @param readFrom
	 *            BufferedReader from where the method will read.
	 * @return String representing the password of the level
	 * @throws IOException
	 * @throws ErrorWhileReadingFileException
	 *             if the end of the file was reached when it wasn't supposed to
	 */
	public String parsePassword(BufferedReader readFrom) throws IOException,
			ErrorWhileReadingFileException {
		try {
			readLineAvoidComments(readFrom); // steps over the dimension
			String passWord = readLineAvoidComments(readFrom);
			return passWord;
		} catch (EOFException e) {
			throw new ErrorWhileReadingFileException();
		}
	}
}
