package nl.tudelft.ti2206.gameobjects;

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
	 */
	public Grid(GameWorld world) {
		this.random = new Random();
		this.grid = new Tile[NTILES];
		this.mover = new TileMover(this);
		this.world = world;
		initGrid();
	}

	/**
	 * Initializes the grid, creating two tiles with a value of 2 or 4 and
	 * setting the rest empty.
	 */
	private void initGrid() {
		for (int i = 0; i < NTILES; i++) {
			grid[i] = new Tile();
		}

		int loc1 = getRandomEmptyLocation();
		int loc2 = getRandomEmptyLocation();
		while (loc2 == loc1) {
			loc2 = getRandomEmptyLocation();
		}

		grid[loc1].setValue(initialValue());
		grid[loc2].setValue(initialValue());
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

	public void update(float delta) {
		for (Tile t : grid) {
			t.update(delta);
		}
		updateHighest();
	}

	public void restart() {
		for (Tile t : grid) {
			t.reset();
		}
		initGrid();
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
	 * Returns the array containing all the tiles.
	 * 
	 * @return the array containing all the tiles.
	 */
	public Tile[] getTiles() {
		return grid;
	}
	
	public void updateHighest() {
		for (Tile t : grid) {
			if (t.getValue() > highest)
				highest = t.getValue();
		}
	}
	
	public int getHighest() {
		return highest;
	}
}
