package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.handlers.TileHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test suite for the Grid class.
 */
public class GridTest {
	@Mock private TileHandler tileHandler;

	/** The object under test. */
	private Grid grid;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		new HeadlessLauncher().launch();

		tileHandler = mock(TileHandler.class);

		grid = new Grid(false);
		grid.setTileHandler(tileHandler);
		TwentyFourtyGame.setState(GameState.RUNNING);
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
				if (grid.getTiles()[i].getValue() != Grid.TWO
						&& grid.getTiles()[i].getValue() != Grid.FOUR) {
					wrongTiles++;
				}
				filledTiles++;
			}
		}
		assertEquals(2, filledTiles);
		assertEquals(0, wrongTiles);
	}

	@Test
	public void testSetGetScore() {
		grid.setScore(200);
		assertEquals(200, grid.getScore());
	}

	/**
	 * Tests if getCurrentHighestTile() returns the correct value.
	 */
	@Test
	public void testGetHighestTile() {
		grid.setTile(0, 2048);
		grid.setTile(1, 4096);
		grid.updateHighestTile();
		assertEquals(4096, grid.getCurrentHighestTile());
	}

	@Test
	public void testTileAddedOnMove() {
		when(tileHandler.isMoveMade()).thenReturn(true);

		int endTiles = 0;
		int tiles = 0;

		// make a move to the left. This can always be done because of the stub.
		grid.move(Direction.LEFT);

		// if the tile is not empty, count it. If the tile is merged, dont
		// increment endTiles because a merge has taken place.
		for (Tile tile : grid.getTiles()) {
			if (!tile.isEmpty()) {
				tiles++;
				if (!tile.isMerged()) {
					endTiles++;
				}
			}
		}

		assertEquals(endTiles, tiles);
	}

	/**
	 * Tests if getPossibleMoves() correctly returns 0 when no move is possible.
	 */
	@Test
	public void testGetPossibleMovesFalse() {
		int[] grid_noMoves = { Grid.TWO, Grid.FOUR, Grid.TWO, Grid.FOUR,
							   Grid.FOUR, Grid.TWO, Grid.FOUR, Grid.TWO,
							   Grid.TWO, Grid.FOUR, Grid.TWO, Grid.FOUR,
							   Grid.FOUR, Grid.TWO, Grid.FOUR, Grid.TWO };

		for (int i = 0; i < grid_noMoves.length; i++) {
			grid.setTile(i, grid_noMoves[i]);
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
		int[] grid_noMoves = { Grid.TWO, Grid.FOUR, Grid.TWO, Grid.FOUR,
				   Grid.FOUR, Grid.TWO, Grid.FOUR, Grid.TWO,
				   Grid.TWO, Grid.FOUR, Grid.TWO, Grid.FOUR,
				   Grid.FOUR, Grid.TWO, Grid.FOUR, Grid.TWO };

		// initialize grid:
		for (int i = 0; i < grid_noMoves.length; i++) {
			grid.setTile(i, grid_noMoves[i]);
		}

		// get neighbors for tile at index 5 (should be 4 in total)
		List<Tile> neighbours = grid.getTileNeighbors(5);

		int found = 0;
		for (Tile neighbour : neighbours) {
			if (neighbour.getValue() == Grid.FOUR) {
				found++;
			}
		}
		assertEquals(4, found);
	}

	/**
	 * Tests the move method when moving down.
	 */
	@Test
	public void testMoveDown() {
		grid.move(Direction.DOWN);
		verify(tileHandler).moveDown();
	}

	/**
	 * Tests the move method when moving up.
	 */
	@Test
	public void testMoveUp() {
		grid.move(Direction.UP);
		verify(tileHandler).moveUp();
	}

	/**
	 * Tests the move method when moving left.
	 */
	@Test
	public void testMoveLeft() {
		grid.move(Direction.LEFT);
		verify(tileHandler).moveLeft();
	}

	/**
	 * Tests the move method when moving right.
	 */
	@Test
	public void testMoveRight() {
		grid.move(Direction.RIGHT);
		verify(tileHandler).moveRight();
	}

	/**
	 * Tests if getTileHandler() correctly returns the tileHandler.
	 */
	@Test
	public void testGetTileHandler() {
		assertTrue(grid.getTileHandler() instanceof TileHandler);
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
		grid.setTile(4, 2048);
		grid.setTile(3, 4096);
		grid.updateHighestTile();
		assertEquals(4096, grid.getCurrentHighestTile());

		/*
		 * Now upon restart, the highest value should be 2 or 4 and there should
		 * be two tiles.
		 */
		grid.restart();
		tiles = 0;
		assertTrue(grid.getCurrentHighestTile() == Grid.TWO
				|| grid.getCurrentHighestTile() == Grid.FOUR);
		for (Tile tile : grid.getTiles()) {
			if (!tile.isEmpty()) {
				tiles++;
			}
		}
		assertEquals(2, tiles);
	}

	@Test
	public void testToString() {
		grid = new Grid(true);
		assertEquals("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", grid.toString());
		grid.setTile(0, 2);
		assertEquals("2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", grid.toString());
	}

	@Test
	public void testObjectName() {
		String objectName = "GridTest";
		grid.setObjectName(objectName);
		assertEquals(objectName, grid.getObjectName());	
	}
 }
