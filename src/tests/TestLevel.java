package tests;

import java.awt.Point;

import junit.framework.Assert;
import model.board.Board;
import model.cells.BlankCell;
import model.cells.BreakableWall;
import model.cells.Transporter;
import model.cells.Wall;
import model.interfaces.KeyListener;
import model.level.Level;
import model.player.Player;

import org.junit.Before;
import org.junit.Test;

import exceptions.PointAllreadyContainsCellException;

public class TestLevel {

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
	public void TestCellsCrossed() {
		level.incrementCellsCrossed();
		Assert.assertEquals(1, level.getCellsCrossed());
		level.resetCellsCrossed();
		Assert.assertEquals(0, level.getCellsCrossed());

	}

	@Test
	public void TestPassword() {
		Assert.assertEquals("testPassword", level.getPassword());
		level.setPassword("testNewPassword");
		Assert.assertEquals("testNewPassword", level.getPassword());
	}

	@Test
	public void TestTime() {
		level.addSavedTime(5);
		Assert.assertEquals(-5, level.getTime());
	}

	@Test
	public void TestPoints() {
		level.addPoints(50);
		Assert.assertEquals(50, level.getPoints());
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
