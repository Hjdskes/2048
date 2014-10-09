package nl.tudelft.ti2206.gameobjects;

import java.util.Observable;

/**
 * The Tile class represents the tiles you move around while playing 2048.
 */
public class Tile extends Observable {
	/** The power of two that makes the value (e.g. 2^1, 2^2, 2^3, 2^4, ...). */
	private int value;

	/** The index into the Grid array. */
	private int index;

	/** Indicates whether this Tile has been merged in the current move. */
	private boolean isMerged;

	//private boolean spawning, moving, merging;

	/**
	 * Creates a new Tile with the given value.
	 * 
	 * @param index
	 *            The index into the Grid array.
	 * @param value
	 *            The value of the Tile.
	 */
	public Tile(int index, int value) {
		this.index = index;

		setValue(value);
		setMerged(false);
	}

	/**
	 * @return The value of the Tile.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the Tile and updates its sprite.
	 * 
	 * @param value
	 *            The new value.
	 */
	public void setValue(int value) {
		this.value = value;
		changed();
	}

	/**
	 * @return The index of the Tile into the Grid array.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * @return True if the tile is empty (value 0), false otherwise.
	 */
	public boolean isEmpty() {
		return this.value == 0;
	}

	/**
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
	 * Resets the Tile to its default state.
	 */
	public void reset() {
		setValue(0);
		setMerged(false);
		//spawning = moving = merging = false;
	}

	/**
	 * Doubles the value of the Tile.
	 */
	public void doubleValue() {
		setValue(++value);
	}

//	public void merge() {
//		merging = true;
//		changed();
//	}
	
//	public void move() {
//		move = true;
//		changed();
//	}

//	public void spawn() {
//		spawning = true;
//		changed();
//	}
	
//	public boolean shouldMerge() {
//		return merging;
//	}
	
//	public boolean shouldMove() {
//		return move;
//	}
	
//	public boolean shouldSpawn() {
//		return spawning;
//	}

	private void changed() {
		if (!hasChanged()) {
			setChanged();
			notifyObservers();
		}
	}
}
