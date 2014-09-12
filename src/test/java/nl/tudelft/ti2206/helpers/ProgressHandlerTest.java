package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProgressHandlerTest {

	private static GameWorld world;
	private Grid grid;

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
		grid = new Grid(world, true);
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
		// construct the grid as saved
		Grid savedGrid = new Grid(world, true);
		savedGrid.setTile(0, 2, true);
		savedGrid.setTile(1, 4, false);

		// copy current scores
		int score = world.getScore();
		int highestTile = world.getHighestTile();

		// reset scores
		world.setScore(0);
		world.setOldHighest(0);

		ProgressHandler.loadGame(world);

		// make sure the grid is loaded in correctly
		assertEquals(score, world.getScore());
		assertEquals(highestTile, world.getHighestTile());
		assertEquals(savedGrid.getPossibleMoves(), world.getGrid()
				.getPossibleMoves());
	}
}
