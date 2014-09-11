package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.helpers.TileMover;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GridTest {

	private static final int TWO = 2;
	private static final int FOUR = 4;
	
	Grid grid;
	Random random;
	GameWorld world;
	TileMover mover;

	@Before
	public void setup() {
		world = Mockito.mock(GameWorld.class);
		mover = Mockito.mock(TileMover.class);
		grid = new Grid(world, false);
		random = new Random();
		grid.setTileMover(mover);
	//	world.setGameState(GameState.RUNNING);
	}

	@Test
	public void testConstructor() {
		assertNotNull(grid);
	}

	@Test
	public void testConstructor2() {
		int filledTiles = 0;
		int wrongTiles = 0;
		for (int i = 0; i < 16; i++) {
			if (!grid.getTiles()[i].isEmpty()) {
				// make sure the grid only contains 2's and/or 4's and empty
				// tiles
				if (grid.getTiles()[i].getValue() != TWO
						&& grid.getTiles()[i].getValue() != FOUR)
					wrongTiles++;
				
				filledTiles++;
			}
		}
		assertEquals(2, filledTiles);
		assertEquals(0, wrongTiles);
	}
	
	@Test
	public void testRandomEmptyLocation() {
		int wrongValue = 0;
		for (int i = 0; i < 1000; i++) {
			int empty = getRandomEmptyLocation();
			grid.getTiles()[empty].setValue(2);
			if (!(empty >= 0 && empty < 16))
				wrongValue += 1;
		}
		assertEquals(0, wrongValue);
	}

	@Test
	public void testHighestTile() {
		int empty = getRandomEmptyLocation();
		grid.getTiles()[empty].setValue(1024);
		
		int empty2 = getRandomEmptyLocation();
		grid.getTiles()[empty2].setValue(2048);
		
		grid.updateHighestTile();
		
		assertEquals(2048, grid.getHighestTile());
	}

	@Test
	public void testFullGrid() {
		for (int i = 0; i < 16; i++) {
			grid.getTiles()[i].setValue(2);
		}
		assertTrue(grid.isFull());

	}

	@Test
	public void testIsFull2() {
		for (int i = 0; i < 16; i = i + 2) {
			grid.getTiles()[i].setValue(2);
		}
		assertFalse(grid.isFull());
	}
	
	// TODO: How to test this?
//	@Test
//	public void testTileAddedOnMove() {
//		grid.move(Direction.LEFT);
//		if (!grid.getTileMover().isMoveMade())
//			grid.move(Direction.RIGHT);
//	}

	@Test
	public void testNoPossibleMoves() {
		int[] grid_noMoves = { TWO, FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO, TWO,
				FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO };

		for (int i = 0; i < grid_noMoves.length; i++)
			grid.setTile(i, grid_noMoves[i], false);

		int moves = grid.getPossibleMoves();

		assertEquals(0, moves);
	}

	@Test
	public void testNoPossibleMoves2() {
		// create an empty grid
		grid = new Grid(world, true);
		
		assertEquals(0, grid.getPossibleMoves());
	}
	
	@Test
	public void testTileNeighbours() {
		int[] grid_noMoves = { TWO, FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO, TWO,
				FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO };

		// initialize grid:
		for (int i = 0; i < grid_noMoves.length; i++)
			grid.setTile(i, grid_noMoves[i], false);

		// get neighbours for tile at index 5 (should be 4 in total)
		List<Tile> neighbours = grid.getTileNeighbors(5);

		int found = 0;

		for (Tile neighbour : neighbours) {
			if (neighbour.getValue() == FOUR)
				found++;
		}
		assertEquals(4, found);
	}
	
	/**
	 * Testing the move method.
	 */
	@Test
	public void testMove() { 
		grid.move(Direction.DOWN);
		Mockito.verify(mover).moveDown();
		grid.move(Direction.LEFT);
		Mockito.verify(mover).moveLeft();
		grid.move(Direction.RIGHT);
		Mockito.verify(mover).moveRight();
		grid.move(Direction.UP);
		Mockito.verify(mover).moveUp();
	}

	/*
	 * Copy of a private method in grid
	 */
	public int getRandomEmptyLocation() {
		int index = random.nextInt(grid.getTiles().length);
		while (!grid.getTiles()[index].isEmpty() && !grid.isFull()) {
			index = random.nextInt(grid.getTiles().length);
		}
		return index;
	}
}
