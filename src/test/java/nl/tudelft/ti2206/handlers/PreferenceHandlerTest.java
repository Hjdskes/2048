package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.handlers.PreferenceHandler;

import org.junit.Before;
import org.junit.Test;

public class PreferenceHandlerTest {

	/**
	 * Sets up the test environment.
	 */
	@Before
	public void setup() {
		HeadlessLauncher launcher = new HeadlessLauncher();
		launcher.launch();
	}

	/**
	 * Tests if the scores are correctly initialized when they aren't present.
	 */
	@Test
	public void initScoresTest() {
		PreferenceHandler.getPrefs().clear();
		PreferenceHandler.initScores();
		assertEquals(0, PreferenceHandler.getScore());
		assertEquals(0, PreferenceHandler.getHighscore());
		assertEquals(0, PreferenceHandler.getHighestTile());
	}

	/**
	 * Tests if the score is correctly set and retrieved from the Preferences.
	 */
	@Test
	public void scoreTest() {
		PreferenceHandler.setScore(128);
		assertEquals(128, PreferenceHandler.getScore());
	}

	/**
	 * Tests if the highscore is correctly set and retrieved from the
	 * Preferences.
	 */
	@Test
	public void highScoreTest() {
		PreferenceHandler.setHighscore(5000);
		assertEquals(5000, PreferenceHandler.getHighscore());
	}

	/**
	 * Tests if the highest value is correctly set and retrieved from the
	 * Preferences.
	 */
	@Test
	public void highestTest() {
		PreferenceHandler.setHighest(1024);
		assertEquals(1024, PreferenceHandler.getHighestTile());
	}

	/**
	 * Tests if the grid is correctly saved and retrieved from the Preferences.
	 */
	@Test
	public void gridTest() {
		String grid = "0,2,false \n1,4,false \n2,6,false \n3,8,false \n"
				+ "4,10,false \n5,12,false \n6,14,false \n7,16,false \n"
				+ "8,18,false \n9,20,false \n10,22,false \n11,24,false \n"
				+ "12,26,false \n13,28,false \n14,30,false \n15,32,false";
		PreferenceHandler.setGrid(grid);
		assertEquals(grid, PreferenceHandler.getGrid());
	}
}
