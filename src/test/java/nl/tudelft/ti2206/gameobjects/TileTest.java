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
		tile = new Tile(0);
	}

	@Test
	public void testConstructor() {
		assertNotNull(tile);
	}

	@Test
	public void testGetValue() {
		assertEquals(0, tile.getValue());
	}

	@Test
	public void testSetValue() {
		tile.setValue(10);
		assertEquals(10, tile.getValue());
	}

	@Test
	public void testIsEmptyTrue() {
		assertEquals(true, tile.isEmpty());
	}

	@Test
	public void testIsEmptyFalse() {
		tile.setValue(10);
		assertEquals(false, tile.isEmpty());
	}

	@Test
	public void testIsMerged() {
		assertEquals(false, tile.isMerged());
	}

	@Test
	public void testSetMerged() {
		tile.setMerged(true);
		assertEquals(true, tile.isMerged());
	}

	@Test
	public void testResetValue() {
		tile.setValue(10);
		assertEquals(10, tile.getValue());
		tile.resetValue();
		assertEquals(0, tile.getValue());
	}

	@Test
	public void testDoubleValue() {
		tile.doubleValue();
		assertEquals(2, tile.getValue());
		tile.doubleValue();
		assertEquals(4, tile.getValue());
	}

	@Test
	public void testReset() {
		tile.doubleValue();
		tile.setMerged(true);
		tile.reset();
		assertEquals(0, tile.getValue());
		assertEquals(false, tile.isMerged());
	}
}
