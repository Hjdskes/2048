package nl.tudelft.ti2206.graphics.screens.overlays;

import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawSimple;
import nl.tudelft.ti2206.graphics.screens.gamescreens.UserComputerScreen;
import nl.tudelft.ti2206.graphics.screens.gamescreens.UserComputerScreen.Difficulty;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The MenuScreen is used on start-up, to have the user choose between
 * singleplayer, hosting a game or connecting to another player.
 */
public class CountDownScreen extends Screen {
	/** The background images. */
	private Image getReady, set, go;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The system time at initialization. */
	private long initTime;

	/** The difficulty the solver should use. */
	private Difficulty difficulty;

	/** The singleton reference to the ScreenHandler class. */
	private ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new MenuScreen. */
	public CountDownScreen(Difficulty difficulty) {
		this.initTime = System.currentTimeMillis();
		this.stage = new Stage();
		this.getReady = new Image(assetHandler.getSkin(), "getreadyoverlay");
		this.set = new Image(assetHandler.getSkin(), "setoverlay");
		this.go = new Image(assetHandler.getSkin(), "gooverlay");

		this.difficulty = difficulty;
		this.setDrawBehavior(new DrawSimple(stage));
	}

	/** Constructor for testing purposes only. */
	public CountDownScreen(long initTime, Stage stage, Image image) {
		this.initTime = initTime;
		this.stage = stage;
		this.getReady = this.set = this.go = image;
		this.setDrawBehavior(new DrawSimple(stage));
	}

	@Override
	public void create() {
		super.create();
		stage.addActor(getReady);
	}

	@Override
	public void update() {
		super.update();

		long endTime = System.currentTimeMillis();
		long seconds = (endTime - initTime) / 1000;
		
		int offset = 2;

		if (seconds >= offset + 2.2) {
			stage.clear();
			screenHandler.set(new UserComputerScreen(difficulty));
		} else if (seconds >= offset + 2) {
			stage.clear();
			stage.addActor(go);
		} else if (seconds >= offset) {
			stage.clear();
			stage.addActor(set);
		}
	}
}
