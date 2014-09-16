package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The Tile class represents the tiles you move around while playing 2048.
 * 
 * @author group-21
 */
public class Tile extends Actor {
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
	/** The value (e.g. 2, 4, 8, 16, ...). */
	private int value;
	/** The index into the Grid array. */
	private int index;
	/**
	 * Defines a rectangular area of a texture, kind of like a viewport, on the
	 * whole image.
	 */
	private TextureRegion region;

	/**
	 * Creates a new Tile with the given value.
	 * 
	 * @param index
	 *            The index into the Grid array.
	 * @param value
	 *            The value of the Tile.
	 */
	public Tile(int index, int value) {
		this.value = value;
		this.index = index;

		region = new TextureRegion();
		setSprite();
	}

	/**
	 * Returns the value of the Tile.
	 * 
	 * @return The value of the Tile.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the Tile.
	 * 
	 * @param value
	 *            The new value.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Returns the index of the Tile into the Grid array.
	 * 
	 * @return The index of the Tile.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Sets the index of the Tile into the Grid array.
	 * 
	 * @param index
	 *            The new index.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Checks whether the tile is empty (value 0).
	 * 
	 * @return True if the tile is empty (value 0), false otherwise.
	 */
	public boolean isEmpty() {
		return this.value == 0;
	}

	/**
	 * Resets the value of the Tile.
	 */
	public void resetValue() {
		this.value = 0;
	}

	/**
	 * Doubles the value of the Tile, or sets it to 2 if the current value is 0.
	 */
	public void doubleValue() {
		if (this.isEmpty()) {
			value = 2;
		} else {
			value *= 2;
		}
	}

	/**
	 * Moves the TextureRegion to the new Texture, belonging to the current
	 * value of the Tile.
	 */
	public void setSprite() {
		region.setRegion(AssetHandler.getSkin().getRegion("tile" + this.value));
	}

	@Override
	public float getX() {
		switch (this.index % 4) {
		case 1:
			return TILE_X + TILE_WIDTH + GAP;
		case 2:
			return TILE_X + 2 * (TILE_WIDTH + GAP);
		case 3:
			return TILE_X + 3 * (TILE_WIDTH + GAP);
		case 0: /* Fallthrough */
		default:
			return TILE_X;
		}
	}

	@Override
	public float getY() {
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
	public float getWidth() {
		return TILE_WIDTH;
	}

	@Override
	public float getHeight() {
		return TILE_HEIGHT;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		// drawTileValue(CoordinateHandler.getCenterTileX(i),
		// CoordinateHandler.getCenterTileY(i),
		// grid.getTiles()[i].getValue());
	}
}
