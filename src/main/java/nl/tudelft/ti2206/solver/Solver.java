package nl.tudelft.ti2206.solver;

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
	private static double SMOOTH = 0.1; // 0.1
	private static double EMPTY = 2.7; // 3.5
	private static double SCORE = 0.0; // 1.0
	private static double MONO = 1.0; // 0.7
	private static double MAX = 1.0; // 1.0

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
		int value = Integer.MIN_VALUE, bestValue = Integer.MIN_VALUE;
		double increment, empty, monotonicity, max, smoothness;

		for (Direction dir : Direction.values()) {
			clone = cloneGrid();
			increment = clone.move(dir);
			if (increment < 0) {
				continue;
			}
			empty = getEmptyTiles(clone.getTiles());
			max = (clone.getCurrentHighestTile() / Math.log(2));
			monotonicity = getMonotonicity(clone.getTiles());
			smoothness = getSmoothness(clone.getTiles());
			value = (int) (EMPTY * empty + SCORE * increment + MAX * max + MONO
					* monotonicity + SMOOTH * smoothness);
			if (value > bestValue) {
				bestValue = value;
				move = dir;
			}
		}
		original.move(move);
	}

	/**
	 * Returns the neighbors (if any) of a Tile. Skips the empty neighbors. 
	 * @param tiles The tiles in the Grid to calculate the neighbors from.
	 * @param index The index of the Tile whose neighbors we want.
	 * @return An array containing both neighbors in the right and down direction.
	 */
	private Tile[] getTileNeighbors(Tile[] tiles, int index) {
		Tile[] neighbors = new Tile[2];

		/*
		 * Right neighbor: check if the index we're checking is not the right
		 * edge of the grid by making sure (index + 1) is a not a multiple of 4.
		 */
		for (int offset = 1; offset < 4; offset += 1) {
			if ((index + offset) % 4 != 0 && (index + offset) < tiles.length) {
				if (!tiles[index + offset].isEmpty()) {
					neighbors[0] = tiles[index + offset];
					break;
				}
			}
		}

		/*
		 * Lower neighbor: check if the index we're checking is not the bottom
		 * edge of the grid by making sure (index + 4) is smaller than
		 * grid.length:
		 */
		for (int offset = 4; offset < 16; offset += 4) {
			if ((index - offset) >= 0) {
				if (!tiles[index - offset].isEmpty()) {
					neighbors[1] = tiles[index - offset];
					break;
				}
			}
		}

		return neighbors;
	}

	/**
	 * The smoothness of the Grid is the 'amount' of how adjacent tiles are mergable.
	 * @param tiles The tiles in the Grid to calculate the smoothness of. 
	 * @return The 'amount' of smoothness.
	 */
	private double getSmoothness(Tile[] tiles) {
		double smoothness = 0;
		double currentValue, targetValue;
		Tile[] neighbors;

		for (int i = 0; i < 16; i++) {
			if (!tiles[i].isEmpty()) {
				currentValue = Math.log(tiles[i].getValue()) / Math.log(2);
				neighbors = getTileNeighbors(tiles, i);

				for (Tile tile : neighbors) {
					if (tile != null) {
						targetValue = Math.log(tile.getValue()) / Math.log(2);
						smoothness -= Math.abs(currentValue - targetValue);
					}
				}
			}
		}
		return smoothness;
	}

	/**
	 * The monotonicity of the Grid is the 'amount' of how all the tiles are
	 * either increasing or decreasing along both the horizontal and vertical directions.
	 *  
	 * @param tiles The tiles in the Grid to calculate the monotonicity of.
	 * @return The 'amount' of monotonicity.
	 */
	private double getMonotonicity(Tile[] tiles) {
		double mono = 0;

		mono += getMonotonicityHorizontal(tiles);
		tiles = rotate(tiles, 90);
		mono += getMonotonicityHorizontal(tiles);
		tiles = rotate(tiles, 270);
		return mono;
	}

	private double getMonotonicityHorizontal(Tile[] tiles) {
		// TileIterator iterator = null;
		double[] totals = new double[2];
		double currentValue, nextValue;

		// /* For each row in the Grid... */
		// for (int index = 0; index < tiles.length; index += ROW_LENGTH) {
		// /* ... create an iterator that iterates over that row only... */
		// iterator = new TileIterator(Arrays.copyOfRange(tiles, index, index
		// + ROW_LENGTH));
		//
		// /* Ask the first two tiles in that row. We know they exist. */
		// Tile current = iterator.next();
		// Tile next = iterator.next();
		// /* Now for the remaining tiles (2)*/
		// while (iterator.hasNext()) {
		// /* Skip all the empty tiles. */
		// while (next.isEmpty() && iterator.hasNext()) {
		// next = iterator.next();
		// }
		// currentValue = current.isEmpty() ? 0 : ((int) Math
		// .log(current.getValue() / Math.log(2)));
		// nextValue = next.isEmpty() ? 0 : ((int) Math.log(next
		// .getValue() / Math.log(2)));
		// if (currentValue > nextValue) {
		// totals[0] += nextValue - currentValue;
		// } else {
		// totals[1] += currentValue - nextValue;
		// }
		// if (iterator.hasNext()) {
		// current = next;
		// next = iterator.next();
		// }
		// }
		// }

		for (int y = 0; y < 4; y++) {
			int current = 0;
			int next = current + 1;
			while (next < 4) {
				while (next < 4 && tiles[next + 4 * y].isEmpty()) {
					next++;
				}
				if (next >= 4) {
					next--;
				}
				currentValue = tiles[current + 4 * y].isEmpty() ? 0
						: ((int) Math.log(tiles[current + 4 * y].getValue()
								/ Math.log(2)));
				nextValue = tiles[next + 4 * y].isEmpty() ? 0 : ((int) Math
						.log(tiles[next + 4 * y].getValue() / Math.log(2)));
				/* If the current is bigger, the order is decreasing */
				if (currentValue > nextValue) {
					totals[0] += nextValue - currentValue;
				} else {
					totals[1] += currentValue - nextValue;
				}
				current = next;
				next++;
			}
		}

		return Math.max(totals[0], totals[1]);
	}

	/**
	 * Rotates the grid. Taken from:
	 * https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L184
	 * 
	 * @param tiles
	 *            The tiles to rotate.
	 * @param angle
	 *            The angle by which to rotate.
	 * @return The rotated Grid.
	 */
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
	 * @param tiles The tiles in the Grid to calculate the amount of empty tiles for.
	 * @return The 2log of the amount of empty tiles.
	 */
	private double getEmptyTiles(Tile[] tiles) {
		double res = 0;
		TileIterator iterator = new TileIterator(tiles);
		while (iterator.hasNext()) {
			if (iterator.next().isEmpty()) {
				res++;
			}
		}
		iterator.reset();
		return (res == 0 ? res : Math.log(res));
	}

	/**
	 * @return A clone of the original grid.
	 */
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
