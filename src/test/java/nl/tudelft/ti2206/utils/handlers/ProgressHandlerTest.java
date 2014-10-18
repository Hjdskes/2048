package nl.tudelft.ti2206.utils.handlers;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.handlers.PreferenceHandler;
import nl.tudelft.ti2206.utils.handlers.ProgressHandler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test class for the progressHandler.
 * 
 * @author group-21
 */
public class ProgressHandlerTest {

	/** A singleton reference to the PreferenceHandler. */
	private static PreferenceHandler prefsHandler;
	/** A singleton reference to the progressHandler. */
	private static ProgressHandler progressHandler;

	private Grid grid;

	/**
	 * Launches a headless game to enable file I/O.
	 */
	@BeforeClass
	public static void init() {
		new HeadlessLauncher().launch();
		prefsHandler = PreferenceHandler.getInstance();
		progressHandler = ProgressHandler.getInstance();
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
		prefsHandler.getPrefs().clear();
		grid = new Grid(true);
	}

	/**
	 * Tests if a Grid is saved correctly.
	 */
	@Test
	public void testSaveGrid() {
		grid.setTile(0, 2);
		grid.setTile(1, 4);

		String saveString = "2,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

		progressHandler.saveGame(grid);
		assertEquals(saveString, prefsHandler.getGrid());
	}

	/**
	 * Tests if a game is saved correctly.
	 */
	@Test
	public void testSaveGame() {
		int score = 200;
		grid.setScore(score);
		grid.setTile(0, 1024);
		grid.updateHighestTile();

		progressHandler.saveGame(grid);

		assertEquals(score, prefsHandler.getScore());
	}

	/**
	 * Tests if a game is loaded correctly.
	 */
	@Test
	public void testLoadGameGridParam() {
		grid.setTile(0, 2);
		grid.setTile(1, 4);
		progressHandler.saveGame(grid);

		Grid testGrid = progressHandler.loadGame(grid);
		assertEquals("2,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0", testGrid.toString());
	}

	/**
	 * Tests if a game is saved and loaded correctly after the setting of new
	 * tiles;
	 */
	@Test
	public void testLoadGameFilled() {
		grid.setTile(0, 2);
		grid.setTile(1, 4);
		progressHandler.saveGame(grid);

		Grid grid = progressHandler.loadGame();
		assertEquals(this.grid.toString(), grid.toString());
	}

	/**
	 * Tests if a empty game is saved and loaded correctly.
	 */
	@Test
	public void testLoadGameEmpty() {
		prefsHandler.getPrefs().clear();
		Grid grid = progressHandler.loadGame();
		assertEquals("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", this.grid.toString());
		assertNotEquals(this.grid.toString(), grid.toString());
	}

	/**
	 * Tests if a game isn't loaded if the string in Preferences is corrupted.
	 */
	@Test
	public void testLoadGameCorruptGridString() {
		prefsHandler.setGrid("0,4");
		Grid grid = progressHandler.loadGame();
		assertNotEquals(grid.toString(), prefsHandler.getGrid().toString());
	}

}
