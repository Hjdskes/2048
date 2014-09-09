package nl.tudelft.ti2206.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.GameWorld.GameState;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the GameWorld class.
 * @author
 */
public class GameWorldTest {

	private GameWorld world;

	/**
	 * Create a GameWorld.
	 */
	@Before
	public void setUp() {
		world = new GameWorld();
	}

	/**
	 * Testing the constructor of GameWorld.class.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(world.getGrid());
		assertEquals(world.getScore(), 0);
		assertTrue(world.isRunning());
	}

	/**
	 * Testing how the world behaves when the game is won.
	 */
	@Test
	public void testWon() {
		assertTrue(world.isRunning());
		world.getGrid().setTile(0, 2048, false);
		world.update(.15f);
		assertTrue(world.isWon());
		assertFalse(world.isLost());
		assertEquals(world.getGameState(), GameState.WON);
		assertFalse(world.isRunning());
	}

	/**
	 * Fills the grid as follows:
	 * 
	 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
	 * 
	 * which should result in a loss
	 */
	@Test
	public void testLost() {
		assertTrue(world.isRunning());

		for (int i = 0; i < world.getGrid().getTiles().length; i++) {
			world.getGrid().setTile(i, i + 1, false);
		}

		world.update(.15f);
		assertTrue(world.isLost());
		assertFalse(world.isWon());
		assertEquals(world.getGameState(), GameState.LOST);
		assertFalse(world.isRunning());
	}

	/**
	 * Testing how the world behaves when the game is restarted.
	 */
	@Test
	public void testRestart() {
		world.setGrid(new Grid(world, true));
		world.setScore(8);
		world.getGrid().setTile(0, 2048, true);
		world.update(.15f);
		assertTrue(world.isWon());
		world.restart();
		assertEquals(world.getScore(), 0);
		assertFalse(world.getGrid().getHighest() == 2048);
	}

	/**
	 * Testing if the score is incremented.
	 */
	@Test
	public void testAddScore() {
		int score = world.getScore();
		int increment = 3;
		world.addScore(increment);
		assertEquals(world.getScore(), score + increment);
	}
}
