package tests;

import java.awt.Point;

import junit.framework.Assert;
import model.cells.Wall;

import org.junit.Before;
import org.junit.Test;

public class TestWall {

	private Wall wall;
	
	@Before
	public void Initialize() {
		wall = new Wall(new Point(1,1));
	}
	
	@Test
	public void TestCanPass() {
		Assert.assertEquals(false, wall.canPass());
	}
	
	@Test
	public void TestPossition() {
		Assert.assertEquals(new Point(1,1), wall.getLocation());
	}
}
