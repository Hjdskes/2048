package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;

/**
 * The ProgressHandler is used to save the current game, or load the previously
 * saved game.
 */
public class ProgressHandler {

	/** A singleton reference to the this class. */
	private static ProgressHandler instance = new ProgressHandler();

	/** A PrefenceHanlder singleton instance. */
	private PreferenceHandler prefsHandler = PreferenceHandler.getInstance();

	private String className = this.getClass().getSimpleName();

	private Logger logger = Logger.getInstance();	
	
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
		logger.info(className, "Saving game to preference file...");
		
		int highest = grid.getCurrentHighestTile();
		int highscore = grid.getHighscore();
		int score = grid.getScore();

		prefsHandler.setGrid(grid.toString());
		prefsHandler.setScore(score);

		if (highest > prefsHandler.getHighestTile()) {
			prefsHandler.setHighest(highest);
		}
		if (highscore > prefsHandler.getHighscore()) {
			prefsHandler.setHighscore(highscore);
		}

		logger.info(className,
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
		logger.info(className, "Loading game from preference file...");
		
		Grid grid = loadGrid();
		grid.setHighestTile(prefsHandler.getHighestTile());
		grid.setHighscore(prefsHandler.getHighscore());
		grid.setScore(prefsHandler.getScore());

		logger.info(className,
				"Game has been loaded: " + grid.toString()
						+ ". Highscore: "
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
		logger.info(className, "Loading saved grid...");
		String filledTiles = prefsHandler.getGrid();
		/*
		 * If no grid is saved, return a default one. Else, fill the grid with
		 * the saved tiles.
		 */
		if (filledTiles == "") {
			return new Grid(false);
		} else {
			Grid grid = new Grid(true);
			String[] split = filledTiles.split(",");

			if (split.length != 16) {
				return grid;
			}

			for (int i = 0; i < split.length; i++) {
				int value = Integer.parseInt(split[i]);
				grid.setTile(i, value % 2 == 0 ? value : 0);
			}
			return grid;
		}
	}
}
