package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;

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
public class MultiMenuScreen extends Screen {
	/** The main label. */
	private Label label;

	/** The button to go to the client menu. */
	private TextButton connect;

	private TextButton host;
	
	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new MenuScreen. */
	public MultiMenuScreen() {
		/*
		 * To resize the display when we get back to the menu from a multiplayer
		 * session.
		 */
		Gdx.graphics.setDisplayMode(TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();
		label = new Label("Who's hosting?", assetHandler.getSkin());
		
		connect = new TextButton("Join a game", assetHandler.getSkin());
		host = new TextButton("Host a game", assetHandler.getSkin());
		
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public MultiMenuScreen(Stage stage, Label label, TextButton button) {
		this.stage = stage;
		this.label = label;
		this.connect = button;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT / 2 + label.getHeight() + 8
				* TwentyFourtyGame.GAP);

		connect.setWidth(220);
		connect.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- connect.getWidth() / 2);
		connect.setY(label.getY() - label.getHeight() - 6
				* TwentyFourtyGame.GAP);

		host.setWidth(220);
		host.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- host.getWidth() / 2);
		host.setY(connect.getY() - host.getHeight() - 2
				* TwentyFourtyGame.GAP);



		stage.addActor(label);
		stage.addActor(connect);
		stage.addActor(host);
		
		
		connect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new ClientScreen());
			}
		});
		
		host.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.add(new HostScreen());
			}
		});
		
	}
}
