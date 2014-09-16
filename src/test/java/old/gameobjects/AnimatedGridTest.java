/**
 * 
 */
package old.gameobjects;

import static org.junit.Assert.*;
import old.game.GameWorld;
import old.gameobjects.AnimatedGrid;
import old.handlers.TileHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test suite for the AnimatedGrid class.
 *
 * @author group-21
 */
public class AnimatedGridTest {
	/** The object under test. */
	private AnimatedGrid grid;
	/** A mock for the GameWorld object. */
	private GameWorld world;
	/** A mock for the TileHandler object. */
	private TileHandler tileHandler;
	
	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@Before
	public void setUp() {
		world = Mockito.mock(GameWorld.class);
		tileHandler = Mockito.mock(TileHandler.class);
		grid = new AnimatedGrid(world, false);
		grid.setTileHandler(tileHandler);
	}

	/**
	 * Test the tile's X-coordinate.
	 */
	@Test
	public void testGetTileX() {
		assertEquals(403, grid.getTileX(3), .1f);
	}

	/**
	 * Test the tile's Y-coordinate.
	 */
	@Test
	public void testGetTileY() {
		assertEquals(115, grid.getTileY(3), .1f);
	}

	/**
	 * Test for the Tile's width.
	 */
	@Test
	public void testGetTileWidth() {
		assertEquals(81, grid.getTileWidth(3), .1f);
	}

	/**
	 * Test for the tile's height.
	 */
	@Test
	public void testGetTileHeight() {
		assertEquals(81, grid.getTileHeight(3), .1f);
	}
}
