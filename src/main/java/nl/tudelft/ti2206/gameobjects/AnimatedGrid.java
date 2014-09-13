package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.CoordinateHandler;

public class AnimatedGrid extends Grid {

	/**
	 * Creates a new AnimatedGrid with the specified parameters.
	 * 
	 * @param world
	 *            The GameWorld instance.
	 * @param isEmpty
	 *            True if the created grid should be empty, false otherwise.
	 */
	public AnimatedGrid(GameWorld world, boolean isEmpty) {
		super(world, isEmpty);
	}

	/**
	 * Sets the variables Tile at the specified index to the specified
	 * parameters, and starts the spawning animation.
	 */
	@Override
	public void setTile(int index, int value, boolean isMerged) {
		super.setTile(index, value, isMerged);
		grid[index].spawn();
	}

	/**
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @return The x coordinate where the Tile should be drawn.
	 */
	public float getTileX(int index) {
		return grid[index].getXYOffset() + CoordinateHandler.getTileX(index);
	}

	/**
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @return The y coordinate where the Tile should be drawn.
	 */
	public float getTileY(int index) {
		return CoordinateHandler.getTileY(index) + grid[index].getXYOffset();
	}

	/**
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @return The width of the Tile.
	 */
	public float getTileWidth(int index) {
		return CoordinateHandler.getTileWidth() * grid[index].getScale();
	}

	/**
	 *
	 * @param index
	 *            The index of the Tile.
	 * @return The height of the Tile.
	 */
	public float getTileHeight(int index) {
		return CoordinateHandler.getTileHeight() * grid[index].getScale();
	}

}
