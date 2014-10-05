/**
 * 
 */
package nl.tudelft.ti2206.ai;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class Solver extends TimerTask {
	

	private Grid original;
	private int wins = 0;
	private int runs = 0;
	private static int maxruns = 20;
	private static int succesfulMoves = 0;
	private static boolean wasRightMove = false;
	
	private static void print(String str) {
		System.out.println("[AUTSOLVE]: " + str);
	}

	public void setGrid(Grid grid) {
		this.original = grid;
	}

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

			// ignore up direction
			if (direction == Direction.UP)// && !leftColFull(ogrid))
				continue;

			// ignore right direction if lower row is not full
//			if (direction == Direction.RIGHT && !lowerRowFull(ogrid))
//				continue;

			Grid grid = ogrid.clone();
			grid.move(direction);

			// get highest score possible
			highest = Math.max(highest, grid.getScore());
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
			Grid grid = ogrid.clone();

			if (direction == Direction.UP)// && !leftColFull(ogrid))
				continue;

//			if (direction == Direction.RIGHT && !lowerRowFull(ogrid))
//				continue;

			// if move actually is possible
			if (grid.move(direction) != -1) {

				int pointsAfter = grid.getScore() + tryMoves(grid.clone());

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

	public static void makeMove(Grid grid, Direction direction) {
		if (grid.move(direction) != -1) {
	//		print("selected move succesfully performed: " + direction);
			succesfulMoves += 1;
		} 
//		else
//			print("selected move failed: " + direction);
	}

	public static Direction selectMove(Grid grid) {
		Direction direction = Direction.LEFT;

		direction = selectDirectionComplex(grid.clone());

		if (direction == null) {

		//	print("smart direction selection failed! using 'dumb' movement selection");
			direction = selectDirectionSimple(grid.clone());

		}
		return direction;
	}

	public static void autoMove(Grid grid) {

		Direction direction = selectMove(grid);
		// make the selected move
		makeMove(grid, direction);
	}

	public static Timer autoSolve(Grid grid, int delay, int maxruns) {
		print("Trying to solve grid automatically, making one move every "
				+ delay + "ms...");

		Solver solver = new Solver();
		solver.setGrid(grid);

		Timer timer = new Timer();
		timer.schedule(solver, 0, delay);
		
		Solver.maxruns = maxruns;
		
		return timer;
	}
	
	public static void printGrid(Grid grid) {
		Tile[] tiles = grid.getTiles();

		String line = "Resulting grid: ";
		
		for (int i = 0; i < tiles.length; i += 1) {
			line += tiles[i].getValue() + " ";
		}
		print(line);
	}

	@Override
	public void run() {
		
		if (original.getCurrentHighestTile() >= 2048) {
			wins += 1;
			runs += 1;
			
			print("-- Statistics for game " + runs + " -- WON");
			print(runs + ": Game ended. Highest tile = " + original.getCurrentHighestTile() + ". Succesful moves made: " + succesfulMoves);
			print(runs + ": Game WON (" + wins + " out of " + runs + " games were won).");
			printGrid(original);
			print(runs + ": Resetting grid and restarting game...\r\n");
			
			original.restart();
			succesfulMoves = 0;
		}
		else if (original.getPossibleMoves() == 0) {
			runs += 1;
			
			print("-- Statistics for game " + runs + " -- LOST");
			print(runs + ": Game ended. Highest tile = " + original.getCurrentHighestTile() + ". Succesful moves made: " + succesfulMoves);
			print(runs + ": Game LOST (" + wins + " out of " + runs + " games were won)");
			printGrid(original);
			print(runs + ": Resetting grid and restarting game...\r\n");
			
			original.restart();
			succesfulMoves = 0;
		} else {
			autoMove(original);
		}
		
		if (runs >= maxruns) {
			print("Maximum amount of runs reached: " + maxruns + ", ending timer...");
			this.cancel();
		}
	}
}
