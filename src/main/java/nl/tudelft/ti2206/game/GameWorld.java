package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.helpers.AssetHandler;
import nl.tudelft.ti2206.helpers.PreferenceHandler;
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
	public enum GameState { RUNNING, WON, LOST }
	/** The current game score. */
	private int score;
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
			state = GameState.RUNNING;
		}
	}

	/**
	 * Update the game objects. This method will be called every frame.
	 * 
	 * @param delta
	 *            Time in milliseconds to update.
	 */
	public void update(float delta) {
		/* Add delta cap so if the game takes too long to update, it will still
		 * work. */
		if (delta > .15f) {
			delta = .15f;
		}

		/* Tell the grid to update its objects. */
		grid.update();

		/* Update the highscore, but only if the new score is higher than
		 * the old. */
		if (PreferenceHandler.getHighscore() < getScore()) {
			PreferenceHandler.setHighscore(getScore());
		}

		/* Update the highest value, but only if the new value is higher
		 * than the old. */
		if (PreferenceHandler.getHighest() < grid.getHighest()) {
			PreferenceHandler.setHighest(grid.getHighest());
		}

		/* Check if 2048 has been reached, in which case the player wins, or if
		 * the grid is full and no more moves are possible, in which case the
		 * player loses. */
		if (grid.getHighest() == 2048) {
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
	 * Restarts the game.
	 */
	public void restart() {
		score = 0;
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
	 * Adds points to current game's score
	 * 
	 * @param increment
	 *            The value to add.
	 */
	public void addScore(int increment) {
		score += increment;
	}
}
