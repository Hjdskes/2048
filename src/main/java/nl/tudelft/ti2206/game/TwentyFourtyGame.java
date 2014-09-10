package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.helpers.AssetHandler;
import nl.tudelft.ti2206.helpers.PreferenceHandler;

import com.badlogic.gdx.Game;

public class TwentyFourtyGame extends Game {
	@Override
	public void create() {
		AssetHandler.load();
		PreferenceHandler.initScores();
		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
		AssetHandler.dispose();
	}
}
