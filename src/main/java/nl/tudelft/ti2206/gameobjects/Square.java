package nl.tudelft.ti2206.gameobjects;

/**
 * The Square class represents the squares
 * you move around while playing 2048.
 * 
 * Its instance variables contain the current
 * value and its index into the Grid array.
 * 
 * Checking for possible movements can be
 * done simply by checking if the value of
 * the squares at index+1, index-1, index+4
 * or index-4 are 0 (since empty squares
 * have a value of 0).
 * 
 * @author group-21
 *
 */
public class Square {
	/** The value (e.g. 2,4,8,16...).*/
	private int value;
	/** The index into the Grid array. */
	private int index;

	/**
	 * Creates a new Square with a given value.
	 * 
	 * @param value
	 *            the value of the Square
	 * @param index
	 *            the index into the Grid array
	 */
	public Square(int value, int index) {
		this.value = value;
		this.index = index;
	}

	/**
	 * Returns the value of the square.
	 * 
	 * @return the value of the square
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the index of the square.
	 * 
	 * @return the index of the square
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the new index for the square.
	 *
	 * @param index
	 * 			  the new index of the square
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Doubles the value of the square.
	 */
	public void doubleValue() {
		value *= 2;
	}
}
