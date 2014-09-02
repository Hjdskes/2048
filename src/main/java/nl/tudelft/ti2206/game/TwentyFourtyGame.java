package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.screens.GameScreen;

import com.badlogic.gdx.Game;

public class TwentyFourtyGame extends Game {
	
	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
