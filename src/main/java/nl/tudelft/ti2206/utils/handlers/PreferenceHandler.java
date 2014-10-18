package nl.tudelft.ti2206.utils.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * The PreferenceHandler is used to control the Preferences. These are used to
 * save and load the highscore, the highest tile value ever reached and the
 * current game (the grid and the current score).
 */
public class PreferenceHandler {
	/** The unique singleton instance of this class. */
	private static PreferenceHandler instance = new PreferenceHandler();

	/** The Preferences object to save and load everything. */
	private static Preferences prefs = Gdx.app.getPreferences("2048");

	/** Overrides the default constructor. */
	private PreferenceHandler() { }

	/**
	 * @return The singleton instance of the PreferenceHandler
	 */
	public static PreferenceHandler getInstance() {
		return instance;
	}

	/**
	 * @return The Preferences object.
	 */
	public Preferences getPrefs() {
		return prefs;
	}

	/**
	 * Initializes the scores to zero in case these can not be found, does
	 * nothing otherwise.
	 */
	public void initScores() {
		if (!prefs.contains("score")) {
			prefs.putInteger("score", 0);
			prefs.flush();
		}
		if (!prefs.contains("highscore")) {
			prefs.putInteger("highscore", 0);
			prefs.flush();
		}
		if (!prefs.contains("highest")) {
			prefs.putInteger("highest", 1);
			prefs.flush();
		}
	}

	/**
	 * @return The score saved from the previous game.
	 */
	public int getScore() {
		return prefs.getInteger("score");
	}

	/**
	 * Saves its parameter as the score of the current game.
	 * 
	 * @param score
	 *            The value to save.
	 */
	public void setScore(int score) {
		prefs.putInteger("score", score);
		prefs.flush();
	}

	/**
	 * @return The saved highscore.
	 */
	public int getHighscore() {
		return prefs.getInteger("highscore");
	}

	/**
	 * Saves its parameter as the new highscore.
	 * 
	 * @param highscore
	 *            The value of the new highscore.
	 */
	public void setHighscore(int highscore) {
		prefs.putInteger("highscore", highscore);
		prefs.flush();
	}

	/**
	 * @return The value of the highest tile ever reached.
	 */
	public int getHighestTile() {
		return prefs.getInteger("highest");
	}

	/**
	 * Saves its parameter as the highest tile value ever reached.
	 * 
	 * @param highest
	 *            The value of the highest tile ever reached.
	 */
	public void setHighest(int highest) {
		prefs.putInteger("highest", highest);
		prefs.flush();
	}

	/**
	 * @return The grid saved from the previous game.
	 */
	public String getGrid() {
		return prefs.getString("grid");
	}

	/**
	 * Saves its parameter as the grid from the current game.
	 * 
	 * @param grid
	 *            The String indicating the contents of the grid.
	 */
	public void setGrid(String grid) {
		prefs.putString("grid", grid);
		prefs.flush();
	}
}
