package nl.tudelft.ti2206.gameobjects;

public class AnimatedTile extends Tile {

	/** The size of the tile */
	private static final int DIMENSION = 81;

	private float size;
	private boolean isSpawning;
	private boolean isMerging;

	/**
	 * Creates a new AnimatedTile with the specified parameters.
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
	 * is merging, reduce its size each frame.
	 */
	public void update() {
		if (isSpawning) {
			if (size < 1)
				size += .04;
			else
				isSpawning = false;
		} else if (isMerging) {
			if (size > 1)
				size -= .015;
			else
				isMerging = false;
		}
	}

	/**
	 * Resets the AnimatedTile.
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
		if (isMerged)
			merge();
	}

	/**
	 * 
	 * @return The dimension multiplied by the scale.
	 */
	public float getTileSize() {
		return size * DIMENSION;
	}

	/**
	 *
	 * @return The scale of the AnimatedTile.
	 */
	public float getScale() {
		return size / 1;
	}

	/**
	 * Calculates the offset that should be taken into account when determining
	 * the x and y coordinates for drawing.
	 * 
	 * @return The offset.
	 */
	public float getXYOffset() {
		if (isSpawning)
			return (1 - size) * DIMENSION / 2;
		else if (isMerging)
			return (DIMENSION - size * DIMENSION) / 2;
		else
			return 0;
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
