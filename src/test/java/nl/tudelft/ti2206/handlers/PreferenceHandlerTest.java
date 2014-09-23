package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.game.HeadlessLauncher;

import org.junit.Before;
import org.junit.Test;

/**
 * A test class for the PreferenceHandler.
 * 
 * @author group-21
 */
public class PreferenceHandlerTest {

	/** A singleton reference to the PreferenceHandler. */
	private PreferenceHandler prefsHandler = PreferenceHandler.getInstance();

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
		prefsHandler.getPrefs().clear();
		prefsHandler.initScores();
		assertEquals(0, prefsHandler.getScore());
		assertEquals(0, prefsHandler.getHighscore());
		assertEquals(0, prefsHandler.getHighestTile());
	}

	/**
	 * Tests if the score is correctly set and retrieved from the Preferences.
	 */
	@Test
	public void scoreTest() {
		prefsHandler.setScore(128);
		assertEquals(128, prefsHandler.getScore());
	}

	/**
	 * Tests if the highscore is correctly set and retrieved from the
	 * Preferences.
	 */
	@Test
	public void highScoreTest() {
		prefsHandler.setHighscore(5000);
		assertEquals(5000, prefsHandler.getHighscore());
	}

	/**
	 * Tests if the highest value is correctly set and retrieved from the
	 * Preferences.
	 */
	@Test
	public void highestTest() {
		prefsHandler.setHighest(1024);
		assertEquals(1024, prefsHandler.getHighestTile());
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
		prefsHandler.setGrid(grid);
		assertEquals(grid, prefsHandler.getGrid());
	}
}
