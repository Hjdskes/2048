package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;

public class ProgressHandler {
	/**
	 * Calls saveGrid to save the current grid and uses the PreferenceHandler to
	 * save the current score.
	 * 
	 * @param world
	 *            The GameWorld to save.
	 */
	public static void saveGame(GameWorld world) {
		saveGrid(world.getGrid());
		PreferenceHandler.setScore(world.getScore());
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
				state += index + "," + tiles[index].getValue() + ","
						+ tiles[index].isMerged() + "\n";
			}
		}

		PreferenceHandler.setGrid(state);
	}

	/**
	 * Loads the saved grid and score.
	 * 
	 * @param world
	 */
	public static void loadGame(GameWorld world) {
		world.setScore(PreferenceHandler.getScore());
		world.setGrid(loadGrid(world));
	}

	/**
	 * Loads the saved grid. If no grid is saved, returns a default grid.
	 * 
	 * @param world
	 * @return the loaded grid.
	 */
	private static Grid loadGrid(GameWorld world) {
		String filledTiles = PreferenceHandler.getGrid();
		/*
		 * If no grid is saved, return a default one. Else, fill the grid with
		 * the saved tiles.
		 */
		if (filledTiles == "") {
			return new Grid(world, false);
		} else {
			Grid grid = new Grid(world, true);
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
