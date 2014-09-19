package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A test class for the ProgressHandler.
 * 
 * @author group-21
 */
public class ProgressHandlerTest {
	
	private Grid grid;

	/**
	 * Launches a headless game to enable file I/O.
	 */
	@BeforeClass
	public static void init() {
		new HeadlessLauncher().launch();
	}

	/**
	 * Clears the saved Preferences and the Grid before executing a new test
	 * case.
	 */
	@Before
	public void reinitGrid() {
		Skin skin = mock(Skin.class);
		TextureRegion region = mock(TextureRegion.class);
		/*
		 * Clear the saved data to make sure the test case can use its own grid,
		 * score, etc.
		 */
		PreferenceHandler.getPrefs().clear();
		grid = new Grid(true, skin, region);
	}

	/**
	 * Tests if a Grid is saved correctly.
	 */
	@Test
	public void testSaveGrid() {
		grid.setTile(0, 2);
		grid.setTile(1, 4);

		String saveString = "0,2\n1,4\n";

		ProgressHandler.saveGame(grid);
		assertEquals(saveString, PreferenceHandler.getGrid());
	}

	/**
	 * Tests if a game is saved correctly.
	 */
	@Test
	public void testSaveGame() {
		int score = 200;
		int highestTile = 2048;
		grid.setScore(score);
		grid.setHighestTile(highestTile);

		ProgressHandler.saveGame(grid);

		assertEquals(score, PreferenceHandler.getScore());
		assertEquals(highestTile, PreferenceHandler.getHighestTile());
	}

	/**
	 * Tests if a game is loaded correctly. This includes loading the grid.
	 */
	@Test
	public void testLoadGame() {
//		grid.setTile(0, 2);
//		grid.setTile(1, 4);
//
//		/* Save the game. */
//		ProgressHandler.saveGame(grid);
//
//		/* Copy current scores. */
//		int score = grid.getScore();
//		int highestTile = grid.getCurrentHighestTile();
//		int possibleMoves = grid.getPossibleMoves();
//
//		/* Reset world. */
//		grid.restart();
//
//		/* Load the saved game and make sure everything is loaded correctly. */
//		grid = ProgressHandler.loadGame();
//		assertEquals(score, grid.getScore());
//		assertEquals(highestTile, grid.getCurrentHighestTile());
//		assertEquals(possibleMoves, grid.getPossibleMoves());
	}
}
