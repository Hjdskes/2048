package old.gameobjects;

/**
 * The AnimatedGrid class is a subclass of the Grid class to provide animations.
 * 
 * @author group-21
 */
public class AnimatedTile extends Tile {
	/** The dimensions of the Tile. */
	private static final int DIMENSION = 81;
	/** The current size of the Tile. */
	private float size;
	/** True if this Tile is spawning. */
	private boolean isSpawning;
	/** True if this Tile is merging. */
	private boolean isMerging;

	/**
	 * Creates a new AnimatedTile with the given value.
	 * 
	 * @param value
	 *            The value of the Tile.
	 */
	public AnimatedTile(int value) {
		super(value);
		size = 1;
		isSpawning = false;
		isMerging = false;
	}

	/**
	 * Updates the Tile. If it is spawning its size increases each frame. If it
	 * is merging, its size reduces each frame.
	 */
	public void update() {
		if (isSpawning) {
			if (size < 1) {
				size += .04;
			} else {
				isSpawning = false;
			}
		} else if (isMerging) {
			if (size > 1) {
				size -= .012;
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
		size = 1;
		isSpawning = false;
		isMerging = false;
	}

	@Override
	public void setMerged(boolean isMerged) {
		super.setMerged(isMerged);
		if (isMerged) {
			merge();
		}
	}

	/**
	 * Returns the current size of the Tile.
	 * 
	 * @return The dimensions multiplied by the Tile's scale.
	 */
	public float getTileSize() {
		return size * DIMENSION;
	}

	/**
	 * Returns the current scale of the Tile.
	 * 
	 * @return The scale of the Tile.
	 */
	public float getScale() {
		return size / 1;
	}

	/**
	 * Calculates the offset that should be taken into account when determining
	 * the x- and y-coordinates for drawing.
	 * 
	 * @return The offset to be taken into account.
	 */
	public float getXYOffset() {
		if (isSpawning) {
			return (1 - size) * DIMENSION / 2;
		} else if (isMerging) {
			return (DIMENSION - size * DIMENSION) / 2;
		} else {
			return 0;
		}
	}

	/**
	 * Starts a new spawning animation by setting isSpawning to true and setting
	 * the size to 0.5.
	 */
	public void spawn() {
		isSpawning = true;
		size = .5f;
	}

	/**
	 * Starts a new merging animation by setting isMerging to true and setting
	 * the size to 1.2.
	 */
	public void merge() {
		isMerging = true;
		size = 1.2f;
	}
}
