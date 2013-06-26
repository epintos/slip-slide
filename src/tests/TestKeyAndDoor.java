package tests;

import java.awt.Point;

import junit.framework.Assert;
import model.cells.Door;
import model.cells.Key;
import model.interfaces.KeyListener;
import model.level.Level;
import model.player.Player;

import org.junit.Before;
import org.junit.Test;

public class TestKeyAndDoor {

	private Key key;
	private Level l;
	private boolean elementRemoved = false;
	
	@Before
	public void Initialize() {
		key =  new Key(new Point(1,1), new Door(new Point(2,2)));
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
				elementRemoved = true;
			}
			
			@Override
			public void transporterLoop() {
				
			}
		});
		key.setLevel(l);
	}
	
	@Test
	public void TestCanPass() {
		Assert.assertEquals(true, key.canPass());
		Assert.assertEquals(false, key.getDoor().canPass());
	}
	
	@Test
	public void TestImOver() {
		key.imOver();
		Assert.assertEquals(true, elementRemoved);
		Assert.assertEquals(true, key.getDoor().canPass());
	}
}
