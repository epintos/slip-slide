package tests;

import java.awt.Point;

import junit.framework.Assert;
import model.board.Board;
import model.cells.BlankCell;
import model.cells.Cell;

import org.junit.Before;
import org.junit.Test;

import exceptions.PointAllreadyContainsCellException;

public class TestBoard {

	private Board board;
	
	@Before
	public void Initialize() {
		board = new Board(5,5);
	}

	@Test
	public void Testadd() {
		try {
			board.add(1, 1, new BlankCell(new Point(1, 1)));
		} catch (PointAllreadyContainsCellException e) {
			Assert.fail();
		}
	}

	@Test
	public void TestAddOnNoNullCell() {
		try {
			/*
			 * Addition fails because there already is an element on 0,0 (a
			 * wall)
			 */
			board.add(0, 0, new BlankCell(new Point(5, 5)));
			Assert.fail();
		} catch (PointAllreadyContainsCellException e) {
		}
	}

	@Test
	public void TestAddOutsideBoard() {
		try {
			/* Addition fails because its adding outside de board */
			board.add(7, 7, new BlankCell(new Point(5, 5)));
			Assert.fail();
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (PointAllreadyContainsCellException e) {
			Assert.fail();
		}
	}

	@Test
	public void TestGetElemOutOfBoard() {
		try {
			board.getElemIn(-1, -1);
			Assert.fail();
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	@Test
	public void TestGetElem() {
		BlankCell c = new BlankCell(new Point(1,1));
		
		try {
			board.add(1, 1, c);
		} catch (PointAllreadyContainsCellException e) {
			Assert.fail();
		}
		Cell c2 = board.getElemIn(1, 1);
		Assert.assertEquals(c2, c);
		
	}
}
