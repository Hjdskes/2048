package nl.tudelft.ti2206.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.helpers.TileMover;

/**
 * This class represents the 4x4 grid you see when playing 2048.
 * 
 * The internal structure is a simple two-dimensional array. Considering we only
 * require simple operations, this is deemed fast enough, while being very
 * simple at the same time.
 * 
 * For example, imagine the grid being laid out like this:
 * 
 * +---+---+---+---+ | 0 | 1 | 2 | 3 | +---+---+---+---+ | 4 | 5 | 6 | 7 |
 * +---+---+---+---+ | 8 | 9 | 10| 11| +---+---+---+---+ | 12| 13| 14| 15|
 * +---+---+---+---+
 * 
 * Now, a square on field 10 can move left or right by adding or subtracting 1
 * from its index. It can move up or down by adding or subtracting 4 from its
 * index.
 * 
 * @author group-21
 * 
 */
public class Grid {

	/**
	 * This enum is used to indicate the direction of movement.
	 */
	public enum Direction {
		/** Upwards. */
		UP,
		/** Downwards. */
		DOWN,
		/** Left. */
		LEFT,
		/** Right. */
		RIGHT;
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
	private Tile[] grid;
	/** Randomizer needed for filling tiles. */
	private Random random;
	/** TileMover is used to move the tiles. */
	private TileMover mover;
	/** Keeps track of the highest tile value in game. */
	private int highest;
	/** The game world. */
	private GameWorld world;

	/**
	 * Creates a new Grid with NTILES Tile objects.
	 * 
	 * @param isEmpty
	 *            true if grid should be empty
	 */
	public Grid(GameWorld world, boolean isEmpty) {
		this.random = new Random();
		this.grid = new Tile[NTILES];
		this.mover = new TileMover(this);
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
	 * Initialize grid with empty tiles (value 0).
	 */
	private void initEmptyGrid() {
		for (int i = 0; i < NTILES; i++) {
			grid[i] = new Tile(0);
		}
	}

	/**
	 * Returns a random value, smaller than 16, indicating a location for a new
	 * Tile.
	 * 
	 * This new location is always valid, i.e. there is not already a tile
	 * there.
	 * 
	 * @return a new valid location.
	 */
	private int getRandomEmptyLocation() {
		int index = random.nextInt(grid.length);
		while (!grid[index].isEmpty()) {
			index = random.nextInt(grid.length);
		}
		return index;
	}

	/**
	 * Returns a random value, which is either 2 or 4. The chances of getting 4
	 * is significantly lower than the change of getting 2.
	 * 
	 * @return a random value, either 2 or 4.
	 */
	private int initialValue() {
		return random.nextInt(10) < 9 ? TWO : FOUR;
	}

	/**
	 * Set a tile's parameters by index.
	 * 
	 * @param index
	 *            the tile's index on the grid
	 * @param value
	 *            the tile's value (should be a multiple of 2)
	 * @param isMerged
	 *            true if the tile is merged
	 */
	public void setTile(int index, int value, boolean isMerged) {
		grid[index].setValue(value);
		grid[index].setMerged(isMerged);
	}

	/**
	 * Update the grid.
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		for (Tile t : grid) {
			t.update(delta);
		}
		updateHighest();
	}

	/**
	 * Reset the grid.
	 */
	public void restart() {
		for (Tile t : grid) {
			t.reset();
		}
		initGrid();
		updateHighest();
	}

	/**
	 * This method is the one method used for moving tiles.
	 * 
	 * Its parameter shall indicate which direction is to be moved in. The
	 * method will walk over all Tiles, checking if a move is possible in the
	 * desired direction. If a valid move is possible, it will update the grid
	 * array.
	 * 
	 * @param direction
	 *            the direction in which is to be moved.
	 */
	public void move(Direction direction) {

		switch (direction) {
		case LEFT:
			mover.moveLeft();
			break;
		case RIGHT:
			mover.moveRight();
			break;
		case UP:
			mover.moveUp();
			break;
		case DOWN:
			mover.moveDown();
			break;
		default:
			break;
		}

		if (mover.isMoveMade()) {
			world.addScore(mover.getScoreIncrement());
			grid[getRandomEmptyLocation()].setValue(initialValue());
		}

		mover.reset();
	}

	/**
	 * Returns true if the grid is full.
	 * 
	 * @return true if the grid is full.
	 */
	public boolean isFull() {
		for (int i = 0; i < grid.length; i++) {
			if (grid[i].getValue() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the amount of possible moves on the grid.
	 * 
	 * @return moves the amount of possible moves
	 */
	public int getPossibleMoves() {
		int moves = 0;

		for (int index = 0; index < grid.length; index++) {

			// an empty tile cannot move
			if (!grid[index].isEmpty()) {

				// get current tile value
				int value = grid[index].getValue();

				// get all Tile's neighbours
				List<Tile> neighbours = getTileNeighbours(index);

				for (Tile neighbour : neighbours) {

					// compare tile values
					if (neighbour.getValue() == value
							|| neighbour.getValue() == 0)
						moves++;
				}
			}
		}

		return moves;
	}

	/**
	 * Get a list of neighbouring Tiles by index.
	 * 
	 * @param index
	 *            the tile index
	 * @return list of tiles
	 */
	public List<Tile> getTileNeighbours(int index) {
		List<Tile> neighbours = new ArrayList<Tile>();

		// right neighbour:
		// check if the index we're checking is not the right edge of the grid
		// by making sure index + 1 is a not a multiple of 4
		if ((index + 1) % 4 != 0 && index + 1 < grid.length)
			neighbours.add(grid[index + 1]);

		// left neighbour:
		// check if the index we're checking is not the left edge of the grid
		// by making sure index is a not a multiple of 4
		if (index % 4 != 0 && index - 1 >= 0)
			neighbours.add(grid[index - 1]);

		// upper neighbour:
		if (index + 4 < grid.length)
			neighbours.add(grid[index + 4]);

		// lower neighbour:
		if (index - 4 >= 0)
			neighbours.add(grid[index - 4]);

		return neighbours;
	}

	/**
	 * Returns the array containing all the tiles.
	 * 
	 * @return the array containing all the tiles.
	 */
	public Tile[] getTiles() {
		return grid;
	}

	public void updateHighest() {
		highest = 0;
		for (Tile t : grid) {
			if (t.getValue() > highest)
				highest = t.getValue();
		}
	}

	public int getHighest() {
		return highest;
	}
	
	public TileMover getTileMover() {
		return mover;
	}
}
