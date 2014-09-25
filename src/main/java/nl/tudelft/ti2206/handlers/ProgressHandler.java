package nl.tudelft.ti2206.handlers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * The ProgressHandler is used to save the current game, or load the previously
 * saved game.
 */
public class ProgressHandler {

	/** A singleton reference to the this class. */
	private static ProgressHandler instance = new ProgressHandler();

	/** A PrefenceHanlder singleton instance. */
	private PreferenceHandler prefsHandler = PreferenceHandler.getInstance();

	/** Overrides the default constructor. */
	private ProgressHandler() {
	}

	public static ProgressHandler getInstance() {
		return instance;
	}

	/**
	 * Calls saveGrid to save the current grid and uses the PreferenceHandler to
	 * save the current score, high score and highest tile value ever reached.
	 * 
	 * @param grid
	 *            The Grid to save its' current state.
	 */
	public void saveGame(Grid grid) {
		int highest = grid.getCurrentHighestTile();
		int highscore = grid.getHighscore();
		int score = grid.getScore();

		saveGrid(grid);
		prefsHandler.setScore(score);

		if (highest > prefsHandler.getHighestTile()) {
			prefsHandler.setHighest(highest);
		}
		if (highscore > prefsHandler.getHighscore()) {
			prefsHandler.setHighscore(highscore);
		}

		/* Logging when the grid is saved */
		Gdx.app.log(
				this.getClass().getSimpleName(),
				"Saved the game with the grid: " + grid.toString()
						+ ". Highscore: "
						+ Integer.toString(prefsHandler.getHighscore())
						+ ". Highest tile: "
						+ Integer.toString(prefsHandler.getHighestTile())
						+ ". Score: "
						+ Integer.toString(prefsHandler.getScore()) + ".");
	}

	/**
	 * Loads the saved grid, score, high score and highest tile value ever
	 * reached.
	 */
	public Grid loadGame() {
		Grid grid = loadGrid();
		grid.setHighestTile(prefsHandler.getHighestTile());
		grid.setHighscore(prefsHandler.getHighscore());
		grid.setScore(prefsHandler.getScore());

		/* Logging when the grid is loaded */
		Gdx.app.log(
				this.getClass().getSimpleName(),
				"Loaded the game with the grid: " + grid.toString()
						+ ". Highscore: "
						+ Integer.toString(prefsHandler.getHighscore())
						+ ". Highest tile: "
						+ Integer.toString(prefsHandler.getHighestTile())
						+ ". Score: "
						+ Integer.toString(prefsHandler.getScore()) + ".");

		return grid;
	}

	/**
	 * Calls the prefsHandler to save the current grid.
	 * 
	 * @param grid
	 *            The grid to store.
	 */
	private void saveGrid(Grid grid) {
		String state = "";

		Tile[] tiles = grid.getTiles();
		for (int index = 0; index < tiles.length; index++) {
			if (!tiles[index].isEmpty()) {
				state += index + "," + tiles[index].getValue() + "\n";
			}
		}

		prefsHandler.setGrid(state);
	}

	/**
	 * Loads the saved grid. If no grid is saved, returns a default grid.
	 */
	private Grid loadGrid() {
		String filledTiles = prefsHandler.getGrid();
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
