package model.saveGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.ErrorWhileReadingFileException;


/**
 * This class is the one in charge of reading a file where a game was saved.
 */
public class MovementsLogImport {

	private ArrayList<String> actions;
	private String password;
	private long time;

	/**
	 * Reads from file the password of the level, the time and the actions and
	 * initializes them
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 * @throws ErrorWhileReadingFileException
	 */
	public MovementsLogImport(File file) throws FileNotFoundException,
			ErrorWhileReadingFileException {

		BufferedReader br = new BufferedReader(new FileReader(file));
		actions = new ArrayList<String>();
		String read = null;
		try {
			read = br.readLine();
		} catch (IOException e) {
			throw new ErrorWhileReadingFileException();
		}
		String[] splitted = read.split(";");

		if (splitted.length < 2)
			throw new ErrorWhileReadingFileException();
		password = splitted[0];
		time = Long.parseLong(splitted[1]);
		int i = 2;
		while (i < splitted.length) {
			String aux = splitted[i];
			if ((aux.equals("DOWN") || aux.equals("UP") || aux.equals("RIGHT") || aux
					.equals("LEFT")))
				actions.add(splitted[i++]);
			else
				throw new ErrorWhileReadingFileException();
		}
	}

	/**
	 * Returns an array list with the actions
	 * @return   ArrayList
	 */
	public ArrayList<String> getActions() {
		return actions;
	}

	/**
	 * Returns password
	 * @return   password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns time in milliseconds
	 * @return   time
	 */
	public long getTime() {
		return time;
	}

}
