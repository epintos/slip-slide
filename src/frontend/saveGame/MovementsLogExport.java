package frontend.saveGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.saveGame.MovementsLog;

/**
 * This class saves the movements done by the player while playing a level into a file.
 */
public class MovementsLogExport {

	MovementsLog moveLogExport;

	/**
	 * Writes in a file the necessary data in order to recreate the played
	 * level. This method receives a MovementsLog instance, which is the one
	 * that has all the actions done during the playing of a level. It also
	 * receives a file in which to write.
	 * 
	 * @param moveLogExport
	 *            instance of the class in charge of keeping a log of the
	 *            actions done
	 * @param file
	 *            file in which to write the log
	 * @throws IOException
	 */
	public MovementsLogExport(MovementsLog moveLogExport, File file)
			throws IOException {
		this.moveLogExport = moveLogExport;
		BufferedWriter br = new BufferedWriter(new FileWriter(file));

		br.write(moveLogExport.getPassword());
		br.write(";");
		br.write(String.valueOf(moveLogExport.getTime()));
		br.write(";");
		ArrayList<String> log = moveLogExport.getLog();
		for (String each : log) {
			br.write(each);
			br.write(";");
		}

		br.close();
	}
}
