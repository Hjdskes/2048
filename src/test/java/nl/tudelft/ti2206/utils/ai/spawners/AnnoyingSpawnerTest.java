package nl.tudelft.ti2206.utils.ai.spawners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Difficulty;
import nl.tudelft.ti2206.utils.states.RunningState;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class AnnoyingSpawnerTest {
	private Grid grid;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		new HeadlessLauncher().launch();

		grid = new Grid(true);
		grid.setTile(12, 2);
		grid.setTile(13, 1);
		grid.setDifficulty(Difficulty.HARD);
		TwentyFourtyGame.setState(RunningState.getInstance());
	}

	/**
	 * Tests if a new tile is spawned with the correct location
	 * and value, when the spawnNewTile() method is called with AnnoyingSpawner.
	 */
	@Test
	public void testSpawnTileAnnoying() {
		int countBefore = 0, countAfter = 0;

		for (int i = 0; i < grid.getTiles().length; i++) {
			if (!grid.getTiles()[i].isEmpty()) {
				countBefore++;
			}
		}

		grid.spawnNewTile();

		for (int i = 0; i < grid.getTiles().length; i++) {
			if (!grid.getTiles()[i].isEmpty()) {
				countAfter++;
			}
		}

		assertEquals(countAfter, countBefore + 1);
		assertTrue(!grid.getTiles()[8].isEmpty());
		assertEquals(1, grid.getTiles()[8].getValue());
	}
}
