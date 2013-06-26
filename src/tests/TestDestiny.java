package tests;

import java.awt.Point;

import junit.framework.Assert;
import model.cells.Destiny;
import model.interfaces.KeyListener;
import model.level.Level;
import model.player.Player;

import org.junit.Before;
import org.junit.Test;

public class TestDestiny {

	private Destiny target;

	private Level l;
	private boolean levelIsOver = false;

	@Before
	public void Initialize() {
		target = new Destiny(new Point(1, 1));
		l = new Level();
		l.setPlayer(new Player(1, 1, null));
		l.setKeyListener(new KeyListener() {
			@Override
			public void changeBreakableWallLeftTries(Point location,
					int leftTries) {
			}

			@Override
			public void levelIsOver() {
				levelIsOver = true;
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
		});
		target.setLevel(l);
	}

	@Test
	public void TestCanPass() {
		Assert.assertEquals(true, target.canPass());
	}

	@Test
	public void TestImOver() {
		target.imOver();
		Assert.assertEquals(true, levelIsOver);
	}
}
