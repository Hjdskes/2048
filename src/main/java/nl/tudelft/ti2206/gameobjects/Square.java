package nl.tudelft.ti2206.gameobjects;

public abstract class Square {

	private int value;

	/**
	 * Creates a new Square with a given value
	 * 
	 * @param value
	 *            the value of the Square
	 */
	public Square(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Doubles the value of the square
	 */
	public void doubleValue() {
		value *= 2;
	}
}
