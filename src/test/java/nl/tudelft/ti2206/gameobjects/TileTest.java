package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.gameobjects.Tile;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

/**
 * Test class for the Tile class.
 * 
 * @author group-21
 */
public class TileTest {
	Tile tile;

	@Before
	public void setup() {
		tile = new Tile();
	}

	@Test
	public void testConstructor() {
		assertNotNull(tile);
	}

	@Test
	public void testGetValue() {
		assertEquals(tile.getValue(), 0);
	}

	@Test
	public void testSetValue() {
		tile.setValue(10);
		assertEquals(tile.getValue(), 10);
	}

	@Test
	public void testIsEmptyTrue() {
		assertEquals(tile.isEmpty(), true);
	}

	@Test
	public void testIsEmptyFalse() {
		tile.setValue(10);
		assertEquals(tile.isEmpty(), false);
	}

	@Test
	public void testIsMerged() {
		assertEquals(tile.isMerged(), false);
	}

	@Test
	public void testSetMerged() {
		tile.setMerged(true);
		assertEquals(tile.isMerged(), true);
	}

	@Test
	public void testResetValue() {
		tile.setValue(10);
		assertEquals(tile.getValue(), 10);
		tile.resetValue();
		assertEquals(tile.getValue(), 0);
	}

	@Test
	public void testDoubleValue() {
		tile.doubleValue();
		assertEquals(tile.getValue(), 2);
		tile.doubleValue();
		assertEquals(tile.getValue(), 4);
	}

	@Test
	public void testReset() {
		tile.doubleValue();
		tile.setMerged(true);
		tile.reset();
		assertEquals(tile.getValue(), 0);
		assertEquals(tile.isMerged(), false);
	}
}
