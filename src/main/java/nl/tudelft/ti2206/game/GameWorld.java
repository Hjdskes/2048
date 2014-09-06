package nl.tudelft.ti2206.game;

import java.util.List;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.helpers.AssetLoader;

/**
 * GameWorld object.
 * 
 * @author group-21
 * 
 */
public class GameWorld {

	public enum GameState {
		RUNNING, WON, LOST
	}

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
		// set the grid and score.
		AssetLoader.loadGame(this);
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

		// check if 2048 has been reached (player wins)
		if (grid.getHighest() == 2048)
			setGameState(GameState.WON);
		// check if grid is full and if no more moves are possible (player loses)
		else if (grid.isFull() && grid.getPossibleMoves() == 0)
			setGameState(GameState.LOST);
		
		System.out.println("DEBUG: moves possible = " + grid.getPossibleMoves());

		List<Tile> neighbours = grid.getTileNeighbours(12);
		for (Tile neighbour : neighbours) {
			System.out.println("neighbour value = " + neighbour.getValue());
		}

	}

	public void setGameState(GameState state) {
		this.state = state;
	}

	public GameState getGameState() {
		return state;
	}

	public boolean isRunning() {
		return (state == GameState.RUNNING);
	}

	public boolean isLost() {
		return (state == GameState.LOST);
	}

	public boolean isWon() {
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

	public void setGrid(Grid grid) {
		this.grid = grid;
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
