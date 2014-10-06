/**
 * 
 */
package nl.tudelft.ti2206.solver;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class HumanSolver {

	private static boolean wasRightMove = false;

	public static boolean lowerRowFull(Grid grid) {
		Tile[] tiles = grid.getTiles();
		for (int index = 12; index < 16; index += 1)
			if (tiles[index].isEmpty())
				return false;
		return !isMergePossibleLowerRow(tiles);
	}

	public static boolean isMergePossibleLowerRow(Tile[] tiles) {
		for (int i = tiles.length - 4; i < tiles.length - 1; i++) {
			if (!tiles[i].isEmpty()
					&& tiles[i].getValue() == tiles[i + 1].getValue()) {
				return true;
			}
		}
		return false;
	}

	public static int tryMoves(Grid ogrid) {

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

			// get highest score possible
			highest = Math.max(highest, cloned.getScore());
		}
		return highest;
	}

	public static Direction selectDirectionComplex(Grid ogrid) {

		int score = ogrid.getScore();
		Direction selected = null;

		if (wasRightMove) {
			wasRightMove = false;
			if (!lowerRowFull(ogrid))
				return Direction.LEFT;
		}

		if (!maxLowerLeft(ogrid) && ogrid.getTiles()[12].isEmpty()) {
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

				int pointsAfter = cloned.getScore() + tryMoves(cloned);

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

	public static boolean maxLowerLeft(Grid grid) {
		return grid.getTiles()[12].getValue() == grid.getCurrentHighestTile();
	}



	public static Direction selectMove(Grid grid) {
		Direction direction = Direction.LEFT;

		direction = selectDirectionComplex(grid.clone());

		if (direction == null) {

			// print("smart direction selection failed! using 'dumb' movement selection");
			direction = selectDirectionSimple(grid.clone());

		}
		return direction;
	}
//

//	
//	public static void autoMove(Grid grid) {
//		
////		if (Solver.getStrategy() == Solver.Strategy.RANDOM) {
//////			Direction direction = Grid.Direction.values()[(int) (Math.random()
//////					* Grid.Direction.values().length)];
//////			makeMove(grid, direction);
////		}
////		else {
//			
//			Direction direction = selectMove(grid);
//			// make the selected move
//			makeMove(grid, direction);
//		}
////	}

}
