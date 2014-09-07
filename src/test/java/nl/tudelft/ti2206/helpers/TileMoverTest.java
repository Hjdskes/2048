package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.Before;
import org.junit.Test;

public class TileMoverTest {

	private Grid grid;
	private TileMover tileMover;
	GameWorld world;

	/**
	 * Setting up the needed variables for testing.
	 * To test the methods the following grid is used:
	 * +---+---+---+---+ 
	 * | 8 | 0 | 4 | 8 | 
	 * +---+---+---+---+ 
	 * | 0 | 8 | 0 | 0 | 
	 * +---+---+---+---+ 
	 * | 0 | 0 | 8 | 0 | 
	 * +---+---+---+---+ 
	 * | 0 | 0 | 0 | 8 |
	 * +---+---+---+---+
	 * With this grid we can test merges and effects on other tiles.
	 */
	
	@Before
	public void setup() {
		grid = new Grid(world, true);
		for (int i = 0; i < 16; i = i + 5) {
			grid.getTiles()[i].setValue(8);
		}
		grid.getTiles()[2].setValue(4);
		grid.getTiles()[3].setValue(8);
		tileMover = new TileMover(grid);
	}

	/**
	 * Test simple move UP without any merges or other affected tiles.
	 */
	@Test
	public void testMoveUp() {
		tileMover.moveUp();
		//test if the tile is in the expected location.
		assertTrue(grid.getTiles()[1].getValue() == 8);
		//test if the tile has disappeared from its previous location
		assertFalse(grid.getTiles()[5].getValue() == 8);
	}
	
	/**
	 * Test simple move Right without any merges or other affected tiles.
	 */
	@Test
	public void testMoveRight() {
		tileMover.moveRight();
		//test if the tile is in the expected location.
		assertTrue(grid.getTiles()[7].getValue() == 8);
		//test if the tile has disappeared from its previous location
		assertFalse(grid.getTiles()[5].getValue() == 8);
	}
	
	/**
	 * Test simple move Down without any merges or other affected tiles.
	 */
	@Test
	public void testMoveDown() {
		tileMover.moveDown();
		//test if the tile is in the expected location.
		assertTrue(grid.getTiles()[12].getValue() == 8);
		//test if the tile has disappeared from its previous location
		assertFalse(grid.getTiles()[0].getValue() == 8);
	}
	
	/**
	 * Test simple move Left without any merges or other affected tiles.
	 */
	@Test
	public void testMoveLeft() {
		tileMover.moveLeft();
		//test if the tile is in the expected location.
		assertTrue(grid.getTiles()[0].getValue() == 8);
	}
}