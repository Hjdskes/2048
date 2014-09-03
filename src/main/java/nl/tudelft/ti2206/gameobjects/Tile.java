package nl.tudelft.ti2206.gameobjects;

/**
 * The Tile class represents the Tiles you move around while playing 2048.
 * 
 * Its instance variables contain the current value and its index into the Grid
 * array.
 * 
 * Checking for possible movements can be done simply by checking if the value
 * of the Tiles at index+1, index-1, index+4 or index-4 are 0 (since empty
 * Tiles have a value of 0).
 * 
 * @author group-21
 *
 */
public class Tile {
	/** The value (e.g. 2,4,8,16... */
	private int value;

	/**
	 * Creates a new Tile with a given value.
	 * 
	 * @param value
	 *            the value of the Tile
	 */
	public Tile(final int value) {
		this.value = value;
	}

	/**
	 * Returns the value of the Tile.
	 * 
	 * @return the value of the Tile
	 */
	public final int getValue() {
		return value;
	}

	/**
	 * Resets the value of the Tile.
	 */
	public final void resetValue() {
		this.value = 0;
	}

	/**
	 * Doubles the value of the Tile.
	 */
	public final void doubleValue() {
		value *= 2;
	}
}
