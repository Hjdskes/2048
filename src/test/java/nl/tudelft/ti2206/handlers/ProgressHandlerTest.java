package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
		Skin skin = mock(Skin.class);
		AssetHandler.getInstance().setSkin(skin);
		TextureRegion region = mock(TextureRegion.class);
		when(skin.getRegion(anyString())).thenReturn(region);
		Texture texture = mock(Texture.class);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
		/*
		 * Clear the saved data to make sure the test case can use its own grid,
		 * score, etc.
		 */
		prefsHandler.getPrefs().clear();
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

		progressHandler.saveGame(grid);
		assertEquals(saveString, prefsHandler.getGrid());
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

		progressHandler.saveGame(grid);

		assertEquals(score, prefsHandler.getScore());
		assertEquals(highestTile, prefsHandler.getHighestTile());
	}
}
