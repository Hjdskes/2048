package nl.tudelft.ti2206.solver;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.gameobjects.TileIterator;

public class Solver extends TimerTask {
	private static int ROW_LENGTH = 4;
	/**
	 * These constants are to provide weights in the static evaluation function.
	 */
	private static double EMPTY = 2.7;
	//private static double SCORE = 7.4;
	private static double MONO = 1.0;
	private static double MAX = 1.0;

	private Grid original;
	private Timer timer;

	public Solver(Grid grid, int delay) {
		this.original = grid;
		this.timer = new Timer();
		timer.schedule(this, 0, delay);
	}

	private void solve() {
		Grid clone;
		Direction move = Direction.DOWN;
		int value, bestValue = Integer.MIN_VALUE;
		double increment, empty, monotonicity, max;

		for (Direction dir : Direction.values()) {
			clone = cloneGrid();
			increment = clone.move(dir);
			empty = getEmptyTiles(clone);
			monotonicity = getMonotonicity(clone);
			max = clone.getCurrentHighestTile() / Math.log(2);
			value = increment < 0 ? Integer.MIN_VALUE : (int) (EMPTY * empty
					//+ SCORE * increment
					+ MAX * max
					+ MONO * monotonicity);
			if (value > bestValue) {
				bestValue = value;
				move = dir;
			}
		}
		original.move(move);
	}

	private double getMonotonicity(Grid grid) {
		Tile[] tiles = grid.getTiles();
		int res = 0;

		res += getMonotonicityHorizontal(tiles);
		tiles = rotate(tiles, 90);
		res += getMonotonicityHorizontal(tiles);
		tiles = rotate(tiles, 270);
		return res;
	}

	private double getMonotonicityHorizontal(Tile[] tiles) {
		TileIterator iterator = null;
		int[] totals = new int[2];
		int currentValue, nextValue;

		/* First check in the horizontal direction: */
		/* for each row in the Grid... */
		for (int index = 0; index < tiles.length; index += ROW_LENGTH) {
			/* ... create an iterator that iterates over that row only... */
			iterator = new TileIterator(Arrays.copyOfRange(tiles, index, index
					+ ROW_LENGTH));
			/* ... and make it do so four times. */
			Tile current = iterator.next();
			Tile next = iterator.next();
			while (iterator.hasNext()) {
				while (next.isEmpty() && iterator.hasNext()) {
					next = iterator.next();
				}
				currentValue = current.isEmpty() ? 0 : ((int) Math
						.log(current.getValue() / Math.log(2)));
				nextValue = next.isEmpty() ? 0 : ((int) Math.log(next
						.getValue() / Math.log(2)));
				if (currentValue > nextValue) {
					totals[0] += nextValue - currentValue;
				} else {
					totals[1] += currentValue - nextValue;
				}
				if (iterator.hasNext()) {
					current = next;
					next = iterator.next();
				}
			}
		}
		return Math.max(totals[0], totals[1]);
	}

	private Tile[] rotate(Tile[] tiles, int angle) {
		Tile[] res = new Tile[16];

		int offsetX = 3, offsetY = 3;
		if (angle == 90) {
			offsetY = 0;
		} else if (angle == 270) {
			offsetX = 0;
		}

		double rad = Math.toRadians(angle);
		int cos = (int) Math.cos(rad);
		int sin = (int) Math.sin(rad);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				int newX = (x * cos) - (y * sin) + offsetX;
				int newY = (x * sin) + (y * cos) + offsetY;
				res[newX + newY * 4] = tiles[x + y * 4];
			}
		}
		return res;
	}

	/**
	 * @param grid The grid whose empty tiles to calculate.
	 * @return The 2log of the amount of empty tiles.
	 */
	private double getEmptyTiles(Grid grid) {
		int res = 0;
		TileIterator iterator = new TileIterator(grid.getTiles());
		while (iterator.hasNext()) {
			if (iterator.next().isEmpty()) {
				res++;
			}
		}
		iterator.reset();
		return Math.log(res);
	}

	// private Integer minimax(Node root, int depth) {
	// /* Stop condition and sanity check. */
	// if (depth == 0 || root.isLeaf()) {
	// return root.getValue();
	// }
	//
	// /*
	// * Calculate maximizing player, we ignore the minimizing player as there
	// * is only one here: us.
	// */
	// int bestValue = Integer.MIN_VALUE;
	// int value = 0;
	// for (Node child : root.getChildren()) {
	// if (child == null) {
	// continue;
	// }
	// value = minimax(child, depth - 1);
	// if (value > bestValue) {
	// bestValue = value;
	// }
	// }
	// return bestValue;
	// }

	private Grid cloneGrid() {
		Grid res = new Grid(true);
		TileIterator iterator = new TileIterator(original.getTiles());
		while (iterator.hasNext()) {
			res.setTile(iterator.getIndex(), iterator.next().getValue());
		}
		iterator.reset();
		return res;
	}

	@Override
	public void run() {
		if (original.getPossibleMoves() == 0
				|| TwentyFourtyGame.getState() != GameState.RUNNING) {
			this.cancel();
		} else {
			this.solve();
		}
	}
}
