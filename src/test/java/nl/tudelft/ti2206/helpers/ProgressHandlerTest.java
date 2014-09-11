package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.assertEquals;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.BeforeClass;
import org.junit.Test;

public class ProgressHandlerTest {

	private static GameWorld world;

	@BeforeClass
	public static void init() {
		new HeadlessLauncher().launch();
		world = new GameWorld();
	}

	@Test
	public void testSaveGrid() {
		Grid grid = new Grid(world, true);
		world.setGrid(grid);

		grid.setTile(0, 2, true);
		grid.setTile(1, 4, false);

		String saveString = "0,2,true\n1,4,false\n";

		ProgressHandler.saveGame(world);
		assertEquals(saveString, PreferenceHandler.getGrid());
	}

	@Test
	public void testSaveGame() {
		int score = 200;
		int highestTile = 2048;
		world.setScore(score);
		world.setOldHighest(highestTile);

		ProgressHandler.saveGame(world);

		assertEquals(score, PreferenceHandler.getScore());
		assertEquals(highestTile, PreferenceHandler.getHighestTile());
	}
	
	@Test
	public void testLoadGrid() {
		// copy the current grid
		Grid grid = world.getGrid();
		// reset the current grid
		world.setGrid(new Grid(world, true));

		ProgressHandler.loadGame(world);
		// assertWhat?
	}
}
