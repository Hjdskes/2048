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
	private Square[] grid;

	public Grid() {
		grid = new Square[16];
	}

	public Square[] getGrid() {
		return grid;
	}

	public void occupySquare(int index, Square occupant) {
		grid[index] = occupant;
	}
}
