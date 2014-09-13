package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.TileHandler;

import org.junit.Before;
import org.junit.Test;

public class TileHandlerTest {

	private Grid grid;
	private TileHandler tileMover;
	private GameWorld world;
	private Grid emptyGrid;

	/**
	 * Setting up the needed variables for testing. To test the methods the
	 * following grid is used:
	 * +---+---+---+---+ 
	 * | 8 | 0 | 4 | 8 |
	 * +---+---+---+---+ 
	 * | 0 | 8 | 0 | 0 | 
	 * +---+---+---+---+ 
	 * | 0 | 0 | 8 | 0 |
	 * +---+---+---+---+ 
	 * | 0 | 0 | 0 | 8 |
	 * +---+---+---+---+ 
	 *  With this grid we
	 * can test merges and effects on other tiles.
	 */
	@Before
	public void setup() {
		grid = new Grid(world, true);
		emptyGrid = new Grid(world, true);
		for (int i = 0; i < 16; i = i + 5) {
			grid.getTiles()[i].setValue(8);
		}
		grid.getTiles()[2].setValue(4);
		grid.getTiles()[3].setValue(8);
		tileMover = new TileHandler(grid);
	}

	/**
	 * Test simple move UP without any merges or other affected tiles.
	 */
	@Test
	public void testMoveUp() {
		tileMover.moveUp();
		// test if the tile is in the expected location.
		assertTrue(grid.getTiles()[1].getValue() == 8);
		// test if the tile has disappeared from its previous location
		assertFalse(grid.getTiles()[5].getValue() == 8);
	}

	/**
	 * Test simple move Right without any merges or other affected tiles.
	 */
	@Test
	public void testMoveRight() {
		tileMover.moveRight();
		// test if the tile is in the expected location.
		assertTrue(grid.getTiles()[7].getValue() == 8);
		// test if the tile has disappeared from its previous location
		assertFalse(grid.getTiles()[5].getValue() == 8);
	}

	/**
	 * Test simple move Down without any merges or other affected tiles.
	 */
	@Test
	public void testMoveDown() {
		tileMover.moveDown();
		// test if the tile is in the expected location.
		assertTrue(grid.getTiles()[12].getValue() == 8);
		// test if the tile has disappeared from its previous location
		assertFalse(grid.getTiles()[0].getValue() == 8);
	}

	/**
	 * Test simple move Left without any merges or other affected tiles.
	 */
	@Test
	public void testMoveLeft() {
		tileMover.moveLeft();
		// test if the tile is in the expected location.
		assertTrue(grid.getTiles()[0].getValue() == 8);
	}

	/**
	 * Test a move on an empty grid. 
	 */
	@Test
	public void testMoveEmptyGrid() {
		tileMover = new TileHandler(emptyGrid);
		tileMover.moveDown();
		assertEquals(toString(emptyGrid), "0000.0000.0000.0000");

	}

	/**
	 * Test simple merging, no other tiles affected by the merge.
	 */
	@Test
	public void testMergingNoOtherAffected() {
		tileMover.moveUp();
		// test if the new merged tile is in the expected location and
		// if the other tiles have disappeared.
		assertEquals(toString(grid), "88416.0080.0000.0000"); 
	}

	/**
	 * Not mergable tiles Placeholder. Make sure that tiles of different kinds
	 * do not merge. Note to Arthur: Change to Parameters implementation.
	 */
	@Test
	public void testNonMergableTiles() {
		tileMover.moveUp();
		// test if the tiles are in expected locations.
		assertTrue(grid.getTiles()[2].getValue() == 4);
		assertTrue(grid.getTiles()[6].getValue() == 8);
		// test if the moved tile has disappeared from its previous location.
		assertFalse(grid.getTiles()[10].getValue() == 8);

	}

	/**
	 * Make sure tiles don't merge when there is a tile of a different kind
	 * between them.
	 */
	@Test
	public void testNonMergableTiles2() {
		tileMover.moveRight();
		// test if the tiles are in expected locations.
		assertEquals(toString(grid), "0848.0008.0008.0008"); 
	}

	/**
	 * Test if multiple merge combo's can be made.
	 * In this case: | 8 | 8 | 8 | 8 | ==> | 16 | 16 | 0 | 0 | 
	 * Vertically
	 */
	@Test
	public void testDoubleMerge() {
		tileMover.moveLeft();
		tileMover.moveDown();
		assertEquals(toString(grid), "0000.0000.16000.16480");
	}

	/**
	 * Test the order of merging blocks.
	 * Moving a row of three tiles of the same type should firstly merge
	 * the tiles from the direction the tiles are being moved.
	 * For example: 
	 * | 8 | 8 | 8 | 0 | ===> | 16| 8 | 0 | 0 |
	 * and not:
	 * | 8 | 8 | 8 | 0 | ===> | 8 | 16| 0 | 0 |
	 */
	@Test
	public void testMergeOrder() {
		tileMover.moveDown();
		tileMover.moveLeft();
		assertEquals(toString(grid), "0000.0000.4000.168160");
	}

	/**
	 * Test the isMoveMade method when a move is made.
	 */
	@Test
	public void testMoveMade() {
		tileMover.moveUp();
		assertTrue(tileMover.isMoveMade());
	}

	/**
	 * Test the isMoveMade method when a move has NOT been made.
	 */
	@Test
	public void testMoveNotMade() {
		assertFalse(tileMover.isMoveMade());
	}

	/**
	 * Test the isMoveMade method on an empty grid.
	 */
	@Test
	public void testIsMoveMadeEmptyGrid() {
		tileMover = new TileHandler(emptyGrid);
		tileMover.moveUp();
		assertFalse(tileMover.isMoveMade());

	}

	/**
	 * Test the increase in score when tiles are merged.
	 */
	@Test
	public void testScoreIncrement() {
		tileMover.moveUp();
		assertEquals(tileMover.getScoreIncrement(), 16);		
	}

	/**
	 * Test if the increment in score is set to 0,
	 * when the tileMover is reset.
	 */
	@Test
	public void testScoreIncrementReset() {
		tileMover.moveUp();
		tileMover.reset();
		assertEquals(tileMover.getScoreIncrement(), 0);		
	}

	/**
	 * The toString method returns a string representation of a grid
	 * as followed: "FIRSTROW.SECONDROW.THIRDROW.FOURTHROW"
	 */
	public String toString(Grid gr) {
		String res = "";
		for (int i = 0; i < 16; i++) {
			if(i%4 == 0 && i != 0)
				res += ".";
			res += gr.getTiles()[i].getValue();
		}
		return res;
	}

	/**
	 * Test for the toString method above.
	 */
	@Test
	public void testToString() {
		//test if the grid has the same rows as generated by the method.
		assertEquals(toString(grid), "8048.0800.0080.0008");
	}

	/**
	 * Test if toString method on an empty grid.
	 */
	@Test
	public void testToStringEmpty() {
		assertEquals(toString(emptyGrid), "0000.0000.0000.0000");
	}
	
	/**
	 * Test for the reset method.
	 */
	@Test
	public void testReset() {
		tileMover.reset();
		assertEquals(tileMover.getScoreIncrement(), 0);
		assertFalse(tileMover.isMoveMade());
	}
}