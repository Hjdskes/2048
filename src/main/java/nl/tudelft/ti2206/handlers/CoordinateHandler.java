package nl.tudelft.ti2206.handlers;

/**
 * The CoordinateHandler is used to calculate coordinates for several sprites.
 * 
 * @author group-21
 */
public class CoordinateHandler {
	/** The width of the Tile. */
	private static final int TILE_WIDTH = 81;
	/** The height of the Tile. */
	private static final int TILE_HEIGHT = 81;
	/** The base Tile x-coordinate. */
	private static final int TILE_X = 115;
	/** The base Tile y-coordinate. */
	private static final int TILE_Y = 115;
	/** The gap in between tiles, Grid edges, etc. */
	private static final int GAP = 15;
	/** The width of the Grid. */
	private static final int GRID_WIDTH = 400;
	/** The height of the Grid. */
	private static final int GRID_HEIGHT = 400;
	/** The base Grid x-coordinate. */
	private static final int GRID_X = 100;
	/** The base Grid y-coordinate. */
	private static final int GRID_Y = 100;

	/**
	 * Returns the x-coordinate of a Tile.
	 * 
	 * @return The x-coordinate of the Tile.
	 */
	public static float getTileX(int index) {
		switch (index % 4) {
		case 1:
			return TILE_X + TILE_WIDTH + GAP;
		case 2:
			return TILE_X + 2 * (TILE_WIDTH + GAP);
		case 3:
			return TILE_X + 3 * (TILE_WIDTH + GAP);
		case 0:
		default:
			return TILE_X;
		}
	}

	/**
	 * Returns the y-coordinate of a Tile.
	 * 
	 * @return The y-coordinate of the Tile.
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
	 * Returns the center x-coordinate of a Tile.
	 * 
	 * @return The center x-coordinate of the Tile.
	 */
	public static float getCenterTileX(int index) {
		return getTileX(index) + TILE_WIDTH / 2;
	}

	/**
	 * Returns the center y-coordinate of a Tile.
	 * 
	 * @return The center y-coordinate of the Tile.
	 */
	public static float getCenterTileY(int index) {
		return getTileY(index) + TILE_HEIGHT / 2;
	}

	/**
	 * Returns the x-coordinate of the Grid.
	 * 
	 * @return The x-coordinate of the Grid.
	 */
	public static int getGridX() {
		return GRID_X;
	}

	/**
	 * Returns the y-coordinate of the Grid.
	 * 
	 * @return The y-coordinate of the Grid.
	 */
	public static int getGridY() {
		return GRID_Y;
	}

	/**
	 * Returns the width of a Tile in pixels.
	 * 
	 * @return the width of a Tile in pixels
	 */
	public static int getTileWidth() {
		return TILE_WIDTH;
	}

	/**
	 * Returns the height of a Tile in pixels.
	 * 
	 * @return The height of a Tile in pixels.
	 */
	public static int getTileHeight() {
		return TILE_HEIGHT;
	}

	/**
	 * Returns the width of the Grid in pixels.
	 * 
	 * @return The width of the Grid in pixels.
	 */
	public static int getGridWidth() {
		return GRID_WIDTH;
	}

	/**
	 * Returns the height of the Grid in pixels.
	 * 
	 * @return The height of the Grid in pixels.
	 */
	public static int getGridHeight() {
		return GRID_HEIGHT;
	}
}