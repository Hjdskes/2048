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

	/** The maximum length allowed for an IP address. */
	private static final int MAX_LENGTH = 20;

	/** The main label. */
	private Label ipLabel;

	/** The TextField for the IP address. */
	private TextField ipField;

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
		String opponentAddress = "  Enter your opponent's\r\nhostname or IP address: ";
		ipLabel = new Label(opponentAddress, assetHandler.getSkin());
		List<String> addresses = networking.initLocalAddresses();
		ipField = new TextField(addresses.get(0), assetHandler.getSkin());
		menu = new MenuButton();
		play = new TextButton("Play!", assetHandler.getSkin());
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor used for mock insertion */
	public ClientScreen(Stage stage, Label label, TextField field,
			MenuButton menuButton, TextButton playButton) {
		this.stage = stage;
		this.ipLabel = label;
		this.ipField = field;
		this.menu = menuButton;
		this.play = playButton;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();
		initPlayButton();
		initIpLabel();
		initIpField();
		addActors();
		stage.setKeyboardFocus(ipField);
	}

	/** Adds all required actors to the stage. */
	private void addActors() {
		stage.addActor(ipLabel);
		stage.addActor(ipField);
		stage.addActor(menu);
		stage.addActor(play);
	}

	/** Positions the playbutton and initializes it to be invisible. */
	private void initPlayButton() {
		play.setX(TwentyFourtyGame.GAME_WIDTH - 2 * TwentyFourtyGame.GAP
				- play.getWidth());
		play.setY(2 * TwentyFourtyGame.GAP);
		addPlayButtonListener();
		play.setVisible(false);
	}

	/** Initializes the label above the ip input field. */
	private void initIpLabel() {
		ipLabel.setX(TwentyFourtyGame.GAME_WIDTH / 2 - ipLabel.getPrefWidth()
				/ 2);
		ipLabel.setY(TwentyFourtyGame.GAME_HEIGHT - ipLabel.getPrefHeight() - 6
				* TwentyFourtyGame.GAP);
	}

	/** Positions the cursor and the ip field itself. */
	private void initIpField() {
		ipField.setWidth(TwentyFourtyGame.GAME_WIDTH / 2);
		ipField.setMaxLength(MAX_LENGTH);
		ipField.setX(TwentyFourtyGame.GAME_WIDTH / 2 - ipField.getWidth() / 2);
		ipField.setY(ipLabel.getY() - 12 * TwentyFourtyGame.GAP);
		ipField.setCursorPosition(Align.right);
	}

	/** Sets the listener for the playButton */
	private void addPlayButtonListener() {
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String text = ipField.getText();

				if (networking.isValidHost(text)) {
					networking.startClient(text);
				} else {
					ipLabel.setText("Invalid host!");
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
				ipLabel.setText(networking.getLastError());
			} else {
				play.setVisible(true);
			}
		}
	}
}
