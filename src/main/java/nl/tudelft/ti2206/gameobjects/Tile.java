package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

	private TextureRegion region;

	/**
	 * Creates a new Tile with the given value.
	 * 
	 * @param value
	 *            The value of the Tile.
	 */
	public Tile(int value, int index) {
		this.value = value;
		this.index = index;

		region = new TextureRegion(AssetHandler.getSkin().get("grid", Texture.class));
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

	/**
	 * Returns the correct color for the Tile, based on its value.
	 * 
	 * @return The color for the Tile.
	 */
	@Override
	public Color getColor() {
		switch (this.value) {
		case 0:
			return new Color();
		case 2:
			return new Color(237f, 227f, 217f, 0f);
		case 4:
			return new Color(236f, 223f, 199f, 0f);
		case 8:
			return new Color(241f, 176f, 120f, 0f);
		case 16:
			return new Color(244f, 148f, 98f, 0f);
		case 32:
			return new Color(245f, 123f, 94f, 0f);
		case 64:
			return new Color(245f, 93f, 58f, 0f);
		case 128:
			return new Color(236f, 206f, 113f, 0f);
		case 256:
			return new Color(236f, 203f, 96f, 0f);
		case 512:
			return new Color(236f, 199f, 79f, 0f);
		case 1024:
			return new Color(236f, 196f, 62f, 0f);
		case 2048: /* Fallthrough */
		case 4096: /* Fallthrough */
		case 16384: /* Fallthrough */
			return new Color(236f, 193f, 45f, 0f);
		default:
			return new Color();
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		//Color color = getColor();
		//batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		// drawTileValue(CoordinateHandler.getCenterTileX(i),
		// CoordinateHandler.getCenterTileY(i),
		// grid.getTiles()[i].getValue());
	}
}
