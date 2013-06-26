package tests;

import java.awt.Point;

import model.board.Board;
import model.cells.BlankCell;
import model.cells.BreakableWall;
import model.cells.Transporter;
import model.cells.Wall;
import model.interfaces.KeyListener;
import model.level.Level;
import model.player.Player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.PointAllreadyContainsCellException;

public class TestPlayerAndTransporter {

	private Player player;
	private Board board;
	private Level level;

	@Before
	public void initialize() throws PointAllreadyContainsCellException {
		KeyListener listener = new KeyListenerImpl();
		board = new Board(3, 3);
		this.player = new Player(1, 1, board);
		this.player.setKeyListener(listener);
		this.level = new Level(board, player, "testPassword");
		level.setKeyListener(listener);

		board.add(1, 1, (new BlankCell(new Point(1, 1))).setLevel(level));
		board.add(1, 2, (new BlankCell(new Point(1, 2))).setLevel(level));
		board.add(1, 3, (new BlankCell(new Point(1, 3))).setLevel(level));
		board.add(2, 1, (new Transporter(new Point(2, 1), new Transporter(
				new Point(2, 3)))).setLevel(level));
		board.add(2, 2, (new Wall(new Point(2, 2))).setLevel(level));
		board.add(3, 1, (new BreakableWall(new Point(3, 1), 2)).setLevel(level));
		board.add(3, 2, (new BlankCell(new Point(3, 2))).setLevel(level));
		board.add(3, 3, (new BlankCell(new Point(3, 3))).setLevel(level));
	}

	@Test
	public void TestLocation() {
		Assert.assertEquals(new Point(1, 1), player.getLocation());
		player.setLocation(1, 2);
		Assert.assertEquals(new Point(1, 2), player.getLocation());
	}

	@Test
	public void TestMove() {
		player.move(1, 0); // DOWN
		Assert.assertEquals(new Point(3, 3), player.getLocation());
		player.move(0, -1); // LEFT
		Assert.assertEquals(new Point(3, 2), player.getLocation());
		player.move(0, -1); // LEFT
		Assert.assertEquals(new Point(3, 2), player.getLocation());
		player.move(0, -1); // LEFT
		Assert.assertEquals(new Point(3, 1), player.getLocation());
	}

	private class KeyListenerImpl implements KeyListener {
		@Override
		public void changeBreakableWallLeftTries(Point location, int leftTries) {
		}

		@Override
		public void levelIsOver() {

		}

		@Override
		public void playerMoved(Point newLocation) {
		}

		@Override
		public void playerTeleport(Point newLocation) {
		}

		@Override
		public void removeElement(Point point) {
		}

		@Override
		public void transporterLoop() {

		}
	}
}
