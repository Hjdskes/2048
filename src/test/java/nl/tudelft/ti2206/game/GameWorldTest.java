package nl.tudelft.ti2206.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GameWorldTest {
	
	private GameWorld world;
	
	@Before
	public void setUp() {
		world = new GameWorld();
	}

	@Test
	public void testConstructor() {
		assertNotNull(world.getGrid());
		assertEquals(world.getScore(), 0);
		assertTrue(world.isRunning());
	}
	
	@Test
	public void testWon() {
		assertTrue(world.isRunning());
		world.getGrid().setTile(0, 2048, false);
		world.update(.15f);
		assertTrue(world.isWon());
	}

	/**
	 * Fills the grid as follows:
	 * 
	 * 1  2  3  4 
	 * 5  6  7  8
	 * 9  10 11 12
	 * 13 14 15 16
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
	}
	
	@Test
	public void testRestart() {
		world.setScore(8);
		world.getGrid().setTile(0, 2048, true);
		world.update(.15f);
		assertTrue(world.isWon());
		world.restart();
		assertEquals(world.getScore(), 0);
		assertFalse(world.getGrid().getHighest() == 2048);
	}
}
