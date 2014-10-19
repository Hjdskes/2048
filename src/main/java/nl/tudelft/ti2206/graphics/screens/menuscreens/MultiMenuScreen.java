package nl.tudelft.ti2206.graphics.screens.menuscreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawSimple;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

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
	private Label hosterLabel;

	/** The button to go to the client menu. */
	private TextButton connect;

	/** The button to go to the host menu. */
	private TextButton host;

	/** The button to return to the main menu. */
	private MenuButton menuButton;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new MenuScreen. */
	public MultiMenuScreen() {
		stage = new Stage();
		hosterLabel = new Label("Who's hosting?", assetHandler.getSkin());

		connect = new TextButton("Join a game", assetHandler.getSkin());
		host = new TextButton("Host a game", assetHandler.getSkin());
		menuButton = new MenuButton();

		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public MultiMenuScreen(Stage stage, Label label, MenuButton menuButton, TextButton button) {
		this.stage = stage;
		this.hosterLabel = label;
		this.connect = button;
		this.host = button;
		this.menuButton = menuButton;
		this.setDrawBehavior(new DrawSimple(stage));
	}

	@Override
	public void create() {
		super.create();

		setHostPlayerLocation();
		setConnectLocation();
		setHostLocation();
		
		setListeners();
		addActors();
	}
	
	/** Sets the location of the hosting player label. */
	private void setHostPlayerLocation() {
		hosterLabel.setX(TwentyFourtyGame.GAME_WIDTH / 2 - hosterLabel.getWidth() / 2);
		hosterLabel.setY(TwentyFourtyGame.GAME_HEIGHT / 2 + hosterLabel.getHeight() + 8
				* TwentyFourtyGame.GAP);
	}

	/** Sets the location of the connect button. */
	private void setConnectLocation() {
		connect.setWidth(220);
		connect.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- connect.getWidth() / 2);
		connect.setY(hosterLabel.getY() - hosterLabel.getHeight() - 6
				* TwentyFourtyGame.GAP);
	}
	
	/** Sets the location of the host button. */
	private void setHostLocation() {
		host.setWidth(220);
		host.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- host.getWidth() / 2);
		host.setY(connect.getY() - host.getHeight() - 2
				* TwentyFourtyGame.GAP);
	}
	
	/** Adds all required actors to stage. */
	private void addActors() {
		stage.addActor(hosterLabel);
		stage.addActor(connect);
		stage.addActor(host);
		stage.addActor(menuButton);

	}
	
	/** Sets the listeners for all buttons. */
	private void setListeners() {
		connect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new ClientScreen());
			}
		});
		
		host.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new HostScreen());
			}
		});	
	}
}
