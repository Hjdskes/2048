package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ButtonHandler;
import nl.tudelft.ti2206.handlers.PreferenceHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.Game;

/**
 * The TwentyFourtyGame is used to hook into LibGDX. It extends Game, which
 * implements LibGDX's ApplicationListener interface.
 * 
 * Through implementing this interface, we receive events from LibGDX. These
 * events are received in the Screen, for which we have written our own
 * GameScreen. In essence, GameScreen is our 'controller' class, because
 * TwentyFourtyGame delegates all events to it.
 * 
 * @author group-21
 */
public class TwentyFourtyGame extends Game {

	@Override
	public void create() {
		Networking.initalize();
		
//		Networking.startServer();
		Networking.startClient("192.168.1.225", 2526);
		
		
		AssetHandler.load();
		ButtonHandler.load();
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
