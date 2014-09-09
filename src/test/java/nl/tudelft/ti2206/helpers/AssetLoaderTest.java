package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.Launcher;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test suite for the AssetLoader class that has to save and load the grid, score, highest value block and highscore in preferences when the window is closed.
 * @author paulbakker
 *
 */
public class AssetLoaderTest {

	private static GameWorld world;

	@BeforeClass
	public static void setup() throws InterruptedException {
		Launcher.main(null);
		Thread.sleep(1000);
		world = new GameWorld();
	}

	/**
	 * Testing the saving of the grid to preferences.
	 */
	@Test
	public void saveGameTest() {
		for (int i = 0; i < 16; i++) {
			world.getGrid().setTile(i, 32, false);
		}
		world.update(1);
		int score = world.getScore();
		AssetLoader.saveGame(world);

		int scoreResult = AssetLoader.getPrefs().getInteger("score");
		String gridResult = AssetLoader.getPrefs().getString("grid");

		assertEquals(scoreResult, score);
		assertEquals(
				gridResult,
				"0,32,false\n1,32,false\n2,32,false\n3,32,false\n"
						+ "4,32,false\n5,32,false\n6,32,false\n7,32,false\n"
						+ "8,32,false\n9,32,false\n10,32,false\n11,32,false\n"
						+ "12,32,false\n13,32,false\n14,32,false\n15,32,false");

	}

	/**
	 * Testing the loading of a grid from preferences.
	 * @throws InterruptedException
	 */
	@Test
	public void loadGameTest() {
		int value = 2;
		for (int i = 0; i < 16; i++) {
			world.getGrid().setTile(i, value, true);
			value += 2;
		}

		AssetLoader.getPrefs().putInteger("score", world.getScore());
		AssetLoader.getPrefs().flush();
		AssetLoader
				.getPrefs()
				.putString(
						"grid",
						"0,2,false \n1,4,false \n2,6,false \n3,8,false \n"
								+ "4,10,false \n5,12,false \n6,14,false \n7,16,false \n"
								+ "8,18,false \n9,20,false \n10,22,false \n11,24,false \n"
								+ "12,26,false \n13,28,false \n14,30,false \n15,32,false");

		AssetLoader.loadGame(world);

		int testValue = 2;
		for (int i = 0; i < 16; i++) {
			assertEquals(world.getGrid().getTiles()[i].getValue(), testValue);
			assertEquals(world.getGrid().getTiles()[i].isMerged(), false);
			testValue += 2;

		}

	}

	/**
	 * Testing if the highscore is correctly set in preferences.
	 */
	@Test
	public void setHighScoreTest() {
		AssetLoader.setHighscore(5000);
		assertEquals(AssetLoader.getPrefs().getInteger("highscore"), 5000);

	}

	/**
	 * Testing if the highest value is correctly set in preferences.
	 */
	@Test
	public void setHighestTest() {
		AssetLoader.setHighest(1024);
		assertEquals(AssetLoader.getPrefs().getInteger("highest"), 1024);

	}

	/**
	 * Testing if the highscore is correctly retrieved from preferences.
	 */
	@Test
	public void getHighScoreTest() {
		AssetLoader.setHighscore(5000);
		assertEquals(AssetLoader.getHighscore(), 5000);
	}

	/**
	 * Testing if the highest value is correctly retrieved from preferences.
	 */
	@Test
	public void getHighestTest() {
		AssetLoader.setHighscore(1024);
		assertEquals(AssetLoader.getHighest(), 1024);
	}

}
