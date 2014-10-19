package nl.tudelft.ti2206.utils.ai;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class Solver extends TimerTask {
	private static final int[] WEIGHTMATRIX = {
//		17, 13, 12, 11,
//		13,  9,  9, 10,
//		12,  9,  8,  8,
//		11, 10,  8,  8
//		17, 13, 12, 11,
//		13,  8,  8, 10,
//		12,  8,  6,  6,
//		11, 10,  6,  6
//		17, 13, 11, 10,
//		13, 10,  9,  9,
//		11,  9,  8,  8,
//		10,  9,  8,  8
		20, 18, 15, 12,
		18, 10,  9,  8,
		15,  9,  8,  5,
		12,  8,  5,  0
	};

	private static final int DEPTHMAP[] = {
		6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4
	};

	private Grid original;
	private int[] valueWeight;
	private boolean calculating;

	public Solver(Grid grid) {
		this.original = grid;
		this.calculating = false;
		this.valueWeight = new int[grid.getTiles().length];
		initValueWeights();
	}

	private void initValueWeights() {
		int curWeight = 1;
		for (int i = 1; i < valueWeight.length; i++) {
			valueWeight[i] = curWeight;
			curWeight *= 3;
		}
	}

	@Override
	public void run() {
		if (original.getPossibleMoves() == 0 || (!TwentyFourtyGame.isRunning() && !TwentyFourtyGame.isContinuing())) {
			this.cancel();
		} else if (!calculating) {
			Direction dir = findMove(original);
			if (dir != null) {
				original.move(dir);
			}
			calculating = false;
		}
	}

	public void solve() {
		new Timer().schedule(this, 0, 50);
	}

	public Direction findMove(Grid grid) {
		this.calculating = true;

		double bestValue = 0;
		Direction bestDirection = null;

		for (Direction dir : Direction.values()) {
			Grid clone = grid.clone();
			if (clone.move(dir) == false) {
				continue;
			}

			int empty = clone.getEmptyTiles();
			double value = computerMove(clone, DEPTHMAP[empty], bestValue);
			if (value > bestValue) { //FIXME: >= versus >
				bestValue = value;
				bestDirection = dir;
			}
		}
		return bestDirection;
	}

	private int evaluate(Tile[] tiles) {
		int value = 0;
		for (int i = 0; i < tiles.length; i++) {
			value += WEIGHTMATRIX[i] * valueWeight[tiles[i].getValue()];
		}
		return value;
	}

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

			if (value >= bestValue) { //FIXME: >= versus >
				bestValue = value;
			}
		}
		return bestValue;
	}

	private double computerMove(Grid grid, int depth, double bestValue) {
		int weight = 0;
		double score = 0;

		for (Tile tile : grid.getTiles()) {
			if (!tile.isEmpty()) {
				continue;
			}

			/* FIXME: to optimize, we can only check tiles with value 4
			 * when we have a certain depth (e.g. >= 5). We should decide
			 * whether we want this (accuracy versus speed). If not, revert to
			 * old behavior with array. */

			/* Try a new tile with value 2. */
			tile.setValue(1);
			score += 9 * playerMove(grid, depth - 1, bestValue);
			weight += 9;

			/* Try a new tile with value 4. */
			tile.setValue(2);
			score += playerMove(grid, depth - 1, bestValue);
			weight += 1;

			tile.setValue(0);
		}
		return weight == 0 ? 0 : score / weight;
	}
}
