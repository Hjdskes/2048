package nl.tudelft.ti2206.utils.ai.spawners;

import java.util.List;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * The AnnoyingSpawner spawns tiles in the most annoying location for the
 * current grid. It does this by evaluating the Grid per empty tile, placing
 * a 2 and a 4 four in it, based on:
 * 
 * 1) The amount of islands; where each island is a group of connected tiles with the
 * same value.
 * 2) The discontinuity; this value is the difference in value between a
 * Tile and its neighbor. The higher the discontinuity, the more annoying this
 * location is.
 */
public class AnnoyingSpawner implements Spawner {
	/** The unique singleton instance of this class. */
	private static AnnoyingSpawner instance = new AnnoyingSpawner();

	/** The values that can possibly spawn. */
	private static int[] possibleValues = { 1, 2 };

	/** Overrides the default constructor. */
	private AnnoyingSpawner() {
	}

	/**
	 * @return The singleton instance of the AnnoyingSpawner.
	 */
	public static AnnoyingSpawner getInstance() {
		return instance;
	}

	/**
	 * @return A tile on the most annoying place for the current game, with the
	 *         most annoying value.
	 */
	@Override
	public Tile findTile(Grid grid) {
		int bestValue = 1, bestScore = 0, bestLocation = 0;
		Tile[] tiles = grid.getTiles();

		for (Tile tile : tiles) {
			if (!tile.isEmpty()) {
				continue;
			}

			for (int i = 0; i < possibleValues.length; i++) {
				tile.setValue(possibleValues[i]);

				int curScore = getIslands(grid)
						+ discontinuity(grid, tile.getIndex());
				if (curScore > bestScore) {
					bestScore = curScore;
					bestLocation = tile.getIndex();
					bestValue = possibleValues[i];
				}

				tile.setValue(0);
			}
		}

		return new Tile(bestLocation, bestValue);
	}

	/**
	 * @param grid
	 *            The Grid to calculate the discontinuity on.
	 * @param index
	 *            The Tile whose neighbors to check.
	 * @return The maximum difference in value between a Tile and its neighbors.
	 *         The higher the discontinuity, the more annoying this location is.
	 */
	private int discontinuity(Grid grid, int index) {
		int maxNeighbor = 0;

		List<Tile> neighbors = grid.getTileNeighbors(index);
		for (Tile neighbor : neighbors) {
			int nValue = neighbor.getValue();
			if (nValue > maxNeighbor) {
				maxNeighbor = nValue;
			}
		}
		return maxNeighbor;
	}

	/**
	 * Marks a Tile and its neighbors, so that they will be skipped when
	 * counting islands.
	 * 
	 * @param grid
	 *            The Grid calculating the islands on.
	 * @param marked
	 *            The array of marked tiles.
	 * @param index
	 *            The index from the starting Tile in the group.
	 * @param value
	 *            The value, so we only count tiles with the same values.
	 */
	private void mark(Grid grid, boolean[] marked, int index, int value) {
		Tile[] tiles = grid.getTiles();
		if (!tiles[index].isEmpty() && tiles[index].getValue() == value
				&& !marked[index]) {
			marked[index] = true;

			List<Tile> neighbors = grid.getTileNeighbors(index);
			for (Tile neighbor : neighbors) {
				mark(grid, marked, neighbor.getIndex(), value);
			}
		}
	}

	/**
	 * Taken from {@link https://github.com/ov3y/2048-AI/blob/master/js/grid.js#L329}
	 * 
	 * @param grid
	 *            The Grid to calculate the islands on.
	 * @return The amount of disconnected groups of tiles of the same value. For
	 *         example, a grid looking like this has four islands:
	 * 
	 *         +---+---+---+---+
	 *         |   |   | 2 | 2 |
	 *         +---+---+---+---+
	 *         |   |   | 2 | 4 |
	 *         +---+---+---+---+
	 *         |   |   | 4 | 4 |
	 *         +---+---+---+---+
	 *         |   |   |   | 8 |
	 *         +---+---+---+---+
	 * 
	 *         All twos form one island, the horizontally connected fours form
	 *         one, the vertically connected fours from another one and the
	 *         eight forms one by itself.
	 */
	private int getIslands(Grid grid) {
		int islands = 0;
		Tile[] tiles = grid.getTiles();
		boolean[] marked = new boolean[16];

		for (int i = 0; i < tiles.length; i++) {
			if (!tiles[i].isEmpty() && !marked[i]) {
				islands++;
				mark(grid, marked, i, tiles[i].getValue());
			}
		}
		return islands;
	}
}
