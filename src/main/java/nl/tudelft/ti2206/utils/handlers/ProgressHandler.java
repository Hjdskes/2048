package nl.tudelft.ti2206.utils.handlers;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.log.Logger;

/**
 * The ProgressHandler is used to save the current game, or load the previously
 * saved game.
 */
public class ProgressHandler {
	/** The classname, used for logging output. */
	private static final String CLASSNAME = ProgressHandler.class
			.getSimpleName();

	/** A singleton reference to the this class. */
	private static ProgressHandler instance = new ProgressHandler();

	/** A PrefenceHanlder singleton instance. */
	private static PreferenceHandler prefsHandler = PreferenceHandler
			.getInstance();

	/** A singleton reference to the logger. */
	private static Logger logger = Logger.getInstance();

	/** Overrides the default constructor. */
	private ProgressHandler() {
	}

	/** Returns the singleton reference to this class. */
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
		logger.info(CLASSNAME, "Saving game to preference file...");

		int highest = grid.getCurrentHighestTile();
		int score = grid.getScore();

		if (!TwentyFourtyGame.isLost()) {
			prefsHandler.setGrid(grid.toString());
			prefsHandler.setScore(score);
		}

		if (highest > prefsHandler.getHighestTile()) {
			prefsHandler.setHighest(highest);
		}
		if (score > prefsHandler.getHighscore()) {
			prefsHandler.setHighscore(score);
		}

		logger.info(
				CLASSNAME,
				"Saved the games with the grid: " + grid.toString()
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
	 * 
	 * @return The loaded Grid.
	 */
	public Grid loadGame() {
		Grid grid = loadGrid();
		loadGame(grid);
		return grid;
	}

	/**
	 * Loads the saved grid, score, high score and highest tile value ever
	 * reached.
	 * 
	 * @return The loaded Grid.
	 */
	public Grid loadGame(Grid grid) {
		logger.info(CLASSNAME, "Loading game from preference file...");

		grid.setSpawner(prefsHandler.getSpawner());
		grid.setScore(prefsHandler.getScore());
		grid.updateHighestTile();

		logger.info(
				CLASSNAME,
				"Game has been loaded: " + grid.toString() + ". Highscore: "
						+ Integer.toString(prefsHandler.getHighscore())
						+ ". Highest tile: "
						+ Integer.toString(prefsHandler.getHighestTile())
						+ ". Score: "
						+ Integer.toString(prefsHandler.getScore()) + ".");

		return grid;
	}

	/**
	 * Loads the saved grid. If no grid is saved, returns a default grid.
	 */
	private Grid loadGrid() {
		logger.info(CLASSNAME, "Loading saved grid...");
		String filledTiles = prefsHandler.getGrid();
		/*
		 * If no grid is saved, return a default one. Else, fill the grid with
		 * the saved tiles.
		 */
		if (filledTiles.equals("")) {
			return new Grid(false);
		} else {
			String[] split = filledTiles.split(",");
			if (split.length != 16) {
				return new Grid(false);
			}

			Grid grid = new Grid(true);
			for (int i = 0; i < split.length; i++) {
				int value = Integer.parseInt(split[i]);
				grid.setTile(i, value < 0 ? 0 : value);
			}
			return grid;
		}
	}
}
