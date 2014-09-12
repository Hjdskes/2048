package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.helpers.AssetHandler;
import nl.tudelft.ti2206.helpers.ProgressHandler;

/**
 * This is simply a helper class to update our game objects.
 * 
 * It is created and called from GameScreen, in its render method which is
 * actually the game loop.
 * 
 * @author group-21
 */
public class GameWorld {
	/** Enumeration indicating what state the game is currently in. */
	public enum GameState {
		RUNNING, WON, LOST, CONTINUING
	}

	/** The current game score. */
	private int score;
	/** The highscore at the time of launching the game. */
	private int oldHighscore;
	/** The highest value at the time of launching the game. */
	private int oldHighestTile;
	/** The current grid in the game. */
	private Grid grid;
	/** The state the game is currently in. */
	private GameState state;

	/**
	 * Constructor for GameWorld object, creating the grid.
	 */
	public GameWorld() {
		if (AssetHandler.isLibraryInitialized()) {
			/* Set the old, saved grid and score. */
			ProgressHandler.loadGame(this);
		} else {
			/* Create a new game. */
			grid = new Grid(this, false);
			score = 0;
		}
		state = GameState.RUNNING;
	}

	/**
	 * Update the game objects. This method will be called every frame.
	 * 
	 * @param delta
	 *            Time in milliseconds to update.
	 */
	public void update(float delta) {
		/*
		 * Add delta cap so if the game takes too long to update, it will still
		 * work.
		 */
		delta = Math.max(delta, .15f);

		/* Tell the grid to update its objects. */
		grid.update();

		if (grid.getHighestTile() == 2048 && !isContinuing() && !isLost()) {
			setGameState(GameState.WON);
		} else if (grid.isFull() && grid.getPossibleMoves() == 0) {
			setGameState(GameState.LOST);
		}
	}

	/**
	 * Sets the current game state.
	 * 
	 * @param state
	 *            The new game state.
	 */
	public void setGameState(GameState state) {
		this.state = state;
	}

	/**
	 * Returns the current game state.
	 * 
	 * @return The current game state.
	 */
	public GameState getGameState() {
		return state;
	}

	/**
	 * Returns true if the game is currently running.
	 * 
	 * @return True if the game is currently running.
	 */
	public boolean isRunning() {
		return (state == GameState.RUNNING);
	}

	/**
	 * Returns true if the current game is lost.
	 * 
	 * @return True if the current game is lost.
	 */
	public boolean isLost() {
		return (state == GameState.LOST);
	}

	/**
	 * Returns true if the current game is won.
	 * 
	 * @return True if the current game is won.
	 */
	public boolean isWon() {
		return (state == GameState.WON);
	}

	/**
	 * Returns true if the current game is in continuing state.
	 * 
	 * @return True if game is in continuing state.
	 */
	public boolean isContinuing() {
		return (state == GameState.CONTINUING);
	}

	/**
	 * Restarts the game.
	 */
	public void restart() {
		state = GameState.RUNNING;
		score = 0;
		oldHighestTile = 0;
		grid.restart();
	}

	/**
	 * Returns the current game score.
	 * 
	 * @return The current score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Returns the current highscore.
	 * 
	 * @return The current highscore.
	 */
	public int getHighscore() {
		return oldHighscore > score ? oldHighscore : score;
	}

	/**
	 * Returns the currently highest value.
	 * 
	 * @return The currently highest value.
	 */
	public int getHighestTile() {
		return oldHighestTile > grid.getHighestTile() ? oldHighestTile : grid
				.getHighestTile();
	}

	/**
	 * Returns the current game grid.
	 * 
	 * @return The current game grid.
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Sets the current game grid.
	 * 
	 * @param grid
	 *            The new grid.
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Sets the current game's score.
	 * 
	 * @param score
	 *            The score to set.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Sets the highest value at the time of launch.
	 * 
	 * @param oldHighest
	 *            The highest value at the time of launch.
	 */
	public void setOldHighest(int oldHighest) {
		this.oldHighestTile = oldHighest;
	}

	/**
	 * Sets the highscore at the time of launch.
	 * 
	 * @param oldHighestTile
	 *            The highscore at the time of launch.
	 */
	public void setOldHighscore(int oldHighscore) {
		this.oldHighscore = oldHighscore;
	}

	/**
	 * Adds points to current game's score
	 * 
	 * @param increment
	 *            The value to add.
	 */
	public void addScore(int increment) {
		score += increment;
	}
}
