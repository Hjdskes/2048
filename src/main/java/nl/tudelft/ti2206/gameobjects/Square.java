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
		value = 0;	
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
	
	public boolean setX() {
		return false;
		
	}
	
	public boolean setY() {
		return false;	
	}

	/**
	 * Returns the x coordinate of the Square
	 * 
	 * @return the x coordinate
	 */
	public float getX() {
		switch (index) {
		case 0:
		case 4:
		case 8:
		case 12:
			return BASE_X;

		case 1:
		case 5:
		case 9:
		case 13:
			return BASE_X + WIDTH + GAP;

		case 2:
		case 6:
		case 10:
		case 14:
			return BASE_X + 2 * (WIDTH + GAP);

		case 3:
		case 7:
		case 11:
		case 15:
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
		switch (index) {
		case 0:
		case 1:
		case 2:
		case 3:
			return BASE_Y;

		case 4:
		case 5:
		case 6:
		case 7:
			return BASE_Y + HEIGHT + GAP;

		case 8:
		case 9:
		case 10:
		case 11:
			return BASE_Y + 2 * (HEIGHT + GAP);

		case 12:
		case 13:
		case 14:
		case 15:
			return BASE_Y + 3 * (HEIGHT + GAP);
		default:
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
	
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Doubles the value of the Square.
	 */
	public void doubleValue() {
		value *= 2;
	}
}
