package frontend;

import exceptions.NoSizeFoundOnFileException;
import exceptions.PlayerMustBeOnBlankCellException;
import exceptions.PointAllreadyContainsCellException;
import frontend.graphics.Frame;
import frontend.graphics.gui.GamePanel;
import frontend.graphics.gui.ImageUtils;
import frontend.graphics.gui.Position;
import frontend.graphics.gui.Sprite;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.game.Game;
import model.interfaces.KeyListener;

/**
 * This class implements the KeyListener interface, which is the one that communicates back end with front end
 */
public class KeyListenerImplementation implements KeyListener {

	private Frame frame;
	private Game game;
	private GamePanel gamePanel;
	private static final int CELL_SIZE = 30;

	public KeyListenerImplementation(Frame frame, Game game, GamePanel gamePanel) {
		this.frame = frame;
		this.game = game;
		this.gamePanel = gamePanel;
	}

	@Override
	public void playerTeleport(Point newLocation) {
		if (frame.existsGamePanel()) {
			gamePanel.translateSprite(frame.getPlayerSprite(), new Position(
					CELL_SIZE * (int) (newLocation.getY() - 1), CELL_SIZE
							* (int) (newLocation.getX() - 1)));
		}
	}

	@Override
	public void playerMoved(Point newPoint) {
		if (frame.existsGamePanel()) {
			gamePanel.moveSprite(frame.getPlayerSprite(), new Position(
					CELL_SIZE * (int) (newPoint.getY() - 1), CELL_SIZE
							* (int) (newPoint.getX() - 1)));
			game.getActualLevel().incrementCellsCrossed();
		}
	}

	@Override
	public void removeElement(Point point) {
		frame.removeSprite(point);
	}

	@Override
	public void changeBreakableWallLeftTries(Point location, int leftTries) {
		Sprite oldSprite = frame.getSprite(new Position(CELL_SIZE
				* (location.y - 1), CELL_SIZE * (location.x - 1)));
		Image newImageBlank = null;
		try {
			newImageBlank = ImageUtils.loadImage("resources/images/wall.png");
		} catch (IOException e) {
			frame.errorWhileReading("loading image");
		}
		Image newImage = ImageUtils.drawString(newImageBlank,
				String.valueOf(leftTries), Color.BLACK);
		gamePanel.changeSpriteImage(oldSprite, newImage);
	}

	@Override
	public void levelIsOver() {
		JOptionPane panel = new JOptionPane();
		long time = System.currentTimeMillis()
				- game.getActualLevel().getTime();
		int oldPoints = game.getActualLevel().getPoints();
		int cellsCrossed=game.getActualLevel().getCellsCrossed();
		if (!game.isOver()) {
			try {
				game.passToNextLevel();
			} catch (NoSizeFoundOnFileException e) {
				frame.errorWhileReading("reading levels size.");
			} catch (PlayerMustBeOnBlankCellException e) {
				frame.errorWhileReading("reading levels, the player is over another sprite.");
			} catch (FileNotFoundException e) {
				frame.errorWhileReading("reading levels, file not found.");
			} catch (PointAllreadyContainsCellException e) {
				frame.	errorWhileReading("reading levels, two or more sprites are on the same place.");
			} catch (IOException e) {
				frame.errorWhileReading("reading file.");
			} catch (Exception e) {
				frame.errorWhileReading("reading levels, file corrupt.");
			}
			JOptionPane.showMessageDialog(panel,
					"Password Level Nº " + (game.getActualLevelNumber()) + ": "
							+ (game.getActualLevel()).getPassword() + "\n"
							+ "Time: " + (time / 1000) + " seconds" + "\n"
							+ "Points: " + oldPoints + "\n" + "Cells crossed: "
							+ cellsCrossed, "Level Complete!",
					JOptionPane.PLAIN_MESSAGE);
			game.getActualLevel().resetCellsCrossed();
			frame.restartTo(game.getActualLevelNumber());

		} else {
			JOptionPane.showMessageDialog(panel,
					"Congratulations, you have won all the levels!" + "\n"
							+ "Time: " + (time / 1000) + " seconds" + "\n"
							+ "Points: " + oldPoints + "\n" + "Cells crossed: "
							+ cellsCrossed, "Game Complete!",
					JOptionPane.INFORMATION_MESSAGE);
			frame.refreshToMenu();
		}
	}
	
	@Override
	public void transporterLoop() {
		JOptionPane.showMessageDialog(null,"You've entered in an endless way", "You Loose!",
				JOptionPane.ERROR_MESSAGE);
		frame.restartTo(game.getActualLevelNumber());
		
	}
}
