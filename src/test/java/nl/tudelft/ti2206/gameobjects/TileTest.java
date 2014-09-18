package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.gameobjects.Tile;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

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
		tile = new Tile(0);
	}

	/**
	 * Tests if the constructor successfully creates a new Tile object.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(tile);
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
	 * Tests if resetValue() correctly resets the value of the tile.
	 */
	@Test
	public void testResetValue() {
		tile.setValue(10);
		assertEquals(10, tile.getValue());
		tile.resetValue();
		assertEquals(0, tile.getValue());
	}

	/**
	 * Tests if doubleValue() correctly doubles the value of the tile.
	 */
	@Test
	public void testDoubleValue() {
		tile.doubleValue();
		assertEquals(2, tile.getValue());
		tile.doubleValue();
		assertEquals(4, tile.getValue());
	}

	/**
	 * Tests if reset() correctly resets the tile.
	 */
	@Test
	public void testReset() {
		tile.doubleValue();
		tile.setMerged(true);
		tile.reset();
		assertEquals(0, tile.getValue());
		assertEquals(false, tile.isMerged());
	}
}
