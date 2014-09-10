package nl.tudelft.ti2206.gameobjects;

/**
 * The Tile class represents the Tiles you move around while playing 2048.
 * 
 * Its instance variables contain the current value and its index into the Grid
 * array.
 * 
 * Checking for possible movements can be done simply by checking if the value
 * of the Tiles at index+1, index-1, index+4 or index-4 are 0 (since empty Tiles
 * have a value of 0).
 * 
 * @author group-21
 *
 */
public class Tile {
	/** The value (e.g. 2,4,8,16... */
	private int value;
	/** Indicates whether or not this Tile
        has merged into another. */
	private boolean isMerged;

	/**
	 * Creates a new Tile with a given value.
	 * 
	 * @param value
	 *            the value of the Tile
	 */
	public Tile(int value) {
		this.value = value;
		this.isMerged = false;
	}

	/**
	 * Returns the value of the Tile.
	 * 
	 * @return the value of the Tile
	 */
	public final int getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the Tile.
	 * 
	 * @param value
	 *            the new value.
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * Check whether the tile is empty (value 0).
	 * 
	 * @return true if tile is empty (value 0)
	 */
	public boolean isEmpty() {
		return this.value == 0;
	}

	/**
	 * Returns true if this Tile has been
	 * merged.
	 * @return true if this Tile has been
	 *         merged.
	 */
	public boolean isMerged() {
		return this.isMerged;
	}

	/**
	 * Sets the merged state of this Tile.
	 * 
	 * @param isMerged the new merged state.
	 */
	public void setMerged(boolean isMerged) {
		this.isMerged = isMerged;
	}

	/**
	 * Resets the value of the Tile.
	 */
	public final void resetValue() {
		this.value = 0;
	}

	/**
	 * Doubles the value of the Tile,
	 * or sets it to 2 if the current Tile
	 * is empty.
	 */
	public final void doubleValue() {
		if (this.isEmpty()) {
			value = 2;
		} else {
			value *= 2;
		}
	}

	/**
	 * Resets the Tile to its default state.
	 */
	public void reset() {
		resetValue();
		isMerged = false;
	}

	public void update(float delta) {}
}
