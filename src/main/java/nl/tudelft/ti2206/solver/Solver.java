package nl.tudelft.ti2206.solver;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.log.Logger;

public class Solver extends TimerTask {
	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	private static final int[][] weightMatrices = {
			{ 8, 7, 6, 5, 1, 2, 3, 4, -4, -3, -2, -1, -5, -6, -7, -8 },
			{ -5, -4, 1, 8, -6, -3, 2, 7, -7, -2, 3, 6, -8, -1, 4, 5 },
	// { 8, 4, 1, 0,
	// 4, 1, 0, -1,
	// 1, 0, -1, -4,
	// 0, -1, -4, -8
	// },
	// { 0, 1, 4, 8,
	// -1, 0, 1, 4,
	// -4, -1, 0, 1,
	// -8, -4, -1, 0
	// }
	};

	private Grid original;
	private int depth;
	private boolean calculating;

	public Solver(Grid grid, int depth) {
		this.original = grid;
		this.depth = depth;
		this.calculating = false;
	}

	@Override
	public void run() {
		if (original.getPossibleMoves() == 0
				|| (!TwentyFourtyGame.isRunning() && !TwentyFourtyGame
						.isContinuing())) {
			this.cancel();
		} else if (!calculating) {
			Direction dir = findMove(original, depth);

			if (dir != null) {
				logger.info(className, "Direction chosen: " + dir);
				original.move(dir);
			} else {
				logger.info(className, "Failed to pick a direction");
			}
			calculating = false;
		}
	}

	public void solve() {
		new Timer().schedule(this, 0, 100);
	}

	public Direction findMove(Grid grid, int depth) {
		logger.info(className, "Calculating next move...");
		this.calculating = true;
		return expectimax(grid, depth);
	}

	private double evaluate(Grid grid) {
		double empty = getEmptyTilesNumber(grid.getTiles());
		// double max = Math.pow(2, grid.getCurrentHighestTile());
		// double monotonicity = getMonotonicity(grid.getTiles());
		double gradient = getGradient(grid.getTiles());
		// double smoothness = getSmoothness(grid.getTiles());
		// return 0.4 * gradient + 0.1 * smoothness + 0.4 * empty + 0.1 *
		// monotonicity;
		// return 0.3 * gradient + 0.1 * smoothness + 0.6 * empty;
		// return 0.4 * gradient + 0.1 * smoothness + 0.4 * empty + 0.1 * max;
		// return 0.8 * gradient + 0.2 * smoothness;
		// return 0.4 * gradient + 0.6 * empty; //GOOD
		// return 0.4 * gradient + Math.log(grid.getScore()) * empty +
		// grid.getScore(); // GOOD
		return 0.6 * gradient + 0.4 * empty; // BETTER?
		// return 0.6 * empty + 0.4 * gradient + 0.1 * smoothness ;
		// return gradient + grid.getScore();
	}

	private Direction expectimax(Grid grid, int depth) {
		double bestValue = 0;
		Direction bestDirection = null;

		for (Direction dir : Direction.values()) {
			Grid clone = grid.clone();
			if (clone.move(dir) < 0) {
				continue;
			}

			double value = computerMove(clone, depth);
			if (value >= bestValue) {
				bestValue = value;
				bestDirection = dir;
			}
		}
		return bestDirection;
	}

	private double playerMove(Grid grid, HashMap<Long, Double> cache, int depth) {
		if (grid.getCurrentHighestTile() == 11) {
			return Double.MAX_VALUE;
		} else if (grid.getPossibleMoves() == 0) {
			return 0;
		} else if (depth == 0) {
			return evaluate(grid);
		}
		double bestValue = 0;

		for (Direction dir : Direction.values()) {
			Grid clone = grid.clone();
			if (clone.move(dir) < 0) {
				continue;
			}

			double value = 0;
			if (cache.containsKey(zobrist(clone.getTiles()))) {
				value = cache.get(zobrist(clone.getTiles()));
			} else {
				value = computerMove(clone, depth - 1);
				cache.put(zobrist(clone.getTiles()), value);
			}

			if (value > bestValue) {
				bestValue = value;
			}
		}
		return bestValue;
	}

	private double computerMove(Grid grid, int depth) {
		HashMap<Long, Double> cache = new HashMap<Long, Double>();
		if (depth == 0) {
			return playerMove(grid, cache, depth);
		}
		int[] possibleMoves = { 1, 2 };
		double[] probabilities = { 0.9, 0.1 };
		double totalScore = 0;
		double totalWeight = 0;

		for (Tile tile : grid.getTiles()) {
			if (tile.isEmpty()) {
				for (int i = 0; i < possibleMoves.length; i++) {
					Grid clone = grid.clone();
					clone.setTile(tile.getIndex(), possibleMoves[i]);

					double value = playerMove(clone, cache, depth - 1);
					totalScore += probabilities[i] * value;
					totalWeight += probabilities[i];
				}
			}
		}
		return totalWeight == 0 ? 0 : totalScore / totalWeight;
	}

	private long zobrist(Tile[] tiles) {
		long hash = 3485734985L;
		for (Tile tile : tiles) {
			hash ^= 46527859L * tile.getValue();
		}
		return hash;
	}

	private double getGradient(Tile[] tiles) {
		double best = 0;

		for (int i = 0; i < weightMatrices.length; i++) {
			double s = 0;
			for (int j = 0; j < tiles.length; j++) {
				s += weightMatrices[i][j] * Math.pow(2, tiles[j].getValue());
			}
			s = Math.abs(s);
			if (s > best) {
				best = s;
			}
		}
		return best;
	}

	private double getEmptyTilesNumber(Tile[] tiles) {
		double res = 0;
		for (Tile tile : tiles) {
			if (tile.isEmpty()) {
				res++;
			}
		}
		return res;
	}
}
