package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.screens.UserComputerScreen.Difficulty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * The MenuScreen is used on start-up, to have the user choose between
 * singleplayer, hosting a game or connecting to another player.
 */
public class CountDownScreen extends Screen {
	/** The background images. */
	private Image getready, set, go;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	private long initTime;
	
	private Difficulty difficulty;

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new MenuScreen. */
	public CountDownScreen(Difficulty difficulty) {
		
		initTime = System.currentTimeMillis();
		/*
		 * To resize the display when we get back to the menu from a multiplayer
		 * session.
		 */
		Gdx.graphics.setDisplayMode(TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();
		getready = new Image(assetHandler.getSkin(), "getreadyoverlay");
		set = new Image(assetHandler.getSkin(), "setoverlay");
		go = new Image(assetHandler.getSkin(), "gooverlay");

		this.difficulty = difficulty;
		
		this.setDrawBehavior(new SimpleDraw(stage));
	}

	/** Constructor for testing purposes only. */
	public CountDownScreen(Stage stage, Label label, TextButton button) {
		this.stage = stage;
		this.setDrawBehavior(new SimpleDraw(stage));
	}

	@Override
	public void create() {
		super.create();

		stage.addActor(getready);
	}

	@Override
	public void update() {
		super.update();

		long endTime = System.currentTimeMillis();
		long seconds = (endTime - initTime) / 1000;
		
		int offset = 2;

		if (seconds >= offset + 2.2) {
			stage.clear();
			screenHandler.add(new UserComputerScreen(difficulty));
		}
		else if (seconds >= offset + 2) {
			stage.clear();
			stage.addActor(go);
		} 
		else if (seconds >= offset) {
			stage.clear();
			stage.addActor(set);
		}
	}
}
