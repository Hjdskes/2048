package old.handlers;

/**
 * The CoordinateHandler is used to calculate coordinates for several sprites.
 * 
 * @author group-21
 */
public class CoordinateHandler {
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
}