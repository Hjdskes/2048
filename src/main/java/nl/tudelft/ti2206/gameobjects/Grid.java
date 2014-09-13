package nl.tudelft.ti2206.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.TileHandler;

/**
 * This class represents the 4x4 grid you see when playing 2048.
 * 
 * The internal structure is a simple one-dimensional array. Considering we only
 * require simple operations, this is deemed fast enough, while being very
 * simple at the same time.
 * 
 * For example, imagine the grid being laid out like this:
 * 
 * +---+---+---+---+
 * | 0 | 1 | 2 | 3 |
 * +---+---+---+---+
 * | 4 | 5 | 6 | 7 |
 * +---+---+---+---+
 * | 8 | 9 | 10| 11|
 * +---+---+---+---+
 * | 12| 13| 14| 15|
 * +---+---+---+---+
 * 
 * Now, a square on field 10 can move left or right by adding or subtracting 1
 * from its index. It can move up or down by adding or subtracting 4 from its
 * index.
 * 
 * @author group-21
 */
public class Grid {
	/** This enumeration is used to indicate the direction of a movement. */
	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	/** The width of the grid. */
	public static final int WIDTH = 400;
	/** The height of the grid. */
	public static final int HEIGHT = 400;
	/** The grid contains sixteen tiles. */
	private static final int NTILES = 16;
	/** The lowest value to start with. */
	private static final int TWO = 2;
	/** The highest value to start with. */
	private static final int FOUR = 4;
	/** The array containing all sixteen tiles. */
	protected AnimatedTile[] grid;
	/** A randomizer is needed for filling tiles. */
	private Random random;
	/** The TileHandler is used to move the tiles. */
	private TileHandler tileHandler;
	/** Keeps track of the highest Tile value in the current game. */
	private int highestTile;
	/** A reference to the GameWorld. */
	private GameWorld world;

	/**
	 * Creates a new Grid with NTILES Tile objects.
	 * 
	 * @param world
	 *            The GameWorld this Grid will be placed in.
	 * @param isEmpty
	 *            True if the grid should be empty.
	 */
	public Grid(GameWorld world, boolean isEmpty) {
		this.random = new Random();
		this.grid = new AnimatedTile[NTILES];
		this.tileHandler = new TileHandler(this);
		this.world = world;
		if (!isEmpty) {
			initGrid();
		} else {
			initEmptyGrid();
		}
	}

	/**
	 * Initializes the grid, creating two tiles with a value of 2 or 4 and
	 * setting the rest empty.
	 */
	private void initGrid() {
		initEmptyGrid();

		int loc1 = getRandomEmptyLocation();
		int loc2 = getRandomEmptyLocation();
		while (loc2 == loc1) {
			loc2 = getRandomEmptyLocation();
		}
		grid[loc1].setValue(initialValue());
		grid[loc2].setValue(initialValue());
	}

	/**
	 * Initializes the grid with empty tiles (value 0).
	 */
	private void initEmptyGrid() {
		for (int i = 0; i < NTILES; i++) {
			grid[i] = new AnimatedTile(0);
		}
	}

	/**
	 * Returns a random value, smaller than 16, indicating a location for a new
	 * Tile. This new location is always valid, i.e. there is not
	 * already an Tile there.
	 * 
	 * @return A new valid location.
	 */
	private int getRandomEmptyLocation() {
		int index = random.nextInt(grid.length);
		while (!grid[index].isEmpty() && !isFull()) {
			index = random.nextInt(grid.length);
		}
		return index;
	}

	/**
	 * Returns a random value, which is either 2 or 4. The chances of getting 4
	 * is significantly lower than the change of getting 2.
	 * 
	 * @return A random value, being either 2 or 4.
	 */
	private int initialValue() {
		return random.nextInt(10) < 9 ? TWO : FOUR;
	}

	/**
	 * Sets an Tile's parameters by index.
	 * 
	 * @param index
	 *            The Tile's index on the grid.
	 * @param value
	 *            The Tile's value (should be a multiple of 2 or 0).
	 * @param isMerged
	 *            True if the Tile is merged.
	 */
	public void setTile(int index, int value, boolean isMerged) {
		grid[index].setValue(value);
		grid[index].setMerged(isMerged);
	}

	/**
	 * Updates the grid, by updating all the Tiles it contains and checking for
	 * a new highest value.
	 */
	public void update() {
		for (AnimatedTile t : grid) {
			t.update();
		}
		updateHighestTile();
	}

	/**
	 * Resets the grid, by calling reset on all the Tiles it contains,
	 * reinitializing itself and checking for the new highest value.
	 */
	public void restart() {
		for (AnimatedTile t : grid) {
			t.reset();
		}
		initGrid();
		updateHighestTile();
	}

