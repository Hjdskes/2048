/**
 * 
 */
package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jochem Heijltjes
 * 
 */
public class XYCalculatorTest {

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
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileX(int)}.
	 */
	@Test
	public void testGetTileX4() {
		assertEquals(TILE_X, XYCalculator.getTileX(4), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileX(int)}.
	 */
	@Test
	public void testGetTileX5() {
		assertEquals(TILE_X + TILE_WIDTH + GAP, XYCalculator.getTileX(5), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileX(int)}.
	 */
	@Test
	public void testGetTileX6() {
		assertEquals(TILE_X + 2 * (TILE_WIDTH + GAP), XYCalculator.getTileX(6),
				0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileX(int)}.
	 */
	@Test
	public void testGetTileX15() {
		assertEquals(TILE_X + 3 * (TILE_WIDTH + GAP),
				XYCalculator.getTileX(15), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getCenterTileX(int)}.
	 */
	@Test
	public void testGetCenterTileX() {
		assertEquals(TILE_X + 2 * (TILE_WIDTH + GAP) + TILE_WIDTH / 2,
				XYCalculator.getCenterTileX(6), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileY(int)}.
	 */
	@Test
	public void testGetTileY0() {
		assertEquals(TILE_Y, XYCalculator.getTileY(0), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileY(int)}.
	 */
	@Test
	public void testGetTileY2() {
		assertEquals(TILE_Y, XYCalculator.getTileY(2), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileY(int)}.
	 */
	@Test
	public void testGetTileY9() {
		assertEquals(TILE_Y + 2 * (TILE_HEIGHT + GAP),
				XYCalculator.getTileY(9), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileY(int)}.
	 */
	@Test
	public void testGetTileY13() {
		assertEquals(TILE_Y + 3 * (TILE_HEIGHT + GAP),
				XYCalculator.getTileY(13), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileY(int)}.
	 */
	@Test
	public void testGetTileY17() {
		assertEquals(TILE_Y, XYCalculator.getTileY(17), 0.5f);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getCenterTileY(int)}.
	 */
	@Test
	public void testGetCenterTileY() {
		assertEquals(TILE_Y + TILE_HEIGHT + GAP + TILE_HEIGHT / 2,
				XYCalculator.getCenterTileY(6), 0.5f);
	}

	/**
	 * Test method for {@link nl.tudelft.ti2206.helpers.XYCalculator#getGridX()}
	 * .
	 */
	@Test
	public void testGetGridX() {
		assertEquals(GRID_X, XYCalculator.getGridX());
	}

	/**
	 * Test method for {@link nl.tudelft.ti2206.helpers.XYCalculator#getGridY()}
	 * .
	 */
	@Test
	public void testGetGridY() {
		assertEquals(GRID_Y, XYCalculator.getGridY());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileWidth()}.
	 */
	@Test
	public void testGetTileWidth() {
		assertEquals(TILE_WIDTH, XYCalculator.getTileWidth());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getTileHeight()}.
	 */
	@Test
	public void testGetTileHeight() {
		assertEquals(TILE_HEIGHT, XYCalculator.getTileHeight());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getGridWidth()}.
	 */
	@Test
	public void testGetGridWidth() {
		assertEquals(GRID_WIDTH, XYCalculator.getGridWidth());
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.helpers.XYCalculator#getGridHeight()}.
	 */
	@Test
	public void testGetGridHeight() {

		assertEquals(GRID_HEIGHT, XYCalculator.getGridHeight());
	}

}
