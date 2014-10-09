package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Tile class.
 */
public class TileTest {
	/** The object under test. */
	private Tile tile;

	/**
	 * Initializes the test object.
	 */
	@Before
	public void setup() {	
		tile = new Tile(4, 0);
	}

	/**
	 * Tests if the constructor successfully creates a new Tile object.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(tile);
	}

	/**
	 * Test if getIndex() correctly returns the index.
	 */
	@Test
	public void testGetIndex() {
		assertEquals(4, tile.getIndex());
	}

	/**
	 * Tests if getValue() correctly returns the current value.
	 */
	@Test
	public void testGetValue() {
		tile.setValue(2048);
		assertEquals(2048, tile.getValue());
	}

	/**
	 * Tests if setValue() correctly sets the value.
	 */
	@Test
	public void testSetValue() {
		tile.setValue(10);
		assertEquals(10, tile.getValue());
	}

	/**
	 * Tests if isEmpty() correctly returns true if the tile is empty.
	 */
	@Test
	public void testIsEmptyTrue() {
		assertEquals(true, tile.isEmpty());
	}

	/**
	 * Tests if isEmpty() correctly returns false if the tile is not empty.
	 */
	@Test
	public void testIsEmptyFalse() {
		tile.setValue(10);
		assertEquals(false, tile.isEmpty());
	}

	/**
	 * Tests if isMerged() correctly returns false if the tile has not merged.
	 */
	@Test
	public void testIsMerged() {
		assertEquals(false, tile.isMerged());
	}

	/**
	 * Tests if testMerged() correctly sets the isMerged variable.
	 */
	@Test
	public void testSetMerged() {
		tile.setMerged(true);
		assertEquals(true, tile.isMerged());
	}

	/**
	 * Test if reset() correctly resets the Tile.
	 */
	@Test
	public void testReset() {
		tile.setValue(10);
		tile.setMerged(true);
		assertEquals(10, tile.getValue());
		assertTrue(tile.isMerged());
		tile.reset();
		assertEquals(0, tile.getValue());
		assertFalse(tile.isMerged());
	}
	
	/**
	 * Tests if doubleValue() correctly doubles the value of the tile.
	 */
	@Test
	public void testDoubleValue() {
		tile.setValue(1);
		tile.doubleValue();
		assertEquals(2, tile.getValue());
	}
}
