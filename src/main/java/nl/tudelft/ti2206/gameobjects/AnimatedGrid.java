package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.CoordinateHandler;

/**
 * The AnimatedGrid class is a subclass of the Grid class to provide animations.
 * 
 * @author group-21
 */
public class AnimatedGrid extends Grid {

	/**
	 * Creates a new AnimatedGrid with the specified parameters.
	 * 
	 * @param world
	 *            The GameWorld this AnimatedGrid will be placed in.
	 * @param isEmpty
	 *            True if the grid should be empty.
	 */
	public AnimatedGrid(GameWorld world, boolean isEmpty) {
		super(world, isEmpty);
	}

	/**
	 * Sets an AnimatedTile's parameters by index and starts the spawning
	 * animation.
	 * 
	 * @param index
	 *            The AnimatedTile's index on the grid.
	 * @param value
	 *            The AnimatedTile's value (should be a multiple of 2 or 0).
	 * @param isMerged
	 *            True if the AnimatedTile is merged.
	 */
	@Override
	public void setTile(int index, int value, boolean isMerged) {
		super.setTile(index, value, isMerged);
		grid[index].spawn();
	}

	/**
	 * Returns the x-coordinate of the AnimatedTile to be drawn.
	 * 
	 * @param index
	 *            The index of the AnimatedTile.
	 * @return The x-coordinate where the AnimatedTile should be drawn.
	 */
	public float getTileX(int index) {
		return grid[index].getXYOffset() + CoordinateHandler.getTileX(index);
	}

	/**
	 * Returns the y-coordinate of the AnimatedTile to be drawn.
	 * 
	 * @param index
	 *            The index of the AnimatedTile.
	 * @return The y-coordinate where the AnimatedTile should be drawn.
	 */
	public float getTileY(int index) {
		return CoordinateHandler.getTileY(index) + grid[index].getXYOffset();
	}

	/**
	 * Returns the width of the AnimatedTile.
	 * 
	 * @param index
	 *            The index of the AnimatedTile.
	 * @return The width of the AnimatedTile.
	 */
	public float getTileWidth(int index) {
		return CoordinateHandler.getTileWidth() * grid[index].getScale();
	}

	/**
	 * Returns the height of the AnimatedTile.
	 * 
	 * @param index
	 *            The index of the AnimatedTile.
	 * @return The height of the AnimatedTile.
	 */
	public float getTileHeight(int index) {
		return CoordinateHandler.getTileHeight() * grid[index].getScale();
	}
}
