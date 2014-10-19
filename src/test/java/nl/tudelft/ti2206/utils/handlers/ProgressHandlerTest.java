package nl.tudelft.ti2206.utils.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;

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
		Gdx.app.getPreferences("2048").clear();
		Gdx.app.getPreferences("2048").flush();
		grid = new Grid(true);
	}

//	/**
//	 * Tests if a Grid is saved correctly.
//	 */
//	@Test
//	public void testSaveGrid() {
//		grid.setTile(0, 1);
//		grid.setTile(1, 2);
//
//		String saveString = "1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
//
//		progressHandler.saveGame(grid);
//		assertEquals(saveString, prefsHandler.getGrid());
//	}
//
//	/**
//	 * Tests if a game is saved correctly.
//	 */
//	@Test
//	public void testSaveGame() {
//		int score = 200;
//		grid.setScore(score);
//		grid.setTile(0, 10);
//		grid.updateHighestTile();
//
//		progressHandler.saveGame(grid);
//
//		assertEquals(score, prefsHandler.getScore());
//	}

	/**
	 * Tests if a game is loaded correctly.
	 */
	@Test
	public void testLoadGameGridParam() {
		grid.setTile(0, 1);
		grid.setTile(1, 2);
		progressHandler.saveGame(grid);

		Grid testGrid = progressHandler.loadGame(grid);
		assertEquals("1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0", testGrid.toString());
	}

//	/**
//	 * Tests if a game is saved and loaded correctly after the setting of new
//	 * tiles;
//	 */
//	@Test
//	public void testLoadGameFilled() {
//		grid.setTile(0, 1);
//		grid.setTile(1, 2);
//		progressHandler.saveGame(grid);
//
//		Grid grid = progressHandler.loadGame();
//		assertEquals(this.grid.toString(), grid.toString());
//	}

	/**
	 * Tests if a empty game is saved and loaded correctly.
	 */
	@Test
	public void testLoadGameEmpty() {
		Gdx.app.getPreferences("2048").clear();
		Grid grid = progressHandler.loadGame();
		assertEquals("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", this.grid.toString());
		assertNotEquals(this.grid.toString(), grid.toString());
	}

	/**
	 * Tests if a game isn't loaded if the string in Preferences is corrupted.
	 */
	@Test
	public void testLoadGameCorruptGridString() {
		prefsHandler.setGrid("0,2");
		Grid grid = progressHandler.loadGame();
		assertNotEquals(grid.toString(), prefsHandler.getGrid().toString());
	}

}
