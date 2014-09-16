package old.gameobjects;

/**
 * The Tile class represents the tiles you move around while playing 2048.
 * 
 * @author group-21
 */
public class Tile {
	/** The value (e.g. 2, 4, 8, 16, ...). */
	private int value;
	/**
	 * Indicates whether or not this Tile has merged into another in the current
	 * update.
	 */
	private boolean isMerged;

	/**
	 * Creates a new Tile with the given value.
	 * 
	 * @param value
	 *            The value of the Tile.
	 */
	public Tile(int value) {
		this.value = value;
		this.isMerged = false;
	}

	/**
	 * Returns the value of the Tile.
	 * 
	 * @return The value of the Tile.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the Tile.
	 * 
	 * @param value
	 *            The new value.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Checks whether the tile is empty (value 0).
	 * 
	 * @return True if the tile is empty (value 0), false otherwise.
	 */
	public boolean isEmpty() {
		return this.value == 0;
	}

	/**
	 * Returns true if this Tile has been merged.
	 * 
	 * @return True if this Tile has been merged, false otherwise.
	 */
	public boolean isMerged() {
		return this.isMerged;
	}

	/**
	 * Sets the merged state of this Tile.
	 * 
	 * @param isMerged
	 *            The new merged state.
	 */
	public void setMerged(boolean isMerged) {
		this.isMerged = isMerged;
	}

	/**
	 * Resets the value of the Tile.
	 */
	public void resetValue() {
		this.value = 0;
	}

	/**
	 * Doubles the value of the Tile, or sets it to 2 if the current value is 0.
	 */
	public void doubleValue() {
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
}
