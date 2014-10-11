package nl.tudelft.ti2206.solver;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class Solver extends TimerTask {
	private static final double[][] weightMatrices = {
		{
		   8,  4,  2,  0,
		   4,  2,  0, -2,
		   2,  0, -2, -4,
		   0, -2, -4, -8
		},
//		{
//		   0,  1,  2,  3,
//		  -1,  0,  1,  2,
//		  -2, -1,  0,  1,
//		  -3, -2, -1,  0
//		},
//		{  8,  7,  6,  5,
//		   1,  2,  3,  4,
//		  -4, -3, -2, -1, 
//		  -5, -6, -7, -8
//		},
//		{ -5, -4,  1,  8,
//		  -6, -3,  2,  7,
//		  -7, -2,  3,  6,
//		  -8, -1,  4,  5
//		},
//		{  4, 3, 3, 4,
//		   3, 2, 2, 3,
//		   3, 2, 2, 3,
//		   4, 3, 3, 4
//		}
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
		if (original.getPossibleMoves() == 0 ||
				!TwentyFourtyGame.isRunning() || !TwentyFourtyGame.isContinuing()) {
			this.cancel();
		} else if (!calculating) {
		//	System.out.println("Calculating next move...");
			Direction dir = findMove(original, depth);
		//	System.out.println("Direction chosen: " + dir);
			if (dir != null) {
				original.move(dir);
			} 
			//else {
			//	System.out.println("AUTOSOLVE: FAILED");
			//}
			calculating = false;
		}
	}

	public void solve() {
		new Timer().schedule(this, 0, 50);
	}

	public Direction findMove(Grid grid, int depth) {
		this.calculating = true;
		return expectimax(grid, depth);
	}

	private double evaluate(Grid grid) {
		double empty = getEmptyTilesNumber(grid.getTiles());
		// double max = Math.pow(2, grid.getCurrentHighestTile());
		// double monotonicity = getMonotonicity(grid.getTiles());
		double gradient = getGradient(grid.getTiles());
		//double clusteringScore = getClustering(grid);
		//double smoothness = getSmoothness(grid.getTiles());
		//return 0.4 * gradient + 0.1 * smoothness + 0.4 * empty + 0.1 * monotonicity;
		//return 0.3 * gradient + 0.1 * smoothness + 0.6 * empty;
		//return 0.4 * gradient + 0.1 * smoothness + 0.4 * empty + 0.1 * max;
		//return 0.8 * gradient + 0.2 * smoothness;
		return 0.4 * gradient + 0.6 * empty; // GOOD
		//return 0.6 * gradient + 0.4 * empty;
		//return gradient + empty;
		//return clusteringScore + empty + gradient;
		//return gradient;
	}

	private Direction expectimax(Grid grid, int depth) {
		double bestValue = 0;
		Direction bestDirection = null;

		for (Direction dir : Direction.values()) {
			final long t0 = System.currentTimeMillis();
			Grid clone = cloneGrid(grid);
			if (clone.move(dir) < 0) {
				continue;
			}

			double value = computerMove(clone, depth);
			if (value >= bestValue) {
				bestValue = value;
				bestDirection = dir;
			}
			//final long t1 = System.currentTimeMillis();
			//System.out.println("Total time: " + (t1 - t0));
		}
		return bestDirection;
	}

	private double playerMove(Grid grid, HashMap<Long, Double> table, int depth) {
		if (grid.getCurrentHighestTile() == 11) {
			return Double.MAX_VALUE;
		} else if (grid.getPossibleMoves() == 0) {
			return Double.MIN_VALUE;
		} else if (depth == 0) {
			return evaluate(grid);
		}
		double bestValue = 0;

		for (Direction dir : Direction.values()) {
			Grid clone = cloneGrid(grid);
			if (clone.move(dir) < 0) {
				continue;
			}

			double value = 0;
			if (table.containsKey(zobrist(clone.getTiles()))) {
				value = table.get(zobrist(clone.getTiles())); 
			} else {
				value = computerMove(clone, depth - 1);
				table.put(zobrist(clone.getTiles()), value);
			}

			if (value > bestValue) {
				bestValue = value;
			}
		}
		return bestValue;
	}

	private double computerMove(Grid grid, int depth) {
		HashMap<Long, Double> table = new HashMap<Long, Double>();
		if (depth == 0) {
			return playerMove(grid, table, depth);
		}
		int[] possibleMoves = { 1, 2 };
		double[] probabilities = { 0.9, 0.1 };
		double totalScore = 0;
		double totalWeight = 0;

		for (Tile tile : grid.getTiles()) {
			if (tile.isEmpty()) {
				for (int i = 0; i < possibleMoves.length; i++) {
					Grid clone = cloneGrid(grid);
					clone.setTile(tile.getIndex(), possibleMoves[i]);

					double value = playerMove(clone, table, depth - 1);
					totalScore  += probabilities[i] * value;
					totalWeight += probabilities[i];
				}
			}
		}
		return totalWeight == 0 ? 0 : totalScore / totalWeight;
	}

	private long zobrist(Tile[] tiles) {
		long hash = 3485734985L;

		for(Tile tile : tiles) {
			hash ^= 46527859L * tile.getValue();
		}
		return hash;
	}

//	private double getSmoothness(Tile[] tiles) {
//	double smoothness = 0;
//	double currentValue, targetValue;
//	Tile[] neighbors;
//
//	for (int i = 0; i < 16; i++) {
//		if (!tiles[i].isEmpty()) {
//			currentValue = tiles[i].getValue();
//			neighbors = getTileNeighbors(tiles, i);
//
//			for (Tile tile : neighbors) {
//				if (tile != null) {
//					targetValue = tile.getValue();
//					smoothness -= Math.abs(currentValue - targetValue);	
//				}
//			}
//		}
//	}
//	return smoothness;
//}

	private double getClustering(Grid grid) {
		Tile[] tiles = grid.getTiles();
		int clusteringScore = 0;

		for (Tile tile : tiles) {
			if (tile.isEmpty()) {
				continue;
			}

			List<Tile> neighbors = grid.getTileNeighbors(tile.getIndex());
			int numOfNeighbors = neighbors.size();
			int sum = 0;
			for (Tile neighbor : neighbors) {
				if (neighbor.isEmpty()) {
					continue;
				}

				int value = neighbor.getValue();
				if (value > 0) {
					++numOfNeighbors;
					sum += Math.abs(Math.pow(2, tile.getValue()) - Math.pow(2, value));
				}
			}
			clusteringScore += sum / numOfNeighbors;
		}
		return clusteringScore;
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

//	private double getMonotonicity(Tile[] tiles) {
//		double mono = getMonotonicityHorizontal(tiles);
//		tiles = rotate(tiles, 90);
//		mono += getMonotonicityHorizontal(tiles);
//		tiles = rotate(tiles, 270);
//		return mono;
//	}

	private double getEmptyTilesNumber(Tile[] tiles) {
		double res = 0;
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i].isEmpty()) {
				res++;
			}
		}
		return (res == 0 ? res : Math.log(res));
		//return res;
	}

