package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.AnimatedGrid;
import nl.tudelft.ti2206.gameobjects.AnimatedTile;

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
	public static void saveGame(GameWorld world) {
		AnimatedGrid grid = world.getGrid();
		int highest = world.getHighestTile();
		int highscore = world.getHighscore();
		int score = world.getScore();

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
	public static void loadGame(GameWorld world) {
		world.setScore(PreferenceHandler.getScore());
		world.setOldHighest(PreferenceHandler.getHighestTile());
		world.setOldHighscore(PreferenceHandler.getHighscore());
		world.setGrid(loadGrid(world));
	}

	/**
	 * Calls the PreferenceHandler to save the current grid.
	 * 
	 * @param grid
	 *            The grid to store.
	 */
	private static void saveGrid(AnimatedGrid grid) {
		String state = "";

		AnimatedTile[] tiles = grid.getTiles();
		for (int index = 0; index < tiles.length; index++) {
			if (!tiles[index].isEmpty()) {
				state += index + "," + tiles[index].getValue() + ","
						+ tiles[index].isMerged() + "\n";
			}
		}

		PreferenceHandler.setGrid(state);
	}

	/**
	 * Loads the saved grid. If no grid is saved, returns a default grid.
	 * 
	 * @param world
	 *            The World to load the AnimatedGrid into.
	 * @return The loaded grid.
	 */
	private static AnimatedGrid loadGrid(GameWorld world) {
		String filledTiles = PreferenceHandler.getGrid();
		/*
		 * If no grid is saved, return a default one. Else, fill the grid with
		 * the saved tiles.
		 */
		if (filledTiles == "") {
			return new AnimatedGrid(world, false);
		} else {
			AnimatedGrid grid = new AnimatedGrid(world, true);
			String[] split = filledTiles.split("\n");

			for (String tile : split) {
				String[] tileInfo = tile.split(",");
				grid.setTile(Integer.parseInt(tileInfo[0]),
						Integer.parseInt(tileInfo[1]),
						Boolean.getBoolean(tileInfo[2]));
			}
			return grid;
		}
	}
}
