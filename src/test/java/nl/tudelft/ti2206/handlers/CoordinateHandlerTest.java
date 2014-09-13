/**
 * 
 */
package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.handlers.CoordinateHandler;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jochem Heijltjes
 * 
 */
public class CoordinateHandlerTest {

	/** The TILE_HEIGHT of the Tile */
	private static final int TILE_WIDTH = 81;
	/** The width of the Tile */
	private static final int TILE_HEIGHT = 81;

	/** Base x coordinate */
	private static final int TILE_X = 115;
	/** Base y coordinate */
	private static final int TILE_Y = 115;
	/** Gap between Tiles, Grid edges, etc */
	private static final int GAP = 15;
	/** The width of the grid. */
	private static final int GRID_WIDTH = 400;
	/** The height of the grid. */
	private static final int GRID_HEIGHT = 400;
	/** Base x coordinate. */
	private static final int GRID_X = 100;
	/** Base y coordinate. */
	private static final int GRID_Y = 100;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileX(int)}.
	 */
	@Test
	public void testGetTileX4() {
		assertEquals(TILE_X, CoordinateHandler.getTileX(4), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileX(int)}.
	 */
	@Test
	public void testGetTileX5() {
		assertEquals(TILE_X + TILE_WIDTH + GAP, CoordinateHandler.getTileX(5), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileX(int)}.
	 */
	@Test
	public void testGetTileX6() {
		assertEquals(TILE_X + 2 * (TILE_WIDTH + GAP), CoordinateHandler.getTileX(6),
				0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileX(int)}.
	 */
	@Test
	public void testGetTileX15() {
		assertEquals(TILE_X + 3 * (TILE_WIDTH + GAP),
				CoordinateHandler.getTileX(15), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getCenterTileX(int)}.
	 */
	@Test
	public void testGetCenterTileX() {
		assertEquals(TILE_X + 2 * (TILE_WIDTH + GAP) + TILE_WIDTH / 2,
				CoordinateHandler.getCenterTileX(6), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileY(int)}.
	 */
	@Test
	public void testGetTileY0() {
		assertEquals(TILE_Y, CoordinateHandler.getTileY(0), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileY(int)}.
	 */
	@Test
	public void testGetTileY2() {
		assertEquals(TILE_Y, CoordinateHandler.getTileY(2), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileY(int)}.
	 */
	@Test
	public void testGetTileY9() {
		assertEquals(TILE_Y + 2 * (TILE_HEIGHT + GAP),
				CoordinateHandler.getTileY(9), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileY(int)}.
	 */
	@Test
	public void testGetTileY13() {
		assertEquals(TILE_Y + 3 * (TILE_HEIGHT + GAP),
				CoordinateHandler.getTileY(13), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileY(int)}.
	 */
	@Test
	public void testGetTileY17() {
		assertEquals(TILE_Y, CoordinateHandler.getTileY(17), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getCenterTileY(int)}.
	 */
	@Test
	public void testGetCenterTileY() {
		assertEquals(TILE_Y + TILE_HEIGHT + GAP + TILE_HEIGHT / 2,
				CoordinateHandler.getCenterTileY(6), 0.5f);
	}

	/**
	 * Test method for {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getGridX()}
	 * .
	 */
	@Test
	public void testGetGridX() {
		assertEquals(GRID_X, CoordinateHandler.getGridX());
	}

	/**
	 * Test method for {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getGridY()}
	 * .
	 */
	@Test
	public void testGetGridY() {
		assertEquals(GRID_Y, CoordinateHandler.getGridY());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileWidth()}.
	 */
	@Test
	public void testGetTileWidth() {
		assertEquals(TILE_WIDTH, CoordinateHandler.getTileWidth());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileHeight()}.
	 */
	@Test
	public void testGetTileHeight() {
		assertEquals(TILE_HEIGHT, CoordinateHandler.getTileHeight());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getGridWidth()}.
	 */
	@Test
	public void testGetGridWidth() {
		assertEquals(GRID_WIDTH, CoordinateHandler.getGridWidth());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getGridHeight()}.
	 */
	@Test
	public void testGetGridHeight() {
		assertEquals(GRID_HEIGHT, CoordinateHandler.getGridHeight());
	}

}
