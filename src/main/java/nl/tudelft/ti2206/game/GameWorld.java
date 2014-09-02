package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;

public class GameWorld {

	private int score;
	private Grid grid;

	public GameWorld(int gameWidth, int gameHeight) {
		score = 0;
		grid = new Grid();
	}

	public void update(float delta) {	
		// add delta cap so if the game takes too long to update,
		// it will still work
		if (delta > .15f)
			delta = .15f;

		grid.update(delta);
	}
	
	public void restart() {
		score = 0;
		grid.onRestart();
	}
	
	public int getScore() {
		return score;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public void setScore(int score) {
		this.score += score;
	}
	
	public void addScore(int increment) {
		score += increment;
	}
}
