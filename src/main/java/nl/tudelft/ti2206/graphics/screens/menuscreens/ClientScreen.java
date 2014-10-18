package nl.tudelft.ti2206.graphics.screens.menuscreens;

import java.util.List;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.graphics.screens.gamescreens.MultiGameScreen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.net.Networking;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The ClientScreen is the screen the client sees. It holds an entry field for
 * the IP address to which a connection should be made.
 */
public class ClientScreen extends Screen {
	/** The text for the main label. */
	public static final String OPPONENT_HOSTADDR = "  Enter your opponent's\r\nhostname or IP address: ";

	/** Error message for an invalid address. */
	public static final String HOST_INVALID = "Invalid host!";

	/** The maximum length allowed for an IP address. */
	private static final int MAX_LENGTH = 20;

	/** The main label. */
	private Label label;

	/** The TextField for the IP address. */
	private TextField textField;

	/** The button to cancel and go back to the main menu. */
	private MenuButton menu;

	/** The button to start the game when a connection has been made. */
	private TextButton play;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton Networking instance. */
	private static Networking networking = Networking.getInstance();

	/** Constructs a new ClientScreen. */
	public ClientScreen() {
		stage = new Stage();
		label = new Label(OPPONENT_HOSTADDR, assetHandler.getSkin());
		List<String> addresses = networking.initLocalAddresses();
		textField = new TextField(addresses.get(0), assetHandler.getSkin());
		menu = new MenuButton();
		play = new TextButton("Play!", assetHandler.getSkin());
		this.setDrawBehavior( new DrawBeige(stage));
	}

	/** Constructor used for mock insertion */
	public ClientScreen(Stage stage, Label label, TextField field,
			MenuButton menuButton, TextButton playButton) {
		this.stage = stage;
		this.label = label;
		this.textField = field;
		this.menu = menuButton;
		this.play = playButton;
		this.setDrawBehavior( new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		play.setX(TwentyFourtyGame.GAME_WIDTH - 2 * TwentyFourtyGame.GAP - play.getWidth()); 
		play.setY(2 * TwentyFourtyGame.GAP);
		addPlayButtonListener();

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getPrefWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT - label.getPrefHeight() - 6
				* TwentyFourtyGame.GAP);
		stage.addActor(label);

		textField.setWidth(TwentyFourtyGame.GAME_WIDTH / 2);
		textField.setMaxLength(MAX_LENGTH);
		textField.setX(TwentyFourtyGame.GAME_WIDTH / 2 - textField.getWidth()
				/ 2);
		textField.setY(label.getY() - 12 * TwentyFourtyGame.GAP);
		textField.setCursorPosition(Align.right);
		stage.setKeyboardFocus(textField);
		stage.addActor(textField);

		stage.addActor(menu);
		stage.addActor(play);
		play.setVisible(false);
	}

	/** Sets the listener for the playButton */
	private void addPlayButtonListener() {
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String text = textField.getText();

				if (networking.isValidHost(text)) {
					networking.startClient(text);
				} else {
					label.setText(HOST_INVALID);
				}
			}
		});
	}

	@Override
	public void update() {
		super.update();

		if (networking.isConnected()) {
			ScreenHandler.getInstance().set(new MultiGameScreen());
		} else {

			if (networking.errorOccured()) {
				label.setText(networking.getLastError());
			} else {
				play.setVisible(true);
			}
		}

	}
}
