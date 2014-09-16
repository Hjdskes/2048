package old.game;

import old.handlers.PreferenceHandler;

import com.badlogic.gdx.Game;

/**
 * The HeadlessTwentyFourtyGame is used to "mock" the real one,
 * as to start the game without a screen and without calling
 * AssetHandler.
 * 
 * @author group-21
 */
public class HeadlessTwentyFourtyGame extends Game {
	@Override
	public void create() {
		PreferenceHandler.initScores();
		setScreen(null);
	}
}
