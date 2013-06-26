package frontend.graphics;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.game.Game;
import exceptions.ErrorWhileReadingFileException;
import exceptions.NoLevelsFoundOnFolderException;
import exceptions.NoSizeFoundOnFileException;
import exceptions.PlayerMustBeOnBlankCellException;
import exceptions.PointAllreadyContainsCellException;
import exceptions.RepeatedPasswordsException;

public class App {

	public static void main(String[] args) {
		try {
			Game game = new Game();
			Frame frame = new Frame(game);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setVisible(true);
		} catch (NoSizeFoundOnFileException e) {
			errorWhileReading("reading levels size.");
		} catch (RepeatedPasswordsException e) {
			errorWhileReading("reading levels passwords, there are repeated.");
		} catch (PlayerMustBeOnBlankCellException e) {
			errorWhileReading("reading levels, the player is over another sprite.");
		} catch (FileNotFoundException e) {
			errorWhileReading("reading levels, file not found.");
		} catch (PointAllreadyContainsCellException e) {
			errorWhileReading("reading levels, two or more sprites are on the same place.");
		} catch (IOException e) {
			errorWhileReading("reading file.");
		} catch (NoLevelsFoundOnFolderException e) {
			errorWhileReading("loading levels. Any level found on folder ./levels.");
		}catch(ErrorWhileReadingFileException e){
			errorWhileReading("reading file.");
		}

	}

	private static void errorWhileReading(String string) {
		JOptionPane.showMessageDialog(null, "Error at " + string,
				"Slip & Slide Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
}
