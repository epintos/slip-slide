package tests;

import java.awt.Point;

import junit.framework.Assert;
import model.cells.BlankCell;

import org.junit.Before;
import org.junit.Test;

public class TestBlankCell {

	private BlankCell blankCell;

	@Before
	public void Initialize() {
		blankCell = new BlankCell(new Point(1, 1));
	}

	@Test
	public void TestCanPass() {
		Assert.assertEquals(true, blankCell.canPass());
	}

	@Test
	public void TestPossition() {
		Assert.assertEquals(new Point(1, 1), blankCell.getLocation());
	}
}
