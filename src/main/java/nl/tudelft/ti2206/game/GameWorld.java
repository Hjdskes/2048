package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * GameWorld object.
 *
 * @author group-21
 *
 */
public class GameWorld {

	/**
	 * Current game score.
	 */
	private int score;

	/**
	 * Current game grid.
	 */
	private Grid grid;

	/**
	 * Constructor for GameWorld object, creating the grid.
	 *
	 * @param gameWidth
	 *            the width of the board
	 * @param gameHeight
	 *            the height of the board
	 */
	public GameWorld(final int gameWidth, final int gameHeight) {
		score = 0;
		grid = new Grid();
	}

	/**
	 * Update the game, should be called every frame.
	 *
	 * @param delta
	 *            time in milliseconds to update
	 */
	public void update(float delta) {
		// add delta cap so if the game takes too long to update,
		// it will still work
		if (delta > .15f)
			delta = .15f;

		grid.update(delta);
	}

	/**
	 * Restart the game.
	 */
	public void restart() {
		score = 0;
		grid.onRestart();
	}

	/**
	 * Get current game score.
	 *
	 * @return current score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Get the game grid.
	 * 
	 * @return game grid
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Set current game's score to amount of points.
	 * 
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Add points to current game's score
	 * 
	 * @param increment
	 */
	public void addScore(int increment) {
		score += increment;
	}
}
