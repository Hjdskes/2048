/**
 * 
 */
package nl.tudelft.ti2206.solver;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * This class attempts to solve the grid using a 'human-like' strategy:
 * - Keep the highest tile in one of the grid's corners (lower left in this case).
 * - Merge tiles of equal values with each other whenever possible.
 *
 * By default, this class looked ahead two moves. However, to increase accuracy
 * the last method (tryMoves) is now recursive:
 * selectDirectionComplex + tryMoves(depth = 0) = look 2 moves ahead
 * selectDirectionComplex + tryMoves(depth = 1) = look 3 moves ahead
 */

public class HumanSolver {
	
	/** The index number of the lower left tile. */
	private static final int LOWER_LEFT = 12;
	
	/** Boolean variable for keeping if the last selected move was right. */
	private static boolean wasRightMove = false;

	/**
	 * Check if lower left corner is empty.
	 * @param grid the grid to check
	 * @return true if lower left corner is empty
	 */
	public static boolean emptyLowerLeft(Grid grid) {
		return grid.getTiles()[LOWER_LEFT].isEmpty();
	}
	
	/**
	 * Check if the highest tile is in the lower left corner.
	 * @param grid the grid to check
	 * @return true if highest tile is in lower left corner
	 */
	public static boolean maxLowerLeft(Grid grid) {
		return grid.getTiles()[LOWER_LEFT].getValue() == grid.getCurrentHighestTile();
	}
	
	/**
	 * Check whether the lower row is full.
	 * @param grid the grid to check
	 * @return true if the lower row is full
	 */
	public static boolean lowerRowFull(Grid grid) {
		Tile[] tiles = grid.getTiles();
		for (int index = 12; index < 16; index += 1)
			if (tiles[index].isEmpty())
				return false;
		return !isMergePossibleLowerRow(grid);
	}
	
	/**
	 * Check whether a merge is possible on the lower row of the grid.
	 * @param grid the grid to check
	 * @return true if a merge is possible on the lower row
	 */
	public static boolean isMergePossibleLowerRow(Grid grid) {
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
	 * @param ogrid the grid to check
	 * @param depth recursion depth
	 * @return how many points a move will gain
	 */
	public static int nextMove(Grid ogrid, int depth) {
		
		//System.out.println("tryMoves, depth = " + depth);

		int highest = 0;

		for (Direction direction : Grid.Direction.values()) {

			// never ever move up:
			if (direction == Direction.UP)// && !leftColFull(ogrid))
				continue;

			// we shouldn't move to right if the lower row is not full
			// so ignore moving to the right
			if (direction == Direction.RIGHT && !lowerRowFull(ogrid))
				continue;

			Grid cloned = ogrid.clone();
			cloned.move(direction);
			
			int score = cloned.getScore();
			
			if (depth > 0)
				score += nextMove(cloned, depth - 1); // recursion!
			
			// get highest score possible
			highest = Math.max(highest, score);
		}
		return highest;
	}
	
	/**
	 * Select a direction to move to based on which direction gives us the
	 * highest amount of points.
	 * @param ogrid the grid
	 * @param depth the depth to check
	 * @return direction which gives us the highest amount of points
	 */
	public static Direction selectDirectionComplex(Grid ogrid, int depth) {

		if (depth <= 0)
			System.out.println("WARNING: depth <= 0!");
		
		int score = ogrid.getScore();
		Direction selected = null;

		if (wasRightMove) {
			wasRightMove = false;
			if (!lowerRowFull(ogrid))
				return Direction.LEFT;
		}

		if (!maxLowerLeft(ogrid) && emptyLowerLeft(ogrid)) {
			return Direction.LEFT;
		}

		for (Direction direction : Grid.Direction.values()) {
			Grid cloned = ogrid.clone();

			// never ever move up:
			if (direction == Direction.UP)// && !leftColFull(ogrid))
				continue;

			// we shouldn't move to right if the lower row is not full
			// so ignore moving to the right
			if (direction == Direction.RIGHT && !lowerRowFull(ogrid))
				continue;

			// if move actually is possible
			if (cloned.move(direction) != -1) {

				int pointsAfter = cloned.getScore() + nextMove(cloned, depth - 1);

				if (pointsAfter > score) {
					score = pointsAfter;
					selected = direction;
				}
			}
		}

		if (selected == Direction.RIGHT)
			wasRightMove = true;

		return selected;
	}
	
	/**
	 * Fallback method for when other movement selections fail.
	 * This will select a move in the order: LEFT, DOWN, RIGHT.
	 * If none of these are possible: move UP.
	 * @param grid the grid to perform a movement on
	 * @return direction to perform move to
	 */
	public static Direction selectDirectionSimple(Grid grid) {
		Direction direction = Direction.LEFT;

		if (grid.clone().move(Direction.LEFT) != -1) {
			direction = Direction.LEFT;
		} else if (grid.clone().move(Direction.DOWN) != -1) {
			direction = Direction.DOWN;
		} else if (grid.clone().move(Direction.RIGHT) != -1) {
			direction = Direction.RIGHT;
			wasRightMove = true;
		} else {
			// if all else fails, move up :/
			direction = Direction.UP;
		}

		return direction;
	}


	/**
	 * Select a suitable move on a grid. This will select one of the moves	
	 * LEFT, DOWN, RIGHT or UP.
	 * @param grid the grid to check
	 * @param depth recursion depth
	 * @return the direction to move into
	 */
	public static Direction selectMove(Grid grid, int depth) {
		
		// set default direction to LEFT
		Direction direction = Direction.LEFT;
		
		// select a move using our 'complex' algorithm
		direction = selectDirectionComplex(grid.clone(), depth);

		if (direction == null) {

			// print("smart direction selection failed! using 'dumb' movement selection");
			direction = selectDirectionSimple(grid.clone());

		}
		return direction;
	}

}