package nl.tudelft.ti2206.game;

import com.badlogic.gdx.Preferences;

import nl.tudelft.ti2206.gameobjects.AnimatedGrid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.PreferenceHandler;
import nl.tudelft.ti2206.handlers.ProgressHandler;
import nl.tudelft.ti2206.net.Networking;

/**
 * This is simply a helper class to manage our game objects in a central place.
 * 
 * It is created from GameScreen and gets updated through the
 * GameScreen.render() method which is actually the game loop.
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
	/** The highest tile value ever reached at the time of launching the game. */
	private int oldHighestTile;
	/** The current grid in the game. */
	private AnimatedGrid grid;
	/** The state the game is currently in. */
	private GameState state;

	/**
	 * Constructor for GameWorld object. If there is a saved game present it
	 * will be loaded, otherwise a new game will be created.
	 */
	public GameWorld() {
		if (AssetHandler.isLibraryInitialized()) {
			/* Set the old, saved grid and score. */
			ProgressHandler.loadGame(this);
		} else {
			/* Create a new game. */
			grid = new AnimatedGrid(this, false);
			score = 0;
		}
		state = GameState.RUNNING;
	}

	/**
	 * Updates the game objects. This method will be called every render.
	 * 
	 * @param delta
	 *            The time in milliseconds since the last render.
	 */
	public void update() {
		/* Tell the grid to update its objects. */
		grid.update();

		
		
		/*
		 * If the current highest tile is of value 2048 and we haven't already
		 * won nor already lost, then we have won. Otherwise, if there are no
		 * possible moves remaining, we lost.
		 */
		
		Networking.send("TILE:" + grid.getCurrentHighestTile());
		
		if (grid.getCurrentHighestTile() == 2048 && !isContinuing()) {
			setGameState(GameState.WON);
		} else if (grid.isFull() && grid.getPossibleMoves() == 0) {
			setGameState(GameState.LOST);
			Networking.send("LOST:0");
		}
		
		if (Networking.getOpponentHighestTile() == 2048) {
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

		if (score > oldHighscore) {
			oldHighscore = score;
		}

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
	 * Sets the current game's score.
	 * 
	 * @param score
	 *            The score to set.
	 */
	public void setScore(int score) {
		this.score = score;
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
		return oldHighestTile > grid.getCurrentHighestTile() ? oldHighestTile
				: grid.getCurrentHighestTile();
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
	 * Sets the highest tile value ever reached at the time of launching the
	 * game.
	 * 
	 * @param oldHighest
	 *            The highest tile value ever reached at the time of launching
	 *            the game.
	 */
	public void setOldHighest(int oldHighest) {
		this.oldHighestTile = oldHighest;
	}

	/**
	 * Returns the current game's grid.
	 * 
	 * @return The current game's grid.
	 */
	public AnimatedGrid getGrid() {
		return grid;
	}

	/**
	 * Sets the current game's grid.
	 * 
	 * @param grid
	 *            The grid to set.
	 */
	public void setGrid(AnimatedGrid grid) {
		this.grid = grid;
	}

	/**
	 * Adds points to current game's score.
	 * 
	 * @param increment
	 *            The value to add.
	 */
	public void addScore(int increment) {
		score += increment;
	}
}
