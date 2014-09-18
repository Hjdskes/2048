package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * The DrawableTile class is a subclass of Tile, that knows how to draw itself.
 * It also applies various animations when spawning and merging.
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

	/** The dimensions of the Tile. */
	private static final int DIMENSION = 81;

	/** The current scale of the Tile. */
	private float scale;

	/** True if this Tile is spawning. */
	private boolean isSpawning;

	/** True if this Tile is merging. */
	private boolean isMerging;

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
		this.scale = 1;
		this.isSpawning = false;
		this.isMerging = false;
	}

//	/**
//	 * @return The current size of the Tile.
//	 */
//	public float getSzie() {
//		return scale * DIMENSION;
//	}

	/**
	 * @return The current scale of the Tile.
	 */
	public float getScale() {
		return scale;
	}

	@Override
	public void setMerged(boolean isMerged) {
		super.setMerged(isMerged);
		if (isMerged) {
			merge();
		}
	}

	/**
	 * Updates the Tile. If it is spawning its size increases each frame. If it
	 * is merging, its size reduces each frame.
	 */
	public void update() {
		if (isSpawning) {
			if (scale < 1) {
				scale += .04;
			} else {
				isSpawning = false;
			}
		} else if (isMerging) {
			if (scale > 1) {
				scale -= .012;
			} else {
				isMerging = false;
			}
		}
	}

	/**
	 * Resets the AnimatedTile to its default state.
	 */
	@Override
	public void reset() {
		super.reset();
		this.scale = 1;
		this.isSpawning = false;
		this.isMerging = false;
	}

	/**
	 * Starts a new spawning animation by setting isSpawning to true and setting
	 * the size to 0.5.
	 */
	public void spawn() {
		this.isSpawning = true;
		this.scale = .5f;
	}

	/**
	 * Starts a new merging animation by setting isMerging to true and setting
	 * the size to 1.2.
	 */
	public void merge() {
		this.isMerging = true;
		this.scale = 1.2f;
	}

	/**
	 * @return The x-coordinate of this Tile.
	 */
	private float getX() {
		switch (this.index % 4) {
		case 1:
			return TILE_X + TILE_WIDTH + GAP + getOffset();
		case 2:
			return TILE_X + 2 * (TILE_WIDTH + GAP) + getOffset();
		case 3:
			return TILE_X + 3 * (TILE_WIDTH + GAP) + getOffset();
		case 0: /* Fallthrough. */
		default:
			return TILE_X + getOffset();
		}
	}

	/**
	 * @return The y-coordinate of this Tile.
	 */
	private float getY() {
		if (index < 4) {
			return TILE_Y + getOffset();
		} else if (index < 8) {
			return TILE_Y + TILE_HEIGHT + GAP + getOffset();
		} else if (index < 12) {
			return TILE_Y + 2 * (TILE_HEIGHT + GAP) + getOffset();
		} else if (index < 16) {
			return TILE_Y + 3 * (TILE_HEIGHT + GAP) + getOffset();
		} else {
			return TILE_Y + getOffset();
		}
	}

	/**
	 * @return The current width of the Tile.
	 */
	private float getWidth() {
		return TILE_WIDTH * scale;
	}

	/**
	 * @return The current height of the Tile.
	 */
	private float getHeight() {
		return TILE_HEIGHT * scale;
	}

	/**
	 * Calculates the offset that should be taken into account when determining
	 * the x- and y-coordinates for drawing.
	 * 
	 * @return The offset to be taken into account.
	 */
	public float getOffset() {
		if (isSpawning) {
			return (1 - scale) * DIMENSION / 2;
		} else if (isMerging) {
			return (DIMENSION - scale * DIMENSION) / 2;
		} else {
			return 0;
		}
	}

	@Override
	public void draw(Batch batch) {
		int value = super.getValue();

		batch.draw(AssetHandler.getTile(value), getX(), getY(), getWidth(), getHeight());
	}
}
