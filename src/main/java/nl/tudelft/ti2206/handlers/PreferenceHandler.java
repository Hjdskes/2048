package nl.tudelft.ti2206.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * The PreferenceHandler is used to control the Preferences. These are used to
 * save and load the highscore, the highest tile value ever reached and the
 * current game (the grid and the current score).
 * 
 * @author group-21
 */
public class PreferenceHandler {
	/** The Preferences object to save and load everything. */
	private static Preferences prefs = Gdx.app.getPreferences("2048");

	/**
	 * Returns the Preferences object.
	 * 
	 * @return The Preferences object.
	 */
	public static Preferences getPrefs() {
		return prefs;
	}

	/**
	 * Initializes the scores to zero in case these can not be found, does
	 * nothing otherwise.
	 */
	public static void initScores() {
		if (!prefs.contains("score")) {
			prefs.putInteger("score", 0);
			prefs.flush();
		}
		if (!prefs.contains("highscore")) {
			prefs.putInteger("highscore", 0);
			prefs.flush();
		}
		if (!prefs.contains("highest")) {
			prefs.putInteger("highest", 0);
			prefs.flush();
		}
	}

	/**
	 * Returns the score saved from the previous game.
	 * 
	 * @return The score saved from the previous game.
	 */
	public static int getScore() {
		return prefs.getInteger("score");
	}

	/**
	 * Saves its parameter as the score of the current game.
	 * 
	 * @param score
	 *            The value to save.
	 */
	public static void setScore(int score) {
		prefs.putInteger("score", score);
		prefs.flush();
	}

	/**
	 * Returns the highscore.
	 * 
	 * @return The highscore.
	 */
	public static int getHighscore() {
		return prefs.getInteger("highscore");
	}

	/**
	 * Saves its parameter as the new highscore.
	 * 
	 * @param highscore
	 *            The value of the new highscore.
	 */
	public static void setHighscore(int highscore) {
		prefs.putInteger("highscore", highscore);
		prefs.flush();
	}

	/**
	 * Returns the value of the highest tile ever reached.
	 * 
	 * @return The value of the highest tile ever reached.
	 */
	public static int getHighestTile() {
		return prefs.getInteger("highest");
	}

	/**
	 * Saves its parameter as the highest tile value ever reached.
	 * 
	 * @param highest The value of the highest tile ever reached.
	 */
	public static void setHighest(int highest) {
		prefs.putInteger("highest", highest);
		prefs.flush();
	}

	/**
	 * Returns the grid saved from the previous game.
	 * @return The grid saved from the previous game.
	 */
	public static String getGrid() {
		return prefs.getString("grid");
	}

	/**
	 * Saves its parameter as the grid from the current game.
	 * @param grid The String indicating the contents of the grid.
	 */
	public static void setGrid(String grid) {
		prefs.putString("grid", grid);
		prefs.flush();
	}
}
