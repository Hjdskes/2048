package nl.tudelft.ti2206.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * The PreferenceHandler is used to control the Preferences. These are used to
 * save the highscore and highest value ever reached.
 * 
 * @author group-21
 */
public class PreferenceHandler {
	private static Preferences prefs = Gdx.app.getPreferences("2048");

	public static Preferences getPrefs() {
		return prefs;
	}

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

	public static int getScore() {
		return prefs.getInteger("score");
	}

	public static void setScore(int score) {
		prefs.putInteger("score", score);
		prefs.flush();
	}

	public static int getHighscore() {
		return prefs.getInteger("highscore");
	}

	public static void setHighscore(int highscore) {
		prefs.putInteger("highscore", highscore);
		prefs.flush();
	}

	public static int getHighestTile() {
		return prefs.getInteger("highest");
	}

	public static void setHighest(int highest) {
		prefs.putInteger("highest", highest);
		prefs.flush();
	}

	public static String getGrid() {
		return prefs.getString("grid");
	}

	public static void setGrid(String grid) {
		prefs.putString("grid", grid);
		prefs.flush();
	}
}
