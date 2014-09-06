package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.helpers.AssetLoader;
import nl.tudelft.ti2206.screens.GameScreen;

import com.badlogic.gdx.Game;

public class TwentyFourtyGame extends Game {
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
		AssetLoader.dispose();
	}
}
