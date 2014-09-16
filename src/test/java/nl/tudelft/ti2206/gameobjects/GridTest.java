package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import nl.tudelft.ti2206.game.HeadlessLauncher;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the Grid class.
 *
 * @author group-21
 */
public class GridTest {
	/** The lowest value to start with. */
	private static final int TWO = 2;
	/** The highest value to start with. */
	private static final int FOUR = 4;
	/** The object under test. */
	private Grid grid;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@Before
	public void setup() {
		HeadlessLauncher launcher = new HeadlessLauncher();
		launcher.launch();

		grid = new Grid(false);
	}

	/**
	 * Tests if the constructor successfully creates and initializes a new Grid
	 * object.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(grid);

		int filledTiles = 0;
		int wrongTiles = 0;
		for (int i = 0; i < 16; i++) {
			if (!grid.getTiles()[i].isEmpty()) {
				/*
				 * Make sure the grid only contains 2's and/or 4's and empty
				 * tiles.
				 */
				if (grid.getTiles()[i].getValue() != TWO
						&& grid.getTiles()[i].getValue() != FOUR) {
					wrongTiles++;
				}
				filledTiles++;
			}
		}
		assertEquals(2, filledTiles);
		assertEquals(0, wrongTiles);
	}

	/**
	 * Tests if getCurrentHighestTile() returns the correct value.
	 */
	@Test
	public void testGetHighestTile() {
		grid.setTile(0, 2048, false);
		grid.setTile(1, 4096, false);
		grid.updateHighestTile();
		assertEquals(4096, grid.getCurrentHighestTile());
	}

	/**
	 * Tests if isFull() correctly returns true if the grid is full.
	 */
	@Test
	public void testIsFull() {
		for (int i = 0; i < 16; i++) {
			grid.getTiles()[i].setValue(2);
		}
		assertTrue(grid.isFull());
	}

	/**
	 * Tests if isFull() correctly returns false if the grid is not full.
	 */
	@Test
	public void testIsFullFalse() {
		for (int i = 0; i < 16; i = i + 2) {
			grid.getTiles()[i].setValue(2);
		}
		assertFalse(grid.isFull());
	}

	/**
	 * Tests if getPossibleMoves() correctly returns 0 when no move is possible.
	 */
	@Test
	public void testGetPossibleMovesFalse() {
		int[] grid_noMoves = { TWO, FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO, TWO,
				FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO };

		for (int i = 0; i < grid_noMoves.length; i++) {
			grid.setTile(i, grid_noMoves[i], false);
		}

		assertEquals(0, grid.getPossibleMoves());
	}

	/**
	 * Tests if getPossibleMoves() correctly returns a positive number of moves
	 * on a grid with two tiles (can't test this with a definite value since the
	 * grid is initialized randomly).
	 */
	@Test
	public void testGetPossibleMovesTrue() {
		assertTrue(grid.getPossibleMoves() > 0);
	}

	@Test
	public void testGetTileNeighbours() {
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
	 * Tests if restart() correctly resets all tiles and the current highest
	 * tile.
	 */
	@Test
	public void testRestart() {
		int tiles = 0;

		/*
		 * Set two extra tiles on the grid (they can possible overlap, so no
		 * assert is possible here) and confirm the highest tile.
		 */
		grid.setTile(4, 2048, false);
		grid.setTile(3, 4096, false);
		grid.updateHighestTile();
		assertEquals(4096, grid.getCurrentHighestTile());

		/*
		 * Now upon restart, the highest value should be 2 or 4 and there should
		 * be two tiles.
		 */
		grid.restart();
		tiles = 0;
		assertTrue(grid.getCurrentHighestTile() == 2
				|| grid.getCurrentHighestTile() == 4);
		for (Tile tile : grid.getTiles()) {
			if (!tile.isEmpty()) {
				tiles++;
			}
		}
		assertEquals(2, tiles);
	}
}