	/**
	 * This method is the one method used for moving tiles.
	 * 
	 * Its parameter shall indicate which direction is to be moved in. The
	 * actual moving will be delegated to TileHandler. If a move has been made,
	 * it will update the score and create a new Tile.
	 * 
	 * @param direction
	 *            The direction in which is to be moved.
	 */
	public void move(Direction direction) {
		/* If the game is not in running or continuing state, ignore the moves. */
		if (world.isLost() || world.isWon()) {
			return;
		}

		switch (direction) {
		case LEFT:
			tileHandler.moveLeft();
			break;
		case RIGHT:
			tileHandler.moveRight();
			break;
		case UP:
			tileHandler.moveUp();
			break;
		case DOWN:
			tileHandler.moveDown();
			break;
		default:
			break;
		}

		if (tileHandler.isMoveMade()) {
			world.addScore(tileHandler.getScoreIncrement());
			setTile(getRandomEmptyLocation(), initialValue(), false);
		}

		tileHandler.reset();
	}

	/**
	 * Returns true if the grid is full.
	 * 
	 * @return True if the grid is full.
	 */
	public boolean isFull() {
		/* Check each Tile on the grid. */
		for (int index = 0; index < grid.length; index++) {
			/* If any Tile on the grid is empty, the grid is not full. */
			if (grid[index].isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return the amount of possible moves on the grid.
	 * 
	 * @return The amount of possible moves.
	 */
	public int getPossibleMoves() {
		int moves = 0;

		for (int index = 0; index < grid.length; index++) {
			/* An empty Tile cannot move. */
			if (!grid[index].isEmpty()) {
				/* Get current Tile value. */
				int value = grid[index].getValue();
				/* Get all Tile's neighbors. */
				List<AnimatedTile> neighbors = getTileNeighbors(index);

				/* For all neighboring tiles, compare the values. */
				for (AnimatedTile neighbor : neighbors) {
					if (neighbor.getValue() == value
							|| neighbor.getValue() == 0)
						moves++;
				}
			}
		}

		return moves;
	}

	/**
	 * Get a list of neighboring Tiles by index.
	 * 
	 * @param index
	 *            The Tile index.
	 * @return A list of tiles.
	 */
	public List<AnimatedTile> getTileNeighbors(int index) {
		List<AnimatedTile> neighbors = new ArrayList<AnimatedTile>();

		/*
		 * Right neighbor: check if the index we're checking is not the right
		 * edge of the grid by making sure (index + 1) is a not a multiple of 4.
		 */
		if ((index + 1) % 4 != 0 && index + 1 < grid.length) {
			neighbors.add(grid[index + 1]);
		}

		/*
		 * Left neighbor: check if the index we're checking is not the left edge
		 * of the grid by making sure (index - 1) is a not a multiple of 4.
		 */
		if (index % 4 != 0 && index - 1 >= 0) {
			neighbors.add(grid[index - 1]);
		}

		/*
		 * Lower neighbor: check if the index we're checking is not the bottom
		 * edge of the grid by making sure (index + 4) is smaller than
		 * grid.length:
		 */
		if (index + 4 < grid.length) {
			neighbors.add(grid[index + 4]);
		}

		/*
		 * Upper neighbor: check if the index we're checking is not the top edge
		 * of the grid by making sure (index - 4) is not smaller than zero:
		 */
		if (index - 4 >= 0) {
			neighbors.add(grid[index - 4]);
		}

		return neighbors;
	}

	/**
	 * Returns the array containing all the AnimatedTiles.
	 * 
	 * @return The array containing all the AnimatedTiles.
	 */
	public AnimatedTile[] getTiles() {
		return grid;
	}

	/**
	 * Updates the highest Tile value present in the grid.
	 */
	public void updateHighestTile() {
		highestTile = 0;
		for (AnimatedTile t : grid) {
			if (t.getValue() > highestTile)
				highestTile = t.getValue();
		}
	}

	/**
	 * Returns the highest Tile value present in the grid.
	 * 
	 * @return The highest Tile value.
	 */
	public int getCurrentHighestTile() {
		return highestTile;
	}

	/**
	 * Returns the TileHandler object used by the grid.
	 * 
	 * @return The TileHandler object.
	 */
	public TileHandler getTileHandler() {
		return tileHandler;
	}

	/**
	 * Sets the TileHandler object used by the grid.
	 * 
	 * @param tileHandler
	 *            The TileHandler object to set.
	 */
	public void setTileHandler(TileHandler tileHandler) {
		this.tileHandler = tileHandler;
	}
}
