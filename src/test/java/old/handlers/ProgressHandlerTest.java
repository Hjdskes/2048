package old.handlers;

import static org.junit.Assert.assertEquals;
import old.game.GameWorld;
import old.game.HeadlessLauncher;
import old.gameobjects.AnimatedGrid;
import old.handlers.PreferenceHandler;
import old.handlers.ProgressHandler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test class for the ProgressHandler.
 * 
 * @author group-21
 */
public class ProgressHandlerTest {

	private static GameWorld world;
	private AnimatedGrid grid;

	/**
	 * Launches a headless game to enable file I/O and creates a new GameWorld.
	 */
	@BeforeClass
	public static void init() {
		new HeadlessLauncher().launch();
		world = new GameWorld();
	}

	/**
	 * Clears the saved Preferences and the Grid before executing a new test
	 * case.
	 */
	@Before
	public void reinitGrid() {
		/*
		 * Clear the saved data to make sure the test case can use its own grid,
		 * score, etc.
		 */
		PreferenceHandler.getPrefs().clear();
		grid = new AnimatedGrid(world, true);
		world.setGrid(grid);
	}

	/**
	 * Tests if a Grid is saved correctly.
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
	 * Tests if a game is saved correctly.
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
		/* Construct the grid. */
		AnimatedGrid grid = new AnimatedGrid(world, true);
		grid.setTile(0, 2, true);
		grid.setTile(1, 4, true);

		/* Set the grid and save the game. */
		world.setGrid(grid);
		ProgressHandler.saveGame(world);

		/* Copy current stats. */
		int score = world.getScore();
		int highestTile = world.getHighestTile();
		int possibleMoves = world.getGrid().getPossibleMoves();

		/* Reset world. */
		world.restart();

		/* Load the saved game and make sure everything is loaded correctly. */
		ProgressHandler.loadGame(world);
		assertEquals(score, world.getScore());
		assertEquals(highestTile, world.getHighestTile());
		assertEquals(possibleMoves, world.getGrid().getPossibleMoves());
	}
}
