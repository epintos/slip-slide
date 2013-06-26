package tests;

import model.game.Game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.NoSuchPasswordException;

public class TestGame {

	private Game game;

	@Before
	public void initialize() {
		try {
			this.game = new Game();
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void TestTruePasswords() {
		Assert.assertEquals("stmap", game.getNextLevelPassword());
		Assert.assertEquals(true, game.searchPasswordExistance("rmmap"));
		try {
			Assert.assertEquals(14, game.searchPassword("ammap"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void TestFalsePasswords() {
		Assert.assertEquals(false, game.searchPasswordExistance("falseLevel"));
		try {
			game.searchPassword("falseLevel");
			Assert.fail();
		} catch (NoSuchPasswordException e) {
		}
	}

	@Test
	public void TestGamePassLevel() {
		try {
			game.passToNextLevel();
			Assert.assertEquals(2, game.getActualLevelNumber());
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void TestGameIsOver() {
		Assert.assertEquals(false, game.isOver());
		try {
			game.passToNextLevel();
			Assert.assertEquals(false, game.isOver());
			game.setActualLevel(23);
			Assert.assertEquals(true, game.isOver());
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
