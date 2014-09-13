package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.helpers.TileMover;
import nl.tudelft.ti2206.helpers.XYCalculator;

public class AnimatedGrid extends Grid {

	/** TileMover is used to move the tiles. */
	private TileMover mover;

	private MovingTile[] movingTiles;

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
		this.mover = new TileMover(this);
		this.movingTiles = new MovingTile[grid.length];
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
	 * Updates the grid, by updating all the Tiles it contains and checking for
	 * a new highest value.
	 */
	public void update() {
		for (AnimatedTile t : grid) {
			t.update();
		}
		for (MovingTile t : movingTiles) {
			if (t != null)
				t.update();
		}

		updateHighestTile();
	}

	/**
	 * This method is the one method used for moving tiles.
	 * 
	 * Its parameter shall indicate which direction is to be moved in. The
	 * method will walk over all Tiles, checking if a move is possible in the
	 * desired direction. If a valid move is possible, it will update the grid
	 * array.
	 * 
	 * @param direction
	 *            The direction in which is to be moved.
	 */
	public void move(Direction direction) {
		/*
		 * If the game is not in running or continuing state, ignore the moves.
		 */
		if (world.isLost() || world.isWon()) {
			return;
		}

		switch (direction) {
		case LEFT:
			mover.moveLeft();
			break;
		case RIGHT:
			mover.moveRight();
			break;
		case UP:
			mover.moveUp();
			break;
		case DOWN:
			mover.moveDown();
			break;
		default:
			break;
		}

		if (mover.isMoveMade()) {
			world.addScore(mover.getScoreIncrement());
			setTile(getRandomEmptyLocation(), initialValue(), false);
		}

		mover.reset();
	}

	public void animateMovement(int index, int offset) {
		boolean isLeftRight = Math.abs(offset) == 1 ? true : false;
		movingTiles[index] = new MovingTile(grid[index]);
		if (isLeftRight) {
			movingTiles[index].move(XYCalculator.getTileX(index),
					XYCalculator.getTileX(index + offset), isLeftRight);
		} else {
			movingTiles[index].move(XYCalculator.getTileY(index),
					XYCalculator.getTileY(index + offset), isLeftRight);
		}
		
		if (!movingTiles[index].isVisible()) {
			movingTiles[index] = null;
		}
	}

	/**
	 * s
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @return The x coordinate where the Tile should be drawn.
	 */
	public float getTileX(int index) {
		return grid[index].getXYOffset() + XYCalculator.getTileX(index);
	}

	/**
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @return The y coordinate where the Tile should be drawn.
	 */
	public float getTileY(int index) {
		return XYCalculator.getTileY(index) + grid[index].getXYOffset();
	}

	/**
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @return The width of the Tile.
	 */
	public float getTileWidth(int index) {
		return XYCalculator.getTileWidth() * grid[index].getScale();
	}

	/**
	 *
	 * @param index
	 *            The index of the Tile.
	 * @return The height of the Tile.
	 */
	public float getTileHeight(int index) {
		return XYCalculator.getTileHeight() * grid[index].getScale();
	}

	/**
	 * Returns the TileMover object used by the grid.
	 * 
	 * @return The TileMover object.
	 */
	public TileMover getTileMover() {
		return mover;
	}

	public MovingTile[] getMovingTiles() {
		return movingTiles;
	}
	
	/**
	 * Sets the TileMover object used by the grid.
	 * 
	 * @param mover
	 *            The TileMover object to set.
	 */
	public void setTileMover(TileMover mover) {
		this.mover = mover;
	}
}
