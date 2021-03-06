package frontend.graphics;

import exceptions.ErrorWhileReadingFileException;
import exceptions.NoSizeFoundOnFileException;
import exceptions.NoSuchPasswordException;
import exceptions.PlayerMustBeOnBlankCellException;
import exceptions.PointAllreadyContainsCellException;
import frontend.Colors;
import frontend.KeyListenerImplementation;
import frontend.graphics.gui.GamePanel;
import frontend.graphics.gui.ImageUtils;
import frontend.graphics.gui.Position;
import frontend.graphics.gui.Sprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.cells.BlankCell;
import model.cells.BreakableWall;
import model.cells.Cell;
import model.cells.Coin;
import model.cells.Destiny;
import model.cells.Door;
import model.cells.Key;
import model.cells.Transporter;
import model.cells.Wall;
import model.game.Game;
import model.interfaces.Paintable;
import model.level.Level;

/**
 * This class is the one in charge of building the frame for the graphic interface
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {
	private static final int CELL_SIZE = 30;
	private int rows = 12;
	private int cols = 12;
	private GamePanel gamePanel;
	private Sprite sprite;
	private List<Sprite> sprites;
	private Game game;
	private Level actualLevel;
	private HashMap<Class<?>, Image> classMap;

	public Frame(Game game) throws IOException {
		this.game = game;

		// Initializes MenuBar
		MenuBar menuBar = new MenuBar(this, game, gamePanel);
		setJMenuBar(menuBar.getMenu());

		// Initializes basic Frame and MapHash of images
		setTitle("Slip & Slide");
		setLayout(null);
		setIconImage(ImageUtils.loadImage("resources/images/player.png"));
		initializeFrame();
		initializeClassMap();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (actualLevel != null) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						actualLevel.moveLeft();
						break;
					case KeyEvent.VK_UP:
						actualLevel.moveUp();
						break;
					case KeyEvent.VK_RIGHT:
						actualLevel.moveRight();
						break;
					case KeyEvent.VK_DOWN:
						actualLevel.moveDown();
						break;
					}
				}
			}
		});

	}

	/**
	 * Returns the player's sprite
	 * 
	 * @return Sprite
	 */
	public Sprite getPlayerSprite() {
		return this.sprite;
	}

	private void initializeClassMap() throws IOException {
		classMap = new HashMap<Class<?>, Image>();
		classMap.put(Key.class,
				ImageUtils.loadImage("resources/images/key.png"));
		classMap.put(BreakableWall.class,
				ImageUtils.loadImage("resources/images/wall.png"));
		classMap.put(Wall.class,
				ImageUtils.loadImage("resources/images/wall.png"));
		classMap.put(Door.class,
				ImageUtils.loadImage("resources/images/door.png"));
		classMap.put(Transporter.class,
				ImageUtils.loadImage("resources/images/teleporter.png"));
		classMap.put(Coin.class,
				ImageUtils.loadImage("resources/images/coin.png"));
		classMap.put(Destiny.class,
				ImageUtils.loadImage("resources/images/target.png"));
	}

	private Image getImage(Cell cell) {
		Image image = classMap.get(cell.getClass());
		if (cell instanceof BreakableWall) {
			String s = String.valueOf(((BreakableWall) cell).getLeftTries());
			return ImageUtils.drawString(image, s, Color.BLACK);
		}
		if (cell instanceof Paintable)
			image = ImageUtils.colorize(image,
					Colors.values()[((Paintable) cell).getID()].getColor());
		return image;
	}

	/**
	 * Sets the actualLevel
	 */
	public void setActualLevel(Level level) {
		this.actualLevel = level;
	}

	/*
	 * Initializes the Frame according to a column and a row and positions it in
	 * the center of the screan
	 */
	private void initializeFrame() {
		setSize(cols * CELL_SIZE - 54, rows * CELL_SIZE - 6);
		Dimension screan = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window = this.getSize();
		setLocation((screan.width - window.width) / 2,
				(screan.height - window.height) / 2);
		paintAll(this.getGraphics());
	}

	/**
	 * Initializes the graphic panel with sprites and if already existed one,
	 * first removes it in order to refresh
	 * 
	 * @throws IOException
	 *             If there was a problem loading the image
	 */
	public void initializePanel() throws IOException {
		rows = actualLevel.getBoard().getRows();
		cols = actualLevel.getBoard().getCols();
		sprites = new LinkedList<Sprite>();
		if (existsGamePanel())
			remove(gamePanel);
		gamePanel = new GamePanel(cols * CELL_SIZE, rows * CELL_SIZE);
		gamePanel.setBackground(Color.WHITE);
		int playerX = actualLevel.getPlayer().getLocation().x;
		int playerY = actualLevel.getPlayer().getLocation().y;
		sprite = new Sprite(
				ImageUtils.loadImage("resources/images/player.png"),
				new Position((playerY - 1) * CELL_SIZE, (playerX - 1)
						* CELL_SIZE));
		add(gamePanel);
		addSprites(actualLevel);
	}

	/**
	 * Returns true if already existed a Game Panel and false if not.
	 * 
	 * @return boolean
	 */
	public boolean existsGamePanel() {
		return gamePanel != null;
	}

	/*
	 * Add all the sprites in the board to the game panel
	 */
	private void addSprites(Level level) throws IOException {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = level.getBoard().getElemIn(i, j);
				if (!(cell instanceof BlankCell)) {
					Image image = getImage(cell);
					Sprite s = new Sprite(image, new Position(CELL_SIZE
							* (j - 1), CELL_SIZE * (i - 1)));
					gamePanel.addSprite(s);
					sprites.add(s);
				}
			}
		}
		gamePanel.addSprite(sprite);
	}

	/**
	 * Searches a sprite in the sprites list and returns it, if it doesn't found
	 * it returns null
	 * 
	 * @param position
	 * @return Sprite
	 */
	public Sprite getSprite(Position position) {
		for (Sprite s : sprites)
			if (s.getPosition().equals(position))
				return s;
		return null;
	}

	/**
	 * Removes a sprite in a point from the game panel
	 * 
	 * @param point
	 */
	public void removeSprite(Point point) {
		Position pos = new Position(CELL_SIZE * (point.y - 1), CELL_SIZE
				* (point.x - 1));
		Sprite s = getSprite(pos);
		gamePanel.removeSprite(s);
	}

	/**
	 * Resets the game actual level to a new level. Initializes the
	 * key listener. Refresh the panel and the title.
	 * 
	 * @param levelNumber
	 */
	public void restartTo(int levelNumber) {
		try {
			repaint();
			game.setActualLevel(levelNumber);
			actualLevel = game.getActualLevel();
			actualLevel.initializeTime();
			initializePanel();
			gamePanel.setPixeslPerStep(5);
			actualLevel.setKeyListener(new KeyListenerImplementation(this,
					game, gamePanel));
			game.setKeyListener(new KeyListenerImplementation(this, game,
					gamePanel));
			setTitle("Slip & Slide" + " - Level " + game.getActualLevelNumber());
			initializeFrame();
		} catch (NoSizeFoundOnFileException e) {
			errorWhileReading("reading levels size.");
		} catch (PlayerMustBeOnBlankCellException e) {
			errorWhileReading("reading levels, the player is over another sprite.");
		} catch (FileNotFoundException e) {
			errorWhileReading("reading levels, file not found.");
		} catch (PointAllreadyContainsCellException e) {
			errorWhileReading("reading levels, two or more sprites are on the same place.");
		} catch (IOException e) {
			errorWhileReading("reading file.");
		}catch(ErrorWhileReadingFileException e){
			errorWhileReading("reading file.");
		}
	}

	/**
	 * Restarts the game with the loaded game and loads the actions
	 * 
	 * @param password
	 * @param timer
	 * @param arrayList
	 * @throws NoSuchPasswordException
	 *             If the password was not found
	 */
	public void newLoadedGame(String password, long timer,
			ArrayList<String> arrayList) throws NoSuchPasswordException {
		if (game.searchPasswordExistance(password)) {
			restartTo(game.searchPassword(password));
			game.getActualLevel().addSavedTime(timer);
			for (int i = 0; i < arrayList.size(); i++) {
				if (arrayList.get(i).equals("UP")) {
					actualLevel.moveUp();
				}
				if (arrayList.get(i).equals("DOWN")) {
					actualLevel.moveDown();
				}
				if (arrayList.get(i).equals("RIGHT")) {
					actualLevel.moveRight();
				}
				if (arrayList.get(i).equals("LEFT")) {
					actualLevel.moveLeft();
				}
			}
		}

	}

	/**
	 * Creates a panel of error with a message starting in "Error at ".
	 * 
	 * @param string
	 */
	public void errorWhileReading(String string) {
		JOptionPane.showMessageDialog(null, "Error at " + string,
				"Slip & Slide Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}

	/**
	 * Returns the game to the main menu with a size of 12x12 and removes the
	 * Game Panel if it exists. Sets the actualLevel in null.
	 */
	public void refreshToMenu() {
		actualLevel = null;
		rows = 12;
		cols = 12;
		if (existsGamePanel())
			remove(gamePanel);
		repaint();
		setTitle("Slip & Slide");
		initializeFrame();
	}

	/**
	 * Returns the actualLevel
	 * @return   Level
	 */

	public Level getActualLevel() {
		return this.actualLevel;
	}
}