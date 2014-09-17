package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * The ProgressHandler is used to save the current game, or load the previously
 * saved game.
 * 
 * @author group-21
 */
public class ProgressHandler {
	/**
	 * Calls saveGrid to save the current grid and uses the PreferenceHandler to
	 * save the current score, highscore and highest tile value ever reached.
	 * 
	 * @param world
	 *            The GameWorld to save its' grid, score, highscore and highest
	 *            tile value ever reached.
	 */
	public static void saveGame(Grid grid) {
		int highest = grid.getCurrentHighestTile();
		// FIXME: use highscore and score instead of highest
		int highscore = grid.getCurrentHighestTile();
		int score = grid.getCurrentHighestTile();

		saveGrid(grid);
		PreferenceHandler.setScore(score);

		if (highest > PreferenceHandler.getHighestTile()) {
			PreferenceHandler.setHighest(highest);
		}
		if (highscore > PreferenceHandler.getHighscore()) {
			PreferenceHandler.setHighscore(highscore);
		}
	}

	/**
	 * Loads the saved grid, score, highscore and highest tile value ever
	 * reached.
	 * 
	 * @param world
	 *            The world to load all the values into.
	 */
	public static Grid loadGame() {
		// FIXME: use setHighscore and setScore
		// grid.setScore(PreferenceHandler.getScore());
		// world.setOldHighest(PreferenceHandler.getHighestTile());
		// world.setOldHighscore(PreferenceHandler.getHighscore());
		Grid grid = loadGrid();
		grid.setHighestTile(PreferenceHandler.getHighestTile());
		return grid;
	}

	/**
	 * Calls the PreferenceHandler to save the current grid.
	 * 
	 * @param grid
	 *            The grid to store.
	 */
	private static void saveGrid(Grid grid) {
		String state = "";

		Tile[] tiles = grid.getTiles();
		for (int index = 0; index < tiles.length; index++) {
			if (!tiles[index].isEmpty()) {
				state += index + "," + tiles[index].getValue() + "\n";
			}
		}

		PreferenceHandler.setGrid(state);
	}

	/**
	 * Loads the saved grid. If no grid is saved, returns a default grid.
	 * 
	 * @param world
	 *            The World to load the AnimatedGrid into.
	 */
	private static Grid loadGrid() {
		String filledTiles = PreferenceHandler.getGrid();
		/*
		 * If no grid is saved, return a default one. Else, fill the grid with
		 * the saved tiles.
		 */
		if (filledTiles == "") {
			return new Grid(false);
		} else {
			Grid grid = new Grid(true);
			String[] split = filledTiles.split("\n");

			for (String tile : split) {
				String[] tileInfo = tile.split(",");
				grid.setTile(Integer.parseInt(tileInfo[0]),
						Integer.parseInt(tileInfo[1]));
			}
			return grid;
		}
	}
}
