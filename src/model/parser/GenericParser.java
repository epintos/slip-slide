package model.parser;

import java.io.BufferedReader;
import java.io.IOException;
import model.level.Level;

public abstract class GenericParser {

	/**
	 * Reads and returns one line from the file that is not a comment Note that
	 * it also jumps empty lines in the file
	 * 
	 * @param readFrom
	 *            is the BufferedReader from which the method must read from
	 * 
	 * @throws EndOfFileReachedException
	 *             if the end of the file is reached.
	 */
	protected String readLineAvoidComments(BufferedReader readFrom)
			throws IOException {
		String read;
		boolean EOF = false;

		do {
			read = readFrom.readLine();
			if (read == null)
				EOF = true;
		} while (!EOF && (read.startsWith("#") || read.equals("") || read.equals(" ") || read.matches("[ ]*#.*")));
		
		if(EOF)
			return null;
		read= read.replaceAll(" ", "")
				  .replaceAll("\t", "")
				  .replaceAll("#.*", "");
		return read;
	}

	private Level level;

	/**
	 * Getter of the property <tt>level</tt>
	 * @return   Returns the level.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Setter of the property <tt>level</tt>
	 * @param level   The level to set.
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

}
