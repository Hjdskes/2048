package nl.tudelft.ti2206.utils.ai.solvers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * A solver that will attempt to solve the Grid using the Expectimax algorithm.
 */
public class Expectimax implements Solver {
	/** This matrix gives a weight to every tile. */
	private static final int[] WEIGHTMATRIX = {
		17, 15, 13, 11,
		15,  9, 10, 11,
		13, 10,  8,  8,
		11, 11,  8,  8
	};

	/**
	 * The depth map indicates the recursion-depth that will be used. A depth is
	 * chosen by looking at the amount of empty tiles. Example: 5 empty tiles
	 * results in index 5 being picked: a depth of 5.
	 */
	private static final int DEPTHMAP[] = {
		6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4
	};

	/**
	 * Two arrays holding all the possible values that will spawn in one, with
	 * their respective possibilities in the second.
	 */
	private static final int NEWTILES[] = { 1, 2 };
	private static final int POSSIBILITIES[] = { 9, 1 };

	/** This array gives weights to every tile value. */
	private int[] valueWeight;

	/** Constructs a new Expectimax object. */
	public Expectimax() {
		this.valueWeight = new int[Grid.NTILES];
		initValueWeights();
	}

	/** Initializes the valueWeight array. */
	private void initValueWeights() {
		int curWeight = 1;
		for (int i = 1; i < valueWeight.length; i++) {
			valueWeight[i] = curWeight;
			curWeight *= 3;
		}
	}

	@Override
	public Direction findMove(Grid grid, int depth) {
		double bestValue = 0;
		Direction bestDirection = null;

		for (Direction dir : Direction.values()) {
			Grid clone = grid.clone();
			if (clone.move(dir) == false) {
				continue;
			}

			int empty = clone.getEmptyTiles();
			double value = computerMove(clone, DEPTHMAP[empty], bestValue);
			if (value >= bestValue) { // FIXME: >= versus >
				bestValue = value;
				bestDirection = dir;
			}
		}
		return bestDirection;
	}

	/**
	 * Evaluates the given Grid, by multiplying the tile's position weight with
	 * its value weight.
	 * 
	 * @param tiles
	 *            The tiles to calculate on.
	 * @return The score for this Grid.
	 */
	private int evaluate(Tile[] tiles) {
		int value = 0;
		for (int i = 0; i < tiles.length; i++) {
			value += WEIGHTMATRIX[i] * valueWeight[tiles[i].getValue()];
		}
		return value;
	}

	/**
	 * The max part of the algorithm. Performs a "player move" and calculates
	 * the output's value.
	 * 
	 * @param grid
	 *            The Grid to calculate a move on.
	 * @param depth
	 *            The recursion depth to use.
	 * @param max
	 *            The maximum value of the previous moves.
	 * @return The best evaluation of the resulting grids.
	 */
	private double playerMove(Grid grid, int depth, double max) {
		int estimate = evaluate(grid.getTiles());

		if (estimate < 0.7 * max) {
			depth--;
		}

		if (depth <= 0) {
			return estimate;
		}

		/* Adjust next depth according to depth map. */
		int nextDepth = depth - 1;
		if (depth > 3) {
			int empty = grid.getEmptyTiles();
			if (nextDepth > DEPTHMAP[empty]) {
				nextDepth--;
			}
		}

		double bestValue = 0;
		for (Direction dir : Direction.values()) {
			Grid clone = grid.clone();
			if (clone.move(dir) == false) {
				continue;
			}

			double value = computerMove(clone, nextDepth, bestValue);

			if (value >= bestValue) { // FIXME: >= versus >
				bestValue = value;
			}
		}
		return bestValue;
	}

	/**
	 * The expecti part of the algorithm. Performs a "computer move" and
	 * calculates the output's value. A computer move is simply the random
	 * placement of the new tiles.
	 * 
	 * @param grid
	 *            The Grid to calculate a move on.
	 * @param depth
	 *            The recursion depth to use.
	 * @param bestValue
	 *            The maximum value of the previous moves.
	 * @return The average evaluation.
	 */
	private double computerMove(Grid grid, int depth, double bestValue) {
		int weight = 0;
		double score = 0;

		for (Tile tile : grid.getTiles()) {
			if (!tile.isEmpty()) {
				continue;
			}

			/*
			 * FIXME: to optimize, we can only check tiles with value 4 when we
			 * have a certain depth (e.g. >= 5). This will probably hurt our
			 * accuracy a bit, though.
			 */
			for (int i = 0; i < POSSIBILITIES.length; i++) {
				tile.setValue(NEWTILES[i]);
				score += POSSIBILITIES[i] * playerMove(grid, depth - 1, bestValue);
				weight += POSSIBILITIES[i];
				tile.setValue(0);
			}
		}
		return weight == 0 ? 0 : score / weight;
	}
}
