package nl.tudelft.ti2206.graphics.screens.menuscreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.graphics.screens.gamescreens.GameScreen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The MenuScreen is used on start-up, to have the user choose between
 * singleplayer, hosting a game or connecting to another player.
 */
public class MenuScreen extends Screen {
	/** The main label. */
	private Label destinyLabel;

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
		destinyLabel = new Label("Choose your destiny!", assetHandler.getSkin().get("large", LabelStyle.class));
		singlePlayer = new TextButton("Singleplayer", assetHandler.getSkin());
		multiPlayer = new TextButton("Multiplayer", assetHandler.getSkin());
		versusComputer = new TextButton("Challenge me!", assetHandler.getSkin());
		settings = new ImageButton(assetHandler.getSkin().getDrawable(
				"settings"));
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public MenuScreen(Stage stage, Label label, TextButton button,
			ImageButton settings) {
		this.stage = stage;
		this.destinyLabel = label;
		this.singlePlayer = button;
		this.versusComputer = button;
		this.multiPlayer = button;
		this.settings = settings;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		setDestinyLabelLocation();
		setSinglePlayerLocation();
		setMultiPlayerLocation();
		setVSComputerLocation();
		setSettingsLocation();

		addActors();
		setupListeners();
	}

	/** Sets the location of the destinyLabel. */
	private void setDestinyLabelLocation() {
		destinyLabel.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- destinyLabel.getWidth() / 2);
		destinyLabel.setY(TwentyFourtyGame.GAME_HEIGHT / 2
				+ destinyLabel.getHeight() + 8 * TwentyFourtyGame.GAP);
	}

	/** Sets the location of the singleplayer button. */
	private void setSinglePlayerLocation() {
		singlePlayer.setWidth(220);
		singlePlayer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- singlePlayer.getWidth() / 2);
		singlePlayer.setY(destinyLabel.getY() - destinyLabel.getHeight() - 6
				* TwentyFourtyGame.GAP);
	}

	/** Sets the location of the multiplayer button. */
	private void setMultiPlayerLocation() {
		multiPlayer.setWidth(220);
		multiPlayer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- multiPlayer.getWidth() / 2);
		multiPlayer.setY(singlePlayer.getY() - singlePlayer.getHeight() - 2
				* TwentyFourtyGame.GAP);
	}

	/** Sets the location of the versus computer button. */
	private void setVSComputerLocation() {
		versusComputer.setWidth(220);
		versusComputer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- versusComputer.getWidth() / 2);
		versusComputer.setY(multiPlayer.getY() - multiPlayer.getHeight() - 4
				* TwentyFourtyGame.GAP);
	}

	/** Sets the location of the settings button. */
	private void setSettingsLocation() {
		settings.setX(10);
		settings.setY(10);
	}

	/** Adds all required actors to stage. */
	private void addActors() {
		stage.addActor(destinyLabel);
		stage.addActor(singlePlayer);
		stage.addActor(multiPlayer);
		stage.addActor(versusComputer);
		stage.addActor(settings);
	}

	/** Sets the listeners for all buttons. */
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