//	private Tile[] getTileNeighbors(Tile[] tiles, int index) {
//		Tile[] neighbors = new Tile[2];
//
//		for (int offset = 1; offset < 4; offset += 1) {
//			if ((index + offset) % 4 != 0 && (index + offset) < tiles.length) {
//				if (!tiles[index + offset].isEmpty()) {
//					neighbors[0] = tiles[index + offset];
//					break;
//				}
//			}
//		}
//
//		for (int offset = 4; offset < 16; offset += 4) {
//			if ((index + offset) < tiles.length) {
//				if (!tiles[index + offset].isEmpty()) {
//					neighbors[1] = tiles[index + offset];
//					break;
//				}
//			}
//		}

//		for (int offset = 1; offset < 4; offset += 1) {
//			if ((index - offset) % 4 != 0 && (index - offset) >= 0) {
//				if (!tiles[index - offset].isEmpty()) {
//					neighbors[2] = tiles[index - offset];
//					break;
//				}
//			}
//		}
//
//		for (int offset = 4; offset < 16; offset += 4) {
//			if ((index - offset) >= 0) {
//				if (!tiles[index - offset].isEmpty()) {
//					neighbors[3] = tiles[index - offset];
//					break;
//				}
//			}
//		}
//		return neighbors;
//	}
//
//	private double getMonotonicityHorizontal(Tile[] tiles) {
//		double[] totals = new double[2];
//		double currentValue, nextValue;
//
//		for (int y = 0; y < 4; y++) {
//			int current = 0;
//			int next = current + 1;
//			while (next < 4) {
//				while (next < 4 && tiles[next + 4 * y].isEmpty()) {
//					next++;
//				}
//				if (next >= 4) {
//					next--;
//				}
//				currentValue = tiles[current + 4 * y].getValue();
//				nextValue = tiles[next + 4 * y].getValue();
//				/* If the current is bigger, the order is decreasing.
//				 * Otherwise, it's increasing. */
//				if (currentValue > nextValue) {
//					totals[0] += nextValue - currentValue;
//				} else {
//					totals[1] += currentValue - nextValue;
//				}
//				current = next;
//				next++;
//			}
//		}
//
//		return Math.max(totals[0], totals[1]);
//	}
//
//	private Tile[] rotate(Tile[] tiles, int angle) {
//		Tile[] res = new Tile[16];
//
//		int offsetX = 3, offsetY = 3;
//		if (angle == 90) {
//			offsetY = 0;
//		} else if (angle == 270) {
//			offsetX = 0;
//		}
//
//		double rad = Math.toRadians(angle);
//		int cos = (int) Math.cos(rad);
//		int sin = (int) Math.sin(rad);
//		for (int x = 0; x < 4; x++) {
//			for (int y = 0; y < 4; y++) {
//				int newX = (x * cos) - (y * sin) + offsetX;
//				int newY = (x * sin) + (y * cos) + offsetY;
//				res[newX + newY * 4] = tiles[x + y * 4];
//			}
//		}
//		return res;
//	}

	private Grid cloneGrid(Grid grid) {
		Tile[] tiles = grid.getTiles();
		Grid res = new Grid(true);
		Tile[] newTiles = res.getTiles();

		for (int i = 0; i < tiles.length; i++) {
			newTiles[i] = new Tile(i, tiles[i].getValue());
		}
		res.updateHighestTile();
		return res;
	}
}
