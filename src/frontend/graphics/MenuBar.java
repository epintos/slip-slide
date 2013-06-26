package frontend.graphics;

import exceptions.ErrorWhileReadingFileException;
import exceptions.NoSuchPasswordException;
import frontend.KeyListenerImplementation;
import frontend.graphics.gui.GamePanel;
import frontend.graphics.gui.ImageUtils;
import frontend.saveGame.MovementsLogExport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import model.game.Game;
import model.saveGame.MovementsLogImport;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	private JMenuBar menuBar;
	private Frame frame;
	private Game game;
	private GamePanel gamePanel;

	public MenuBar(Frame frame, Game game, GamePanel gamePanel) {
		this.frame = frame;
		this.game = game;
		this.gamePanel = gamePanel;

		menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		/*
		 * Creates the New Game option, initializes the Key Listener and
		 * restarts to level 1
		 */
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setMnemonic(KeyEvent.VK_N);
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getFrame().restartTo(1);
			}
		});

		/*
		 * Creates a Pane that asks for a password, if found it, restarts the
		 * game to the level that matches with that password
		 */
		JMenuItem insertPassword = new JMenuItem("Insert Password");
		insertPassword.setMnemonic(KeyEvent.VK_I);
		insertPassword.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.CTRL_MASK));
		insertPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				boolean correct = false;
				String insertedPassword = "";
				while (!correct & insertedPassword != null) {
					insertedPassword = (String) JOptionPane.showInputDialog(
							getParent(), "Insert Password:", "",
							JOptionPane.PLAIN_MESSAGE, null, null, "");
					if (insertedPassword != null) {
						correct = getGame().searchPasswordExistance(
								insertedPassword);
					}
				}
				int levelFound;
				try {
					if (insertedPassword != null) {
						levelFound = getGame().searchPassword(insertedPassword);
						getFrame().restartTo(levelFound);
					}
				} catch (NoSuchPasswordException e) {
					getFrame().errorWhileReading("getting password");
				}
			}
		});

		/*
		 * Loads a level and starts playing with the load data
		 */
		JMenuItem loadGame = new JMenuItem("Load Game");
		loadGame.setMnemonic(KeyEvent.VK_O);
		loadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		loadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileopen = new JFileChooser();
				int ret = fileopen.showDialog(getParent(), "Open File");
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileopen.getSelectedFile();
					try {
						MovementsLogImport moveLogIm = new MovementsLogImport(
								file);
						getGame().setKeyListener(
								new KeyListenerImplementation(getFrame(),
										getGame(), getGamePanel()));
						getFrame().newLoadedGame(moveLogIm.getPassword(),
								moveLogIm.getTime(), moveLogIm.getActions());
					} catch (FileNotFoundException e) {
						getFrame().errorWhileReading(
								"loading game, file not found");
					} catch (NoSuchPasswordException e) {
						getFrame().errorWhileReading("getting password");
					} catch (ErrorWhileReadingFileException e) {
						getFrame().errorWhileReading(
								"reading file.");
					}
				}
			}
		});

		/*
		 * Resets the actual level
		 */
		JMenuItem resetLevel = new JMenuItem("Reset Level");
		resetLevel.setMnemonic(KeyEvent.VK_R);
		resetLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		resetLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (getFrame().getActualLevel() != null) {
					int oldLevelNumber = getGame().getActualLevelNumber();
					getFrame().restartTo(oldLevelNumber);
				}
			}
		});

		/*
		 * Solves the actual level
		 */
		JMenuItem solveLevel = new JMenuItem("Solve Level");
		solveLevel.setMnemonic(KeyEvent.VK_H);
		solveLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));
		solveLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File file = new File("." + File.separator + "Solutions"
						+ File.separator + "Sol"
						+ getGame().getActualLevelNumber() + ".txt");
				try {
					MovementsLogImport moveLogIm = new MovementsLogImport(file);
					getGame().setKeyListener(
							new KeyListenerImplementation(getFrame(),
									getGame(), getGamePanel()));
					getFrame().newLoadedGame(moveLogIm.getPassword(),
							moveLogIm.getTime(), moveLogIm.getActions());
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Solution not found",
							"ERROR!", JOptionPane.ERROR_MESSAGE);
				} catch (NoSuchPasswordException e) {
					getFrame().errorWhileReading("getting password");
				} catch (ErrorWhileReadingFileException e) {
					getFrame().errorWhileReading(
							"reading file.");
				}
			}
		});

		/*
		 * Saves a game, must be .txt
		 */
		JMenuItem saveGame = new JMenuItem("Save Game");
		saveGame.setMnemonic(KeyEvent.VK_S);
		saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		saveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (getFrame().getActualLevel()!=null){
				JFileChooser fileChooser = new JFileChooser();
				int ret = fileChooser.showSaveDialog(getParent());
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					getGame().save();
					try {
						new MovementsLogExport(getGame().getActualLevel()
								.getMoveLog(), file);
					} catch (IOException e) {
						getFrame().errorWhileReading("saving game");
					}
				}
				}else{
					getFrame().errorWhileReading("saving game, no levels where loaded.");
				}
			}
		});

		/*
		 * Returns the game to the main menu
		 */
		JMenuItem cancelGame = new JMenuItem("Cancel Game");
		cancelGame.setMnemonic(KeyEvent.VK_E);
		cancelGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));
		cancelGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getFrame().refreshToMenu();
			}
		});

		/*
		 * Closes the application
		 */
		JMenuItem close = new JMenuItem("Close");
		close.setMnemonic(KeyEvent.VK_C);
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		JMenuItem about = new JMenuItem("About");
		about.setMnemonic(KeyEvent.VK_A);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getParent(),
						"Slip & Slide was developed by:\n"
								+ " -Esteban Guido Pintos\n"
								+ " -Matias Ezequiel De Santi",
						"About Slip & Slide", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		/*
		 * Frame with the instructions of the game
		 */
		JMenuItem intructions = new JMenuItem("Instructions");
		intructions.setMnemonic(KeyEvent.VK_C);
		intructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		intructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFrame instFrame = new JFrame();
				instFrame.setSize(425, 432);
				JTextPane instPane = new JTextPane();
				instPane.setBounds(0, 0, 420, 432);
				instPane.setText("This game consists on getting to the destiny cell, "
						+ "but for this, the player has to move slipping until "
						+ "it reaches an special cell, which can be:\n\n"
						+ "- Breakable Wall: Wall with a number, that can be "
						+ "passed through only if it's number is zero. Every time the "
						+ "player tries to pass through it, the number will "
						+ "decrease until it gets to one and then breaks. Now "
						+ "the player can pass.\n\n"
						+ "- Walls: The player can't pass this walls.\n\n"
						+ "- Transporters: If the player get's into it, it will "
						+ "continue it's movements but in the destiny "
						+ "transporter. If a wall blocks the way of the "
						+ "destiny, it won't be able to pass.\n\n"
						+ "- Coins: When a player collects a coin, "
						+ "points are added according to its value.\n\n"
						+ "- Keys and Doors: When a player collects a "
						+ "key, the corresponding door with "
						+ "the same color will open letting "
						+ "the player pass through it.\n\n"
						+ "- Destiny: Cell where the player needs "
						+ "to reach in order to win the level\n\n"
						+ "A level or the game can be restarted at any "
						+ "moment, and also, actions can be saved"
						+ " and then loaded in orden to continue at any time.\n"
						+ "In every level, time, movements and points are counted.");
				instPane.setEditable(false);
				instFrame.add(instPane);
				instFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				try {
					instFrame.setIconImage(ImageUtils
							.loadImage("resources/images/player.png"));
				} catch (IOException e) {
					getFrame().errorWhileReading("loading image");
				}
				instFrame.setTitle("Slip & Slide Instructions");
				instFrame.setResizable(false);
				instFrame.setVisible(true);
			}
		});

		// Ads the sub menus to the menu
		gameMenu.add(newGame);
		gameMenu.add(insertPassword);
		gameMenu.add(loadGame);
		gameMenu.add(saveGame);
		gameMenu.add(solveLevel);
		gameMenu.add(resetLevel);
		gameMenu.add(cancelGame);
		gameMenu.add(close);
		helpMenu.add(about);
		helpMenu.add(intructions);
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
	}

	/**
	 * Returns the menuBar
	 * 
	 * @return JMenuBar
	 */
	public JMenuBar getMenu() {
		return menuBar;
	}

	private Frame getFrame() {
		return frame;
	}

	private Game getGame() {
		return game;
	}

	private GamePanel getGamePanel() {
		return gamePanel;
	}

}
