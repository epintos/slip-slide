package tests;

import java.awt.Point;

import junit.framework.Assert;
import model.cells.BreakableWall;
import model.interfaces.KeyListener;
import model.level.Level;
import model.player.Player;

import org.junit.Before;
import org.junit.Test;

public class TestBreakableWall {

	private BreakableWall bw;
	private Level l;
	private boolean leftTriesChanged = false;
	private boolean wallRemoved = false;

	@Before
	public void Initialize() {
		bw = new BreakableWall(new Point(1, 1), 3);
		l = new Level();
		l.setPlayer(new Player(1, 1, null));
		l.setKeyListener(new KeyListener() {
			@Override
			public void changeBreakableWallLeftTries(Point location,
					int leftTries) {
				leftTriesChanged = true;
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
				wallRemoved = true;
			}

			@Override
			public void transporterLoop() {

			}
		});
		bw.setLevel(l);
	}

	@Test
	public void TestLeftTries() {
		Assert.assertEquals(3, bw.getLeftTries());
		bw.canPass();
		Assert.assertEquals(true, leftTriesChanged);
		leftTriesChanged = false;
		Assert.assertEquals(2, bw.getLeftTries());
		bw.canPass();
		Assert.assertEquals(true, leftTriesChanged);
		leftTriesChanged = false;
		Assert.assertEquals(1, bw.getLeftTries());
	}

	@Test
	public void TestCanPass() {
		Assert.assertEquals(false, bw.canPass());
		Assert.assertEquals(false, bw.canPass());
		Assert.assertEquals(false, bw.canPass());
		Assert.assertEquals(true, bw.canPass());
		Assert.assertEquals(true, wallRemoved);
	}

}
