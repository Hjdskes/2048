package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.HeadlessLauncher;

import org.junit.Before;
import org.mockito.Mockito;

public class ScoreDisplayTest {

	private ScoreDisplay scores;
	private Grid grid;
	
	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@Before
	public void setup() {
		new HeadlessLauncher().launch();

		grid = Mockito.mock(Grid.class);
		scores = new ScoreDisplay(grid);
	}
	
//	/**
//	 * Tests if getCurrentHighestTile() returns the correct value.
//	 */
//	@Test
//	public void testGetHighestTile() {
//		grid.setTile(0, 2048);
//		grid.setTile(1, 4096);
//		scores.updateHighestTile();
//		assertEquals(4096, scores.getCurrentHighestTile());
//	}
}
