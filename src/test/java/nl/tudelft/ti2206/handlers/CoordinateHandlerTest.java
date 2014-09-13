package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.handlers.CoordinateHandler;

import org.junit.Test;

/**
 * A test class for the CoordinateHandler.
 * 
 * @author group-21
 */
public class CoordinateHandlerTest {
	/** The width of the Tile. */
	private static final int TILE_WIDTH = 81;
	/** The height of the Tile. */
	private static final int TILE_HEIGHT = 81;
	/** The base Tile x-coordinate. */
	private static final int TILE_X = 115;
	/** The base Tile y-coordinate. */
	private static final int TILE_Y = 115;
	/** The gap in between tiles, Grid edges, etc. */
	private static final int GAP = 15;
	/** The width of the Grid. */
	private static final int GRID_WIDTH = 400;
	/** The height of the Grid. */
	private static final int GRID_HEIGHT = 400;
	/** The base Grid x-coordinate. */
	private static final int GRID_X = 100;
	/** The base Grid y-coordinate. */
	private static final int GRID_Y = 100;

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
		assertEquals(TILE_X + TILE_WIDTH + GAP, CoordinateHandler.getTileX(5),
				0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getTileX(int)}.
	 */
	@Test
	public void testGetTileX6() {
		assertEquals(TILE_X + 2 * (TILE_WIDTH + GAP),
				CoordinateHandler.getTileX(6), 0.5f);
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
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getGridX()} .
	 */
	@Test
	public void testGetGridX() {
		assertEquals(GRID_X, CoordinateHandler.getGridX());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.handlers.CoordinateHandler#getGridY()} .
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
