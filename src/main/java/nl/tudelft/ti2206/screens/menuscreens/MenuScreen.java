package nl.tudelft.ti2206.screens.menuscreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.screens.Screen;
import nl.tudelft.ti2206.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.screens.gamescreens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The MenuScreen is used on start-up, to have the user choose between
 * singleplayer, hosting a game or connecting to another player.
 */
public class MenuScreen extends Screen {
	/** The main label. */
	private Label label;

	/** The button to launch a singleplayer game. */
	private TextButton singlePlayer;

	/** The button to go to the multiplayer menu. */
	private TextButton multiPlayer;

	/** The button to launch a player versus computer game. */
	private TextButton versusComputer;
	
	/** The button to go to the settings menu. */
	private ImageButton settings;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new MenuScreen. */
	public MenuScreen() {
		/*
		 * To resize the display when we get back to the menu from a multiplayer
		 * session.
		 */
		Gdx.graphics.setDisplayMode(TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();
		label = new Label("Choose your destiny!", assetHandler.getSkin());
		singlePlayer = new TextButton("Singleplayer", assetHandler.getSkin());
		multiPlayer = new TextButton("Multiplayer", assetHandler.getSkin());
		versusComputer = new TextButton("Challenge me!",
				assetHandler.getSkin());
		settings = new ImageButton(assetHandler.getSkin().getDrawable("settings"));
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public MenuScreen(Stage stage, Label label, TextButton button, ImageButton settings) {
		this.stage = stage;
		this.label = label;
		this.singlePlayer = button;
		this.versusComputer = button;
		this.multiPlayer = button;
		this.settings = settings;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT / 2 + label.getHeight() + 8
				* TwentyFourtyGame.GAP);

		singlePlayer.setWidth(220);
		singlePlayer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- singlePlayer.getWidth() / 2);
		singlePlayer.setY(label.getY() - label.getHeight() - 6
				* TwentyFourtyGame.GAP);

		multiPlayer.setWidth(220);
		multiPlayer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- multiPlayer.getWidth() / 2);
		multiPlayer.setY(singlePlayer.getY() - singlePlayer.getHeight() - 2
				* TwentyFourtyGame.GAP);

		versusComputer.setWidth(220);
		versusComputer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- versusComputer.getWidth() / 2);
		versusComputer.setY(multiPlayer.getY() - multiPlayer.getHeight() - 4
				* TwentyFourtyGame.GAP);
		
		settings.setX(10);
		settings.setY(10);

		addActors();
		setupListeners();
	}
	
	private void addActors() {
		stage.addActor(label);
		stage.addActor(singlePlayer);
		stage.addActor(multiPlayer);
		stage.addActor(versusComputer);
		stage.addActor(settings);
	}
	
	private void setupListeners() {
		singlePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new GameScreen());
			}
		});

		multiPlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new MultiMenuScreen());
			}
		});
		
		versusComputer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new DifficultyScreen());
			}
		});
		
		settings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new SettingsScreen());
			}
		});
	}
}
