package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.helpers.AssetLoader;

/**
 * GameWorld object.
 *
 * @author group-21
 *
 */
public class GameWorld {

	public enum GameState { RUNNING, WON, LOST }
	
	/**
	 * Current game score.
	 */
	private int score;

	/**
	 * Current game grid.
	 */
	private Grid grid;

	private GameState state;

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
		grid = new Grid(this);
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
		
		
		
		if (AssetLoader.getHighscore() < getScore())
			AssetLoader.setHighscore(getScore());
		
		// save highscore
		if (AssetLoader.getHighest() < grid.getHighest())
			AssetLoader.setHighest(grid.getHighest());
		
		if (grid.getHighest() == 2048) // player wins
			setGameState(GameState.WON);
		else if (grid.isFull()) // player loses
			setGameState(GameState.LOST);
	}

	
	public void setGameState(GameState state)
	{
		this.state = state;
	}
	
	public GameState getGameState()
	{
		return state;
	}
	
	public boolean isRunning()
	{
		return (state == GameState.RUNNING);
	}
	
	public boolean isLost()
	{
		return (state == GameState.LOST);
	}
	
	public boolean isWon()
	{
		return (state == GameState.WON);
	}
	/**
	 * Restart the game.
	 */
	public void restart() {
		score = 0;
		grid.restart();
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
