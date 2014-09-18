package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * The DrawableTile class is a subclass of Tile, that knows how to draw itself.
 */
public class DrawableTile extends Tile implements Drawable {

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

	/** The index into the Grid array. */
	private int index;

	/**
	 * Constructs a new DrawableTile instance.
	 * 
	 * @param index
	 *            The index of the Tile into the Grid array.
	 * @param value
	 *            The value of the Tile.
	 */
	public DrawableTile(int index, int value) {
		super(value);
		this.index = index;
	}

	/**
	 * @return The x-coordinate of this Tile.
	 */
	private float getX() {
		switch (this.index % 4) {
		case 1:
			return TILE_X + TILE_WIDTH + GAP;
		case 2:
			return TILE_X + 2 * (TILE_WIDTH + GAP);
		case 3:
			return TILE_X + 3 * (TILE_WIDTH + GAP);
		case 0: /* Fallthrough. */
		default:
			return TILE_X;
		}
	}

	/**
	 * @return The y-coordinate of this Tile.
	 */
	private float getY() {
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

	@Override
	public void draw(Batch batch) {
		int value = super.getValue();

		batch.draw(AssetHandler.getTile(value), getX(), getY(), TILE_WIDTH, TILE_HEIGHT);
	}
}
