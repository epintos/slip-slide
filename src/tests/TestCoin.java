package tests;

import java.awt.Point;

import model.cells.Coin;
import model.interfaces.KeyListener;
import model.level.Level;
import model.player.Player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCoin {

	private Coin coin;

	private Level l;
	private boolean coinRemoved = false;

	protected enum states {VISIBLE,NOTVISIBLE};

	@Before
	public void Initialize() {
		coin = new Coin(10, new Point(1, 1));
		l = new Level();
		l.setPlayer(new Player(1, 1, null));
		l.setKeyListener(new KeyListener() {
			@Override
			public void changeBreakableWallLeftTries(Point location,
					int leftTries) {
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
				coinRemoved = true;
			}

			@Override
			public void transporterLoop() {

			}
		});
		coin.setLevel(l);
	}

	@Test
	public void TestCanPass() {
		Assert.assertEquals(true, coin.canPass());
	}

	@Test
	public void TestCoinIsRemoved() {
		coin.imOver();
		Assert.assertEquals(true, coinRemoved);
	}

}
