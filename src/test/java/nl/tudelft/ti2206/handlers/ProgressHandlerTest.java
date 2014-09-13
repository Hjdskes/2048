package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.AnimatedGrid;
import nl.tudelft.ti2206.handlers.PreferenceHandler;
import nl.tudelft.ti2206.handlers.ProgressHandler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProgressHandlerTest {

	private static GameWorld world;
	private AnimatedGrid grid;

	/**
	 * Launch a headless game to enable file IO and create a new GameWorld
	 */
	@BeforeClass
	public static void init() {
		new HeadlessLauncher().launch();
		world = new GameWorld();
	}

	/**
	 * Clear the grid before executing a new test case
	 */
	@Before
	public void reinitGrid() {
		// clear the saved data to make sure the test case can use its own grid,
		// score, etc.
		PreferenceHandler.getPrefs().clear();
		grid = new AnimatedGrid(world, true);
		world.setGrid(grid);
	}

	/**
	 * Tests if a grid is saved correctly
	 */
	@Test
	public void testSaveGrid() {

		grid.setTile(0, 2, true);
		grid.setTile(1, 4, false);

		String saveString = "0,2,true\n1,4,false\n";

		ProgressHandler.saveGame(world);
		assertEquals(saveString, PreferenceHandler.getGrid());
	}

	/**
	 * Tests if a game is saved correctly
	 */
	@Test
	public void testSaveGame() {
		int score = 200;
		int highestTile = 2048;
		world.setScore(score);
		world.setOldHighest(highestTile);

		ProgressHandler.saveGame(world);

		assertEquals(score, PreferenceHandler.getScore());
		assertEquals(highestTile, PreferenceHandler.getHighestTile());
	}

	/**
	 * Tests if a game is loaded correctly. This includes loading the grid.
	 */
	@Test
	public void testLoadGame() {
		// construct the grid
		AnimatedGrid grid = new AnimatedGrid(world, true);
		grid.setTile(0, 2, true);
		grid.setTile(1, 4, true);

		// set the grid and save the game
		world.setGrid(grid);
		ProgressHandler.saveGame(world);

		// copy current scores
		int score = world.getScore();
		int highestTile = world.getHighestTile();

		// reset world
		world.setGrid(new AnimatedGrid(world, true));
		world.setScore(0);
		world.setOldHighest(0);

		ProgressHandler.loadGame(world);

		// make sure the grid is loaded in correctly
		assertEquals(score, world.getScore());
		assertEquals(highestTile, world.getHighestTile());
		assertEquals(grid.getPossibleMoves(), world.getGrid()
				.getPossibleMoves());
	}
}
