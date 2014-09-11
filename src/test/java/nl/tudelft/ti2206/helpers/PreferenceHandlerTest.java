package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.game.HeadlessLauncher;

import org.junit.Before;
import org.junit.Test;

public class PreferenceHandlerTest {
	@Before
	public void setup() {
		HeadlessLauncher launcher = new HeadlessLauncher();
		launcher.launch();
	}

//	public void initScoresTest() {
//		
//	}

	/**
	 * Tests if the score is correctly set
	 * and retrieved from the Preferences.
	 */
	@Test
	public void scoreTest() {
		PreferenceHandler.setScore(128);
		assertEquals(PreferenceHandler.getScore(), 128);
	}
	
	/**
	 * Tests if the highscore is correctly set
	 * and retrieved from the Preferences.
	 */
	@Test
	public void highScoreTest() {
		PreferenceHandler.setHighscore(5000);
		assertEquals(PreferenceHandler.getHighscore(), 5000);
	}

	/**
	 * Tests if the highest value is correctly set
	 * and retrieved from the Preferences.
	 */
	@Test
	public void highestTest() {
		PreferenceHandler.setHighest(1024);
		assertEquals(PreferenceHandler.getHighestTile(), 1024);
	}

	/**
	 * Tests if the grid is correctly saved
	 * and retrieved from the Preferences.
	 */
	@Test
	public void gridTest() {
		String grid = "0,2,false \n1,4,false \n2,6,false \n3,8,false \n"
				+ "4,10,false \n5,12,false \n6,14,false \n7,16,false \n"
				+ "8,18,false \n9,20,false \n10,22,false \n11,24,false \n"
				+ "12,26,false \n13,28,false \n14,30,false \n15,32,false";
		PreferenceHandler.setGrid(grid);
		assertEquals(PreferenceHandler.getGrid(), grid);
	}
}
