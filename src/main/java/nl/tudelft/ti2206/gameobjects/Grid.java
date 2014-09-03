package nl.tudelft.ti2206.gameobjects;

import java.util.Random;

/**
 * This class represents the 4x4 grid you see when playing 2048.
 *
 * The internal structure is a simple two-dimensional array. Considering we only
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
 *
 */
public class Grid {

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
	private Tile[] grid;
	/** Randomizer needed for filling tiles. */
	private Random random;

	/**
	 * Creates a new Grid with NTILES Tile objects.
	 */
	public Grid() {
		this.random = new Random();
		this.grid = new Tile[NTILES];
		initGrid();
	}

	/**
	 * Initializes the grid, creating two tiles with a value of 2 or 4 and
	 * setting the rest empty.
	 */
	private void initGrid() {
		int loc1 = initialLocation();
		int loc2 = initialLocation();
		while (loc2 == loc1) 
			loc2 = initialLocation();

		for (int i = 0; i < NTILES; i++) {
			if (i == loc1) {
				grid[i] = new Tile(initialValue());
			} else if (i == loc2) {
				grid[i] = new Tile(initialValue());
			} else {
				grid[i] = new Tile(0);
			}
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
	private int initialLocation() {
		return random.nextInt(NTILES);
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

	public void update(float delta) {

	}

	public void restart() {
		for (Tile t : grid) {
			t.restart();
		}
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
	 * 
	 * @return true if a move has been made.
	 */
	public boolean move(Direction direction) {
		/* TODO: add a block after a valid move */
		boolean res = false;

		switch (direction) {
		case LEFT:
			res = moveLeft();
			break;
		case RIGHT:
			res = moveRight();
			break;
		case UP:
			res = moveUp();
			break;
		case DOWN:
			res = moveDown();
			break;
		default:
			break;
		}

		if (res) {
			grid[getRandomEmptyLocation()].setValue(initialValue());
		}
		return res;
	}

	private int getRandomEmptyLocation() {
		int index = random.nextInt(grid.length);
		while (!grid[index].isEmpty()) {
			index = random.nextInt(grid.length);
		}
		return index;
	}

	/**
	 * Performs a move to the left.
	 * 
	 * @return true if a move has been made.
	 */
	public boolean moveLeft() {
		boolean res = false;

		for (int i = 0; i < NTILES; i++) {
			/* Tile is in the leftmost row. */
			if (i % FOUR == 1) {
				continue;
			}
			if (grid[i].getValue() == grid[i - 1].getValue()) {
				grid[i - 1].doubleValue();
				grid[i].resetValue();
				if (!res) {
					res = true;
				}
			}
		}
		return res;
	}

	/**
	 * Performs a move to the right.
	 * 
	 * @return true if a move has been made.
	 */
	public boolean moveRight() {
		boolean res = false;

		/*
		 * To have the tiles merge correctly, we need to revert the order in
		 * which we walk through them.
		 */
		for (int i = NTILES; i > 0; i--) {
			/* Tile is in the rightmost row. */
			if (i % FOUR == FOUR - 1) {
				continue;
			}
			if (grid[i].getValue() == grid[i - 1].getValue()) {
				grid[i].doubleValue();
				grid[i - 1].resetValue();
				if (!res) {
					res = true;
				}
			}
		}
		return res;
	}

	/**
	 * Performs a move upwards.
	 * 
	 * @return true if a move has been made.
	 */
	public boolean moveUp() {
		boolean res = false;

		/* Skip the first four tiles. */
		for (int i = FOUR; i < NTILES; i++) {
			if (grid[i].getValue() == grid[i - FOUR].getValue()) {
				grid[i - FOUR].doubleValue();
				grid[i].resetValue();
				if (!res) {
					res = true;
				}
			}
		}
		return res;
	}

	/**
	 * Performs a move downwards.
	 * 
	 * @return true if a move has been made.
	 */
	public boolean moveDown() {
		boolean res = false;

		/* Skip the last four tiles. */
		for (int i = 0; i < NTILES - FOUR; i++) {
			if (grid[i].getValue() == grid[i + 1].getValue()) {
				grid[i + FOUR].doubleValue();
				grid[i].resetValue();
				if (!res) {
					res = true;
				}
			}
		}
		return res;
	}

	/**
	 * Returns true if the grid is full.
	 * 
	 * @return true if the grid is full.
	 */
	public boolean isFull() {
		for (int i = 0; i < NTILES; i++) {
			if (grid[i].getValue() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the array containing all the tiles.
	 * 
	 * @return the array containing all the tiles.
	 */
	public Tile[] getTiles() {
		return grid;
	}
}
