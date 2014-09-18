package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the DrawableTile class.
 */
public class DrawableTileTest {
	/** The object under test. */
	private DrawableTile tile;

	/**
	 * Creates the test object.
	 */
	@Before
	public void setUp() {
		tile = new DrawableTile(0, 4);
	}

	/**
	 * Test DrawableTile's setMerged method.
	 */
	@Test
	public void testSetMerged() {
		tile.setMerged(true);
		assertTrue(tile.isMerged());
	}

	/**
	 * Test DrawableTile's reset method.
	 */
	@Test
	public void testReset() {
		tile.reset();
		assertEquals(0, tile.getValue());
		assertFalse(tile.isMerged());
	}

	/**
	 * Test DrawableTile's update method when spawning.
	 */
	@Test
	public void testUpdateSpawn() {
		tile.spawn();
		tile.update();
		assertEquals(.54f, tile.getScale(), .01f);
	}

	/**
	 * Test DrawableTile's update method when merging.
	 */
	@Test
	public void testUpdateMerge() {
		tile.merge();
		tile.update();
		assertEquals(1.188f, tile.getScale(), .01f);
	}

	/**
	 * Test DrawableTile's getTileSize method.
	 */
	@Test
	public void testGetTileSize() {
		assertEquals(1, tile.getScale(), .1f);
	}

//	/**
//	 * Test DrawableTile's getScale method.
//	 */
//	@Test
//	public void testGetScale() {
//		assertEquals(1, tile.getScale(), .1f);
//	}

	/**
	 * Test DrawableTile's getOffset method.
	 */
	@Test
	public void testGetOffset() {
		assertEquals(0, tile.getOffset(), .1f);
	}

	/**
	 * Test DrawableTile's getOffset method after merge.
	 */
	@Test
	public void testGetOffset_merge() {
		tile.merge();
		assertEquals(-8.1f, tile.getOffset(), .1f);
	}	
	
	/**
	 * Test DrawableTile's getOffset method after spawn.
	 */
	@Test
	public void testGetOffset_spawn() {
		tile.spawn();
		assertEquals(20.25, tile.getOffset(), .1f);
	}
	
	/**
	 * Test DrawableTile's merge method.
	 */
	@Test
	public void testMerge() {
		tile.merge();
		assertEquals(1.2f, tile.getScale(), .1f);
	}
}
