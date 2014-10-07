package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.TileHandler;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A test class for TileHandler.
 */
public class TileHandlerTest {
	/** The grid containing the tiles. */
	private Grid grid;

	/** The object under test. */
	private TileHandler tileHandler;

	/** The grid containing no tiles. */
	private Grid emptyGrid;

	/**
	 * Sets up the needed variables for testing. To test the methods the
	 * following grid is used:
	 * 
	 * +---+---+---+---+ 
	 * | 8 | 0 | 4 | 8 |
	 * +---+---+---+---+ 
	 * | 0 | 8 | 0 | 0 | 
	 * +---+---+---+---+ 
	 * | 0 | 0 | 8 | 0 |
	 * +---+---+---+---+ 
	 * | 0 | 0 | 0 | 8 |
	 * +---+---+---+---+
	 *  
	 * With this grid we can test merges and effects on other tiles.
	 */
	@Before
	public void setup() {
		Skin skin = mock(Skin.class);
		Texture texture = mock(Texture.class);
		TextureRegion region = mock(TextureRegion.class);
		when(skin.getRegion(anyString())).thenReturn(region);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
		AssetHandler.getInstance().setSkin(skin);
		
		grid = new Grid(true, skin, region);
		emptyGrid = new Grid(true, skin, region);
		for (int i = 0; i < 16; i = i + 5) {
			grid.getTiles()[i].setValue(8);
		}
		grid.getTiles()[2].setValue(4);
		grid.getTiles()[3].setValue(8);
		tileHandler = new TileHandler(grid.getTiles());
	}

	/**
	 * Tests a simple move upwards without any merges or other affected tiles.
	 */
	@Test
	public void testMoveUp() {
		tileHandler.moveUp();
		/* Test if the tile is in the expected location. */
		assertTrue(grid.getTiles()[1].getValue() == 8);
		/* Test if the tile has disappeared from its previous location. */
		assertFalse(grid.getTiles()[5].getValue() == 8);
	}

	/**
	 * Tests a simple move to the right without any merges or other affected tiles.
	 */
//	@Test
//	public void testMoveRight() {
//		tileHandler.moveRight();
//		/* Test if the tile is in the expected location. */
//		assertTrue(grid.getTiles()[7].getValue() == 8);
//		/* Test if the tile has disappeared from its previous location. */
//		assertFalse(grid.getTiles()[5].getValue() == 8);
//	}

	/**
	 * Tests a simple move downwards without any merges or other affected tiles.
	 */
	@Test
	public void testMoveDown() {
		tileHandler.moveDown();
		/* Test if the tile is in the expected location. */
		assertTrue(grid.getTiles()[12].getValue() == 8);
		/* Test if the tile has disappeared from its previous location. */
		assertFalse(grid.getTiles()[0].getValue() == 8);
	}

	/**
	 * Tests simple move to the left without any merges or other affected tiles.
	 */
	@Test
	public void testMoveLeft() {
		tileHandler.moveLeft();
		/* Test if the tile is in the expected location. */
		assertTrue(grid.getTiles()[0].getValue() == 8);
	}

	/**
	 * Tests a move on an empty grid. 
	 */
	@Test
	public void testMoveEmptyGrid() {
		tileHandler = new TileHandler(emptyGrid.getTiles());
		tileHandler.moveDown();
		assertEquals(emptyGrid.toString(), "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
	}

	/**
	 * Tests simple merging, no other tiles are affected by the merge.
	 */
	@Test
	public void testMergingNoOtherAffected() {
		tileHandler.moveUp();
		/* Test if the new merged tile is in the expected location and
		 * if the other tiles have disappeared. */
		assertEquals(grid.toString(), "8,8,4,16,0,0,8,0,0,0,0,0,0,0,0,0"); 
	}

	/**
	 * Nonmergable tiles Placeholder. Makes sure that tiles of different kinds
	 * do not merge.
	 */
	@Test
	public void testNonMergableTiles() {
		tileHandler.moveUp();
		/* Test if the tiles are in their expected locations. */
		assertTrue(grid.getTiles()[2].getValue() == 4);
		assertTrue(grid.getTiles()[6].getValue() == 8);
		/* Test if the moved tile has disappeared from its previous location. */
		assertFalse(grid.getTiles()[10].getValue() == 8);

	}

	/**
	 * Makes sure tiles don't merge when there is a tile of a different kind
	 * between them.
	 */
	@Test
	public void testNonMergableTiles2() {
		tileHandler.moveRight();
		/* Test if the tiles are in expected locations. */
		assertEquals(grid.toString(), "0,8,4,8,0,0,0,8,0,0,0,8,0,0,0,8"); 
	}

	/**
	 * Tests if multiple merge combinations can be made.
	 * In this case: | 8 | 8 | 8 | 8 | ==> | 0 | 0 | 0 | 0 |
	 *               | 0 | 0 | 0 | 0 |     | 16| 16| 0 | 0 |
	 */
	@Test
	public void testDoubleMerge() {
		tileHandler.moveLeft();
		tileHandler.moveDown();
		assertEquals(grid.toString(), "0,0,0,0,0,0,0,0,16,0,0,0,16,4,8,0");
	}

	/**
	 * Tests the order of merging blocks.
	 * Moving a row of three tiles of the same type should firstly merge
	 * the tiles from the direction the tiles are being moved.
	 * For example: 
	 * | 8 | 8 | 8 | 0 | ===> | 16| 8 | 0 | 0 |
	 * and not:
	 * | 8 | 8 | 8 | 0 | ===> | 8 | 16| 0 | 0 |
	 */
	@Test
	public void testMergeOrder() {
		tileHandler.moveDown();
		tileHandler.moveLeft();
		assertEquals(grid.toString(), "0,0,0,0,0,0,0,0,4,0,0,0,16,8,16,0");
	}

	/**
	 * Tests the isMoveMade method when a move is made.
	 */
	@Test
	public void testMoveMade() {
		tileHandler.moveUp();
		assertTrue(tileHandler.isMoveMade());
	}

	/**
	 * Tests the isMoveMade method when a move has not been made.
	 */
	@Test
	public void testMoveNotMade() {
		assertFalse(tileHandler.isMoveMade());
	}

	/**
	 * Tests the isMoveMade method on an empty grid.
	 */
	@Test
	public void testIsMoveMadeEmptyGrid() {
		tileHandler = new TileHandler(emptyGrid.getTiles());
		tileHandler.moveUp();
		assertFalse(tileHandler.isMoveMade());

	}

	/**
	 * Tests the increase in score when tiles are merged.
	 */
	@Test
	public void testScoreIncrement() {
		tileHandler.moveUp();
		assertEquals(tileHandler.getScoreIncrement(), 16);		
	}

	/**
	 * Tests if the increment in score is set to 0,
	 * when the tileHandler is reset.
	 */
	@Test
	public void testScoreIncrementReset() {
		tileHandler.moveUp();
		tileHandler.reset();
		assertEquals(tileHandler.getScoreIncrement(), 0);		
	}

	/**
	 * Tests for the reset method.
	 */
	@Test
	public void testReset() {
		tileHandler.reset();
		assertEquals(tileHandler.getScoreIncrement(), 0);
		assertFalse(tileHandler.isMoveMade());
	}
}