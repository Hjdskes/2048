package nl.tudelft.ti2206.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.GameWorld.GameState;
import nl.tudelft.ti2206.gameobjects.AnimatedGrid;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Test suite for the GameWorld class.
 *
 * @author group-21
 */
public class GameWorldTest {
	/** A mock for the AnimatedGrid object. */
	private AnimatedGrid grid;
	/** The object under test. */
	private GameWorld world;

	/**
	 * Initializes the mock object and creates a GameWorld.
	 * Sets the mocked AnimatedGrid in the GameWorld.
	 */
	@Before
	public void setUp() {
		grid = mock(AnimatedGrid.class);
		world = new GameWorld();
		world.setGrid(grid);
	}

	/**
	 * Tests the constructor of GameWorld: after creation, there should be an
	 * AnimatedGrid and the game should be running.
	 * 
	 * Testing for the score is impossible, since the game might be restarted
	 * from an earlier session in which the score is unknown.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(world.getGrid());
		assertTrue(world.isRunning());
	}

	/**
	 * Tests if the world behaves correctly when the game is won.
	 */
	@Test
	public void testWon() {
		when(grid.getCurrentHighestTile()).thenReturn(2048);
		world.update();
		assertTrue(world.isWon());
		assertFalse(world.isLost());
		assertEquals(world.getGameState(), GameState.WON);
		assertFalse(world.isRunning());
	}

	/**
	 * Tests if the world behaves correctly when the game is lost.
	 */
	@Test
	public void testLost() {
		when(grid.isFull()).thenReturn(true);
		when(grid.getPossibleMoves()).thenReturn(0);
		world.update();
		assertTrue(world.isLost());
		assertFalse(world.isWon());
		assertEquals(world.getGameState(), GameState.LOST);
		assertFalse(world.isRunning());
	}

	/**
	 * Tests if the world behaves correctly when the game is won
	 * and the player continues.
	 */
	@Test
	public void testContinuing() {
		when(grid.getCurrentHighestTile()).thenReturn(2048);
		world.update();

		/* We have to manually set the game state to continuing. */
		world.setGameState(GameState.CONTINUING);
		assertTrue(world.isContinuing());
	}

	/**
	 * Tests if the world behaves correctly when the game is restarted.
	 */
	@Test
	public void testRestart() {
		when(grid.getCurrentHighestTile()).thenReturn(2048);
		/* We have to manually set a score to test if the score gets reset
		 * correctly. */
		world.setScore(1500);

		world.update();
		assertTrue(world.isWon());

		/* Make the restart happen and assert the correct behaviour. */
		world.restart();
		assertEquals(world.getGameState(), GameState.RUNNING);
		assertEquals(world.getScore(), 0);
		verify(grid).restart();
	}

	/**
	 * Tests if the score gets incremented correctly.
	 */
	@Test
	public void testAddScore() {
		int score = world.getScore();
		int increment = 2;
		world.addScore(increment);
		assertEquals(world.getScore(), score + increment);
	}

	/**
	 * Tests if the correct highest tile gets returned when the
	 * highest tile is in the current game.
	 */
	@Test
	public void testGetHighestTileCurrent() {
		when(grid.getCurrentHighestTile()).thenReturn(4096);
		world.setOldHighest(2048);
		assertEquals(world.getHighestTile(), 4096);
	}

	/**
	 * Tests if the correct highest tile gets returned when the
	 * highest tile is from an old game.
	 */
	@Test
	public void testGetHighestTileOld() {
		when(grid.getCurrentHighestTile()).thenReturn(2048);
		world.setOldHighest(4096);
		assertEquals(world.getHighestTile(), 4096);
	}

	/**
	 * Tests if the correct highscore gets returned when the
	 * highestscore is in the current game.
	 */
	@Test
	public void testGetHighscoreCurrent() {
		world.setScore(4096);
		world.setOldHighscore(2048);
		assertEquals(world.getHighscore(), 4096);
	}

	/**
	 * Tests if the correct highscore gets returned when the
	 * highestscore is from an old game.
	 */
	@Test
	public void testGetHighscoreOld() {
		world.setScore(2048);
		world.setOldHighscore(4096);
		assertEquals(world.getHighscore(), 4096);
	}
}
