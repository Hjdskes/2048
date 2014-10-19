package nl.tudelft.ti2206.utils.ai;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class Expectimax implements Solver {
	private static final int[] WEIGHTMATRIX = {
		17, 13, 12, 11,
		13,  9,  9, 10,
		12,  9,  8,  8,
		11, 10,  8,  8
//		17, 13, 12, 11,
//		13,  8,  8, 10,
//		12,  8,  6,  6,
//		11, 10,  6,  6
//		17, 13, 11, 10,
//		13, 10,  9,  9,
//		11,  9,  8,  8,
//		10,  9,  8,  8
//		20, 18, 15, 12,
//		18,  9,  9, 10,
//		15,  9,  8,  5,
//		12, 10,  5,  0
	};

	private static final int DEPTHMAP[] = {
		6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4
	};

	private static final int NEWTILES[] = { 1, 2 };
	private static final int POSSIBILITIES[] = { 9, 1};

	private int[] valueWeight;

	public Expectimax() {
		this.valueWeight = new int[Grid.NTILES];
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
			if (value >= bestValue) { //FIXME: >= versus >
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
			 * when we have a certain depth (e.g. >= 5). This will probably
			 * hurt our accuracy a bit, though. */
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
