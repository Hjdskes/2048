package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.TileHandler;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
	private TileHandler tileHandler;

	private static Skin skin;
	private static Texture texture;
	private static TextureRegion region;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@Before
	public void setup() {
		new HeadlessLauncher().launch();

		skin = mock(Skin.class);
		texture = mock(Texture.class);
		region = mock(TextureRegion.class);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
		when(skin.getRegion(anyString())).thenReturn(region);
		AssetHandler.setSkin(skin);

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
		grid.setTile(0, 2048);
		grid.setTile(1, 4096);
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
	 * Tests if a move is correctly not made because the game has been won.
	 */
	@Test
	public void testMoveImpossibleWhenWon() {
		TwentyFourtyGame.setState(GameState.WON);
		grid.move(Direction.LEFT);
		verify(tileHandler, times(0)).moveLeft();
	}

	/**
	 * Tests if a move is correctly not made because the game has been lost.
	 */
	@Test
	public void testMoveImpossibleWhenLost() {
		TwentyFourtyGame.setState(GameState.LOST);
		grid.move(Direction.LEFT);
		verify(tileHandler, times(0)).moveLeft();
	}

	/**
	 * Tests if getPossibleMoves() correctly returns 0 when no move is possible.
	 */
	@Test
	public void testGetPossibleMovesFalse() {
		int[] grid_noMoves = { TWO, FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO, TWO,
				FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO };

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
		int[] grid_noMoves = { TWO, FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO, TWO,
				FOUR, TWO, FOUR, FOUR, TWO, FOUR, TWO };

		// initialize grid:
		for (int i = 0; i < grid_noMoves.length; i++)
			grid.setTile(i, grid_noMoves[i]);

		// get neighbors for tile at index 5 (should be 4 in total)
		List<Tile> neighbours = grid.getTileNeighbors(5);

		int found = 0;

		for (Tile neighbour : neighbours) {
			if (neighbour.getValue() == FOUR)
				found++;
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
