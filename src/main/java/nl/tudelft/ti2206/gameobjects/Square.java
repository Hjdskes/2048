package nl.tudelft.ti2206.gameobjects;

/**
 * The Square class represents the Squares you move around while playing 2048.
 * 
 * Its instance variables contain the current value and its index into the Grid
 * array.
 * 
 * Checking for possible movements can be done simply by checking if the value
 * of the Squares at index+1, index-1, index+4 or index-4 are 0 (since empty
 * Squares have a value of 0).
 * 
 * @author group-21
 *
 */
public class Square {

	/** The height of the Square */
	public static final int WIDTH = 81;
	/** The width of the Square */
	public static final int HEIGHT = 81;

	/** Base x coordinate */
	private static final int BASE_X = 115;
	/** Base y coordinate */
	private static final int BASE_Y = 115;
	/** Gap between Squares */
	private static final int GAP = 15;

	/** The value (e.g. 2,4,8,16... */
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
	 * Updates the Square at the rate of 1000/delta times per second
	 * 
	 * @param delta
	 */
	public void update(float delta) {

	}

	/**
	 * Restarts the Square
	 */
	public void onRestart() {

	}

	/**
	 * Returns the value of the Square
	 * 
	 * @return the value of the Square
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the index of the Square.
	 * 
	 * @return the index of the Square
	 */
	public int getIndex() {
		return index;
	}
	
	public void setX(int x) {
		index = x;
	}

	public void setY(int y) {
		index = y;
	}

	/**
	 * Returns the x coordinate of the Square
	 * 
	 * @return the x coordinate
	 */
	public float getX() {
		switch (index % 4) {
		case 0:
			return BASE_X;
		case 1:
			return BASE_X + WIDTH + GAP;
		case 2:
			return BASE_X + 2 * (WIDTH + GAP);
		case 3:
			return BASE_X + 3 * (WIDTH + GAP);
		default:
			return BASE_X;
		}
	}

	/**
	 * 
	 * @return center x coordinate of the Square
	 */
	public float getCenterX() {
		return getX() + WIDTH / 2;
	}

	/**
	 * Returns the y coordinate of the Square
	 * 
	 * @return the y coordinate
	 */
	public float getY() {
		if (index < 4) {
			return BASE_Y;
		} else if (index < 8) {
			return BASE_Y + HEIGHT + GAP;
		} else if (index < 12) {
			return BASE_Y + 2 * (HEIGHT + GAP);
		} else if (index < 16) {
			return BASE_Y + 3 * (HEIGHT + GAP);
		} else {
			return BASE_Y;
		}
	}

	/**
	 * 
	 * @return center y coordinate of the Square
	 */
	public float getCenterY() {
		return getY() + HEIGHT / 2;
	}

	/**
	 * Sets the new index for the Square.
	 *
	 * @param index
	 *            the new index of the Square
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Doubles the value of the Square.
	 */
	public void doubleValue() {
		value *= 2;
	}
}
