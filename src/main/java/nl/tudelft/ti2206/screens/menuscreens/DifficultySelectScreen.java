package nl.tudelft.ti2206.screens.menuscreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.screens.Screen;
import nl.tudelft.ti2206.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.screens.gamescreens.UserComputerScreen.Difficulty;
import nl.tudelft.ti2206.screens.overlays.CountDownScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The MenuScreen is used on start-up, to have the user choose between
 * singleplayer, hosting a game or connecting to another player.
 */
public class DifficultySelectScreen extends Screen {
	/** The main label. */
	private Label label;

	/** The button to launch a singleplayer game. */
	private TextButton easyButton, mediumButton, hardButton, extremeButton;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new MenuScreen. */
	public DifficultySelectScreen() {
		/*
		 * To resize the display when we get back to the menu from a multiplayer
		 * session.
		 */
		Gdx.graphics.setDisplayMode(TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();
		label = new Label("Select your difficulty!", assetHandler.getSkin());

		easyButton = new TextButton("Easy", assetHandler.getSkin());
		mediumButton = new TextButton("Medium", assetHandler.getSkin());
		hardButton = new TextButton("Hard", assetHandler.getSkin());
		extremeButton = new TextButton("Extreme", assetHandler.getSkin());

		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public DifficultySelectScreen(Stage stage, Label label, TextButton button) {
		this.stage = stage;
		this.label = label;

		this.easyButton = button;
		this.mediumButton = button;
		this.hardButton = button;
		this.extremeButton = button;

		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT / 2 + label.getHeight() + 10
				* TwentyFourtyGame.GAP);

		// position buttons:
		easyButton.setWidth(200);
		easyButton.setX(TwentyFourtyGame.GAME_WIDTH / 2 - easyButton.getWidth()
				/ 2);
		easyButton.setY(label.getY() - label.getHeight() - 6
				* TwentyFourtyGame.GAP);

		mediumButton.setWidth(200);
		mediumButton.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- mediumButton.getWidth() / 2);
		mediumButton.setY(easyButton.getY() - easyButton.getHeight() - 2
				* TwentyFourtyGame.GAP);

		hardButton.setWidth(200);
		hardButton.setX(TwentyFourtyGame.GAME_WIDTH / 2 - hardButton.getWidth()
				/ 2);
		hardButton.setY(mediumButton.getY() - easyButton.getHeight() - 2
				* TwentyFourtyGame.GAP);

		extremeButton.setWidth(200);
		extremeButton.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- extremeButton.getWidth() / 2);
		extremeButton.setY(hardButton.getY() - mediumButton.getHeight() - 2
				* TwentyFourtyGame.GAP);

		// add buttons to stage:
		stage.addActor(label);
		stage.addActor(easyButton);
		stage.addActor(mediumButton);
		stage.addActor(hardButton);
		stage.addActor(extremeButton);

		easyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new CountDownScreen(Difficulty.EASY));
			}
		});

		mediumButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new CountDownScreen(Difficulty.MEDIUM));
			}
		});

		hardButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new CountDownScreen(Difficulty.HARD));
			}
		});

		extremeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new CountDownScreen(Difficulty.EXTREME));
			}
		});
	}
}
