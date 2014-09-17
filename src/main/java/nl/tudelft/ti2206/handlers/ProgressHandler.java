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
	 * save the current score, high score and highest tile value ever reached.
	 * 
	 * @param grid
	 *            The Grid to save its' current state.
	 */
	public static void saveGame(Grid grid) {
		int highest = grid.getCurrentHighestTile();
		int highscore = grid.getHighscore();
		int score = grid.getScore();

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
	 * Loads the saved grid, score, high score and highest tile value ever
	 * reached.
	 */
	public static Grid loadGame() {
		Grid grid = loadGrid();
		grid.setHighestTile(PreferenceHandler.getHighestTile());
		grid.setHighscore(PreferenceHandler.getHighscore());
		grid.setScore(PreferenceHandler.getScore());
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
	 * Loads the saved grid. If no grid is saved, returns a default grid.s
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
