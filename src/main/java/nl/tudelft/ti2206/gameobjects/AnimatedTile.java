package nl.tudelft.ti2206.gameobjects;

public class AnimatedTile extends Tile {

	/** The size of the tile */
	private static final int DIMENSION = 81;

	private float size;
	private boolean isSpawning;

	/**
	 * Creates a new AnimatedTile with the specified parameters.
	 * 
	 * @param value
	 *            The value of the Tile.
	 * @param isSpawning
	 *            True if the tile shown show its spawning animation, false
	 *            otherwise.
	 */
	public AnimatedTile(int value, boolean isSpawning) {
		super(value);
		size = 1;
		this.isSpawning = false;
	}

	/**
	 * Updates the Tile, if it is spawning its size increases each frame.
	 */
	public void update() {
		if (isSpawning) {
			if (size < 1)
				size += .04;
			else
				isSpawning = false;
		}
	}

	/**
	 * Resets the AnimatedTile.
	 */
	@Override
	public void reset() {
		super.reset();
		size = 1;
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
	 * The offset that should be taken into account when determining the x and y
	 * coordinates for drawing.
	 * 
	 * @return The offset.
	 */
	public float getXYOffset() {
		if (isSpawning)
			return (1 - size) * DIMENSION / 2;
		else
			return 0;
	}

	/**
	 * Starts a new spawning animation by setting isSpawning to true and setting
	 * the size to s0.5.
	 */
	public void spawn() {
		isSpawning = true;
		size = .5f;
	}
}
