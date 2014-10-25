package nl.tudelft.ti2206.utils.ai.solvers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * This class attempts to solve the grid using a 'human-like' strategy: - Keep
 * the highest tile in one of the grid's corners (lower left in this case). -
 * Merge tiles of equal values with each other whenever possible.
 *
 * By default, this class looked ahead two moves. However, to increase accuracy
 * the last method (tryMoves) is now recursive: selectDirectionComplex +
 * tryMoves(depth = 0) = look 2 moves ahead selectDirectionComplex +
 * tryMoves(depth = 1) = look 3 moves ahead
 */
public class HumanSolver implements Solver {
	/** The Singleton reference to this class. */
	private static Solver instance = new HumanSolver();

	/** The index number of the lower left tile. */
	private static final int LOWER_LEFT = 12;

	/** Boolean variable for keeping if the last selected move was right. */
	private static boolean wasRightMove = false;

	/** Overrides the default constructor. */
	private HumanSolver() {
	}

	/** Returns the singleton reference to this class. */
	public static Solver getInstance() {
		return instance;
	}

	/**
	 * Check if lower left corner is empty.
	 * 
	 * @param grid
	 *            The grid to check.
	 * @return True if lower left corner is empty, false otherwise.
	 */
	public boolean emptyLowerLeft(Grid grid) {
		return grid.getTiles()[LOWER_LEFT].isEmpty();
	}

	/**
	 * Check if the highest tile is in the lower left corner.
	 * 
	 * @param grid
	 *            The grid to check.
	 * @return True if highest tile is in lower left corner, false otherwise.
	 */
	public boolean maxLowerLeft(Grid grid) {
		return grid.getTiles()[LOWER_LEFT].getValue() == grid
				.getCurrentHighestTile();
	}

	/**
	 * Check whether the lower row is full.
	 * 
	 * @param grid
	 *            The grid to check.
	 * @return True if the lower row is full, false otherwise.
	 */
	public boolean lowerRowFull(Grid grid) {
		Tile[] tiles = grid.getTiles();
		for (int index = 12; index < 16; index += 1)
			if (tiles[index].isEmpty())
				return false;
		return !isMergePossibleLowerRow(grid);
	}

	/**
	 * Check whether a merge is possible on the lower row of the grid.
	 * 
	 * @param grid
	 *            The grid to check.
	 * @return True if a merge is possible on the lower row, false otherwise.
	 */
	public boolean isMergePossibleLowerRow(Grid grid) {
		Tile[] tiles = grid.getTiles();
		for (int i = tiles.length - 4; i < tiles.length - 1; i++) {
			if (!tiles[i].isEmpty()
					&& tiles[i].getValue() == tiles[i + 1].getValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Recursive method to check moves to.
	 * 
	 * @param ogrid
	 *            The grid to make a move on.
	 * @param depth
	 *            The recursion depth to use.
	 * @return The points a move will make.
	 */
	public int nextMove(Grid ogrid, int depth) {
		int highest = 0;

		for (Direction direction : Grid.Direction.values()) {
			/* Never ever move up. */
			if (direction == Direction.UP) {
				continue;
			}

			/* We shouldn't move to right if the lower row is not full
			 * so ignore moving to the right. */
			if (direction == Direction.RIGHT && !lowerRowFull(ogrid)) {
				continue;
			}

			Grid cloned = ogrid.clone();
			cloned.move(direction);
			int score = cloned.getScore();

			if (depth > 0) {
				score += nextMove(cloned, depth - 1);
			}

			/* Get highest score possible. */
			highest = Math.max(highest, score);
		}
		return highest;
	}

	/**
	 * Selects a direction to move to based on which direction gives us the
	 * highest amount of points.
	 * 
	 * @param ogrid
	 *            The grid to make a move on.
	 * @param depth
	 *            The recursion depth to use.
	 * @return The direction which gives us the highest amount of points.
	 */
	public Direction selectDirectionComplex(Grid ogrid, int depth) {
		int score = ogrid.getScore();
		Direction selected = null;

		if (wasRightMove) {
			wasRightMove = false;
			if (!lowerRowFull(ogrid)) {
				return Direction.LEFT;
			}
		}

		if (!maxLowerLeft(ogrid) && emptyLowerLeft(ogrid)) {
			return Direction.LEFT;
		}

		for (Direction direction : Grid.Direction.values()) {
			Grid cloned = ogrid.clone();

			/* Never ever move up. */
			if (direction == Direction.UP) {
				continue;
			}

			/* We shouldn't move to right if the lower row is not full
			 * so ignore moving to the right. */
			if (direction == Direction.RIGHT && !lowerRowFull(ogrid)) {
				continue;
			}

			/* If move actually is possible. */
			if (cloned.move(direction) == true) {
				int pointsAfter = cloned.getScore()
						+ nextMove(cloned, depth - 1);

				if (pointsAfter > score) {
					score = pointsAfter;
					selected = direction;
				}
			}
		}

		if (selected == Direction.RIGHT) {
			wasRightMove = true;
		}

		return selected;
	}

	/**
	 * Fallback method for when other movement selections fail. This will select
	 * a move in the order: LEFT, DOWN, RIGHT. If none of these are possible:
	 * move UP.
	 * 
	 * @param grid
	 *            The grid to perform a movement on.
	 * @return direction The direction to perform move into.
	 */
	public Direction selectDirectionSimple(Grid grid) {
		Direction direction = Direction.LEFT;

		if (grid.clone().move(Direction.LEFT)) {
			direction = Direction.LEFT;
		} else if (grid.clone().move(Direction.DOWN)) {
			direction = Direction.DOWN;
		} else if (grid.clone().move(Direction.RIGHT)) {
			direction = Direction.RIGHT;
			wasRightMove = true;
		} else {
			/* If all else fails, move up :/ */
			direction = Direction.UP;
		}

		return direction;
	}

	@Override
	public Direction findMove(Grid grid, int depth) {
		Direction direction = Direction.LEFT;

		/* First try find a move using our 'complex' algorithm. */
		direction = selectDirectionComplex(grid.clone(), depth);

		/* If that fails, use the simple algorithm. */
		if (direction == null) {
			direction = selectDirectionSimple(grid.clone());
		}

		return direction;
	}
}
