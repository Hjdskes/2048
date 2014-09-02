package nl.tudelft.ti2206.gameobjects;


/**
 * This class represents the 4x4 grid you see when playing 2048.
 *
 * The internal structure is a simple one-dimensional array.
 * Considering we only require simple operations, this is deemed
 * fast enough, while being very simple at the same time.
 *
 * For example, imagine the grid being laid out like this:
 *
 *   +---+---+---+---+
 *   | 1 | 2 | 3 | 4 |
 *   +---+---+---+---+
 *   | 5 | 6 | 7 | 8 |
 *   +---+---+---+---+
 *   | 9 | 10| 11| 12|
 *   +---+---+---+---+
 *   | 13| 14| 15| 16|
 *   +---+---+---+---+
 *
 * Now, a square on field 11 can move left or right by adding
 * or subtracting 1 from its index. Consequently, it can move
 * up or down by adding or subtracting 4 from its index.
 *
 * @author group-21
 *
 */
public class Grid {

	/** The width of the grid. */
	public static final int WIDTH = 400;
	/** The height of the grid. */
	public static final int HEIGHT = 400;

	/** Base x coordinate. */
	private static final int BASE_X = 100;
	/** Base y coordinate. */
	private static final int BASE_Y = 100;

	/** The grid contains sixteen squares. */
	private static final int NSQUARES = 16;
	/** The array containing all sixteen squares. */
	private Square[] grid;

	/**
	 * Creates a new Grid with NSQUARES Square objects.
	 */
	public Grid() {
		grid = new Square[NSQUARES];
		for (int i = 0; i < grid.length; i++) {
			grid[i] = new Square(0, i);
		}
	}

	/**
	 * Updates the grid at the rate of delta/1000 times per second.
	 *
	 * @param delta
	 */
	public void update(float delta) {
		for (Square s : grid) {
			s.update(delta);
		}
	}

	/**
	 * Restarts the grid.
	 */
	public void onRestart() {
		for (Square s : grid) {
			s.onRestart();
		}
	}

	/**
	 *
	 * @return x coordinate
	 */
	public int getX() {
		return BASE_X;
	}

	/**
	 *
	 * @return y coordinate
	 */
	public int getY() {
		return BASE_Y;
	}

	/**
	 *
	 * @return the grid containing all the squares
	 */
	public Square[] getSquares() {
		return grid;
	}

	/**
	 * Lets a Square occupy a spot on the grid, depending on its index.
	 * @param index
	 * @param occupant
	 */
	public void occupySquare(int index, Square occupant) {
		grid[index] = occupant;
	}
}
