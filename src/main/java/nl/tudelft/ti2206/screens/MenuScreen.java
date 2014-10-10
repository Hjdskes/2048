package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

	/** The button to go to the host menu. */
	private TextButton hostGame;

	/** The button to go to the client menu. */
	private TextButton connect;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	private TextButton versusComputer;

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
		versusComputer = new TextButton("VS Computer",
				assetHandler.getSkin());
		hostGame = new TextButton("Host a game", assetHandler.getSkin());
		connect = new TextButton("Join a game", assetHandler.getSkin());
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public MenuScreen(Stage stage, Label label, TextButton button) {
		this.stage = stage;
		this.label = label;
		this.singlePlayer = button;
		this.versusComputer = button;
		this.hostGame = button;
		this.connect = button;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT / 2 + label.getHeight() + 6
				* TwentyFourtyGame.GAP);

		singlePlayer.setWidth(200);
		singlePlayer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- singlePlayer.getWidth() / 2);
		singlePlayer.setY(label.getY() - label.getHeight() - 6
				* TwentyFourtyGame.GAP);

		versusComputer.setWidth(200);
		versusComputer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- versusComputer.getWidth() / 2);
		versusComputer.setY(singlePlayer.getY() - 2 
				* TwentyFourtyGame.GAP);

		hostGame.setWidth(200);
		hostGame.setX(TwentyFourtyGame.GAME_WIDTH / 2 - hostGame.getWidth() / 2);
		hostGame.setY(singlePlayer.getY() - singlePlayer.getHeight() - 2
				* TwentyFourtyGame.GAP);

		connect.setWidth(200);
		connect.setX(TwentyFourtyGame.GAME_WIDTH / 2 - connect.getWidth() / 2);
		connect.setY(hostGame.getY() - hostGame.getHeight() - 2
				* TwentyFourtyGame.GAP);

		stage.addActor(label);
		stage.addActor(singlePlayer);
		stage.addActor(versusComputer);
		stage.addActor(hostGame);
		stage.addActor(connect);

		versusComputer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new UserComputerScreen());
			}
		});

		singlePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new GameScreen());
			}
		});
		hostGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new HostScreen());
			}
		});
		connect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new ClientScreen());
			}
		});
	}
}
