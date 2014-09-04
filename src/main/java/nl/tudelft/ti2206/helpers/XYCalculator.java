package nl.tudelft.ti2206.helpers;

public class XYCalculator {

	/** The TILE_HEIGHT of the Tile */
	private static final int TILE_WIDTH = 81;
	/** The width of the Tile */
	private static final int TILE_HEIGHT = 81;

	/** Base x coordinate */
	private static final int TILE_X = 115;
	/** Base y coordinate */
	private static final int TILE_Y = 115;
	/** Gap between Tiles, Grid edges, etc */
	private static final int GAP = 15;
	/** The width of the grid. */
	private static final int GRID_WIDTH = 400;
	/** The height of the grid. */
	private static final int GRID_HEIGHT = 400;
	/** Base x coordinate. */
	private static final int GRID_X = 100;
	/** Base y coordinate. */
	private static final int GRID_Y = 100;

	/**
	 * Returns the x coordinate of the Tile
	 * 
	 * @return the x coordinate
	 */
	public static float getTileX(int index) {
		switch (index % 4) {
		case 0:
			return TILE_X;
		case 1:
			return TILE_X + TILE_WIDTH + GAP;
		case 2:
			return TILE_X + 2 * (TILE_WIDTH + GAP);
		case 3:
			return TILE_X + 3 * (TILE_WIDTH + GAP);
		default:
			return TILE_X;
		}
	}

	/**
	 * 
	 * @return center x coordinate of the Tile
	 */
	public static float getCenterTileX(int index) {
		return getTileX(index) + TILE_WIDTH / 2;
	}

	/**
	 * Returns the y coordinate of the Tile
	 * 
	 * @return the y coordinate
	 */
	public static float getTileY(int index) {
		if (index < 4) {
			return TILE_Y;
		} else if (index < 8) {
			return TILE_Y + TILE_HEIGHT + GAP;
		} else if (index < 12) {
			return TILE_Y + 2 * (TILE_HEIGHT + GAP);
		} else if (index < 16) {
			return TILE_Y + 3 * (TILE_HEIGHT + GAP);
		} else {
			return TILE_Y;
		}
	}

	/**
	 * 
	 * @return center y coordinate of the Tile
	 */
	public static float getCenterTileY(int index) {
		return getTileY(index) + TILE_HEIGHT / 2;
	}

	/**
	 *
	 * @return x coordinate
	 */
	public static int getGridX() {
		return GRID_X;
	}

	/**
	 *
	 * @return y coordinate
	 */
	public static int getGridY() {
		return GRID_Y;
	}

	public static int getTileWidth() {
		return TILE_WIDTH;
	}

	public static int getTileHeight() {
		return TILE_HEIGHT;
	}

	public static int getGridWidth() {
		return GRID_WIDTH;
	}

	public static int getGridHeight() {
		return GRID_HEIGHT;
	}
}