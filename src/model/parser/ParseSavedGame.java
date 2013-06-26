package model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseSavedGame {

	private BufferedReader br;
	private String password;
	private ArrayList<String> actions;
	private int time;

	public ParseSavedGame(File file) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(file));
		actions = new ArrayList<String>();
	}

	public void parseFile() throws IOException {
		password = br.readLine();
		time = Integer.parseInt(br.readLine());
		String read;
		while ((read = br.readLine()) != null)
			actions.add(read);
		br.close();
	}

	/**
	 * @return an ArrayList containing the actions taken by the player
	 */
	public ArrayList<String> getActions() {
		return actions;
	}

	/**
	 * @return a string representing the password of the level played
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the time taken for the player to play the level
	 */
	public int getTime() {
		return time;
	}

}
