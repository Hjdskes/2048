package nl.tudelft.ti2206.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.AssetHandler;

/**
 * The DrawableGrid class is a subclass of Grid, that knows how to draw itself.
 */
public class DrawableGrid extends Grid implements Drawable {
	/** The width of the Grid. */
	private static final int GRID_WIDTH = 400;

	/** The height of the Grid. */
	private static final int GRID_HEIGHT = 400;

	/** The base Grid x-coordinate. */
	private static final int GRID_X = 100;

	/** The base Grid y-coordinate. */
	private static final int GRID_Y = 100;

	/**
	 * Constructs a new DrawableGrid instance, by calling super with its
	 * supplied parameters.
	 * 
	 * @param world
	 *            The GameWorld this Grid will be placed in.
	 * @param isEmpty
	 *            True if the grid should be empty.
	 */
	public DrawableGrid(GameWorld world, boolean isEmpty) {
		super(world, isEmpty);
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(AssetHandler.grid, GRID_X, GRID_Y, GRID_WIDTH, GRID_HEIGHT);
	}
}
