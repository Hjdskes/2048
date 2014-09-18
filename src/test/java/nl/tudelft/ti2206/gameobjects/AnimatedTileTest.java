package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the AnimatedTile class.
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

	/**
	 * Test AnimatedTile's update method when spawning.
	 */
	@Test
	public void testUpdateSpawn() {
		tile.spawn();
		tile.update();
		assertEquals(.54f, tile.getScale(), .01f);
	}

	/**
	 * Test AnimatedTile's update method when merging.
	 */
	@Test
	public void testUpdateMerge() {
		tile.merge();
		tile.update();
		assertEquals(1.188f, tile.getScale(), .01f);
	}

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

	/**
	 * Test AnimatedTile's getXYOffset method after merge.
	 */
	@Test
	public void testGetXYOffset_merge() {
		tile.merge();
		assertEquals(-8.1f, tile.getXYOffset(), .1f);
	}	
	
	/**
	 * Test AnimatedTile's getXYOffset method after spawn.
	 */
	@Test
	public void testGetXYOffset_spawn() {
		tile.spawn();
		assertEquals(20.25, tile.getXYOffset(), .1f);
	}
	
	/**
	 * Test AnimatedTile's merge method.
	 */
	@Test
	public void testMerge() {
		tile.merge();
		assertEquals(1.2f, tile.getScale(), .1f);
	}
}
