package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the AnimatedTile class.
 *
 * @author group-21
 */
public class AnimatedTileTest {
	/** The object under test. */
	private AnimatedTile tile;

	/**
	 * Creates the test object.
	 */
	@Before
	public void setUp() {
		tile = new AnimatedTile(4);
	}

	/**
	 * Test AnimatedTile's setMerged method.
	 */
	@Test
	public void testSetMerged() {
		tile.setMerged(true);
		assertTrue(tile.isMerged());
	}

	/**
	 * Test AnimatedTile's reset method.
	 */
	@Test
	public void testReset() {
		tile.reset();
		assertEquals(0, tile.getValue());
		assertFalse(tile.isMerged());
	}

	// /**
	// * Test AnimatedTile's update method.
	// */
	// @Test
	// public void testUpdate() {
	// tile.update();
	// }

	/**
	 * Test AnimatedTile's getTileSize method.
	 */
	@Test
	public void testGetTileSize() {
		assertEquals(81, tile.getTileSize(), .1f);
	}

	/**
	 * Test AnimatedTile's getScale method.
	 */
	@Test
	public void testGetScale() {
		assertEquals(1, tile.getScale(), .1f);
	}

	/**
	 * Test AnimatedTile's getXYOffset method.
	 */
	@Test
	public void testGetXYOffset() {
		assertEquals(0, tile.getXYOffset(), .1f);
	}

	// /**
	// * Test AnimatedTile's merge method.
	// */
	// @Test
	// public void testMerge() {
	// tile.merge();
	// }
}
