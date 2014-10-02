/**
 * 
 */
package nl.tudelft.ti2206.gameobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.log.Logger;

public class Solver extends TimerTask {

	private static Logger logger = Logger.getInstance();

	private Grid original;

	private int moves;

	private static void print(String str) {
		System.out.println("[AUTSOLVE]: " + str);
	}

	public void setGrid(Grid grid) {
		this.original = grid;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getMoves() {
		return moves;
	}

	public static int tryMoves(Grid ogrid) {

		int highest = 0;

		for (Direction direction : Grid.Direction.values()) {

			if (direction == Direction.UP)
				continue;
			
			Grid grid = ogrid.clone();
			grid.move(direction);

			highest = Math.max(highest, grid.getScore());
		}
		return highest;
	}

	public static Map<String, Object> bestMove(Grid ogrid) {
		Map<String, Object> result = new HashMap<>();

		int score = ogrid.getScore();
		Direction bestDirection = null;

		for (Direction direction : Grid.Direction.values()) {

			if (direction == Direction.UP || direction == Direction.RIGHT)
				continue;
			
			Grid grid = ogrid.clone();
			
			if (grid.move(direction) != -1) {

				int pointsAfter = grid.getScore() + tryMoves(grid.clone());
				
				if (pointsAfter > score) {
					score = pointsAfter;
					bestDirection = direction;
				}

			//	System.out.println("Our best move is " + direction
			//			+ " giving us " + score);
			}
		}
		result.put("score", score);
		result.put("direction", bestDirection);

		return result;
	}

	public static void bruteForce(Grid grid, int maxMoves) {

		int moves = 0;

		while (moves++ < maxMoves) {
			Map<String, Object> newResult = bestMove(grid.clone());

			// int newScore = ((Number) newResult.get("score")).intValue();
			Direction direction = ((Direction) newResult.get("direction"));
			
			if (direction == null) {
				print("smart selection failed! using 'dumb' movement selection");
				if (grid.clone().move(Direction.LEFT) != -1)
					direction = Direction.LEFT;
				else if (grid.clone().move(Direction.DOWN) != -1)
					direction = Direction.DOWN;
				else if (grid.clone().move(Direction.RIGHT) != -1)
					direction = Direction.RIGHT;
				else
					direction = Direction.UP;
			}
			print("making selected move: " + direction);
			grid.move(direction);
		}
	}

	public static Timer autoSolve(Grid grid, int delay, int moves) {
		print("Trying to solve grid automatically, making " + moves
				+ " move every " + delay + "ms...");

		Solver solver = new Solver();
		solver.setGrid(grid);
		solver.setMoves(moves);

		Timer timer = new Timer();
		timer.schedule(solver, 0, delay);

		return timer;
	}

	@Override
	public void run() {
		bruteForce(original, getMoves());
	}

}
