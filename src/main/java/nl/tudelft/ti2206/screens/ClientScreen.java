package nl.tudelft.ti2206.screens;

import java.util.List;

import nl.tudelft.ti2206.buttons.CancelButton;
import nl.tudelft.ti2206.buttons.PlayButton;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.StringConstants;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The ClientScreen is the screen the client sees. It holds an entry field for
 * the IP address to which a connection should be made.
 */
public class ClientScreen extends Screen {
	/** The main label. */
	private Label label;

	/** The TextField for the IP address. */
	private TextField textField;

	/** The button to cancel and go back to the main menu. */
	private CancelButton cancel;

	/** button to start the game when a connection has been made. */
	private PlayButton play;

	/** Constructs a new ClientScreen. */
	public ClientScreen() {
		stage = new Stage();

		label = new Label(StringConstants.OPPONENT_HOSTADDR,
				AssetHandler.getSkin());

		List<String> addresses = Networking.getLocalAddresses();
		textField = new TextField(addresses.get(0), AssetHandler.getSkin());
		cancel = new CancelButton();
		play = new PlayButton();
	}

	/** Constructor used for mock insertion */
	public ClientScreen(Stage stage, Label label, TextField field,
			CancelButton cancelButton, PlayButton playButton) {
		this.stage = stage;
		this.label = label;
		this.textField = field;
		this.cancel = cancelButton;
		this.play = playButton;
	}

	@Override
	public void create() {
		super.create();

		addPlayButtonListener();

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getPrefWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT - label.getPrefHeight() - 6
				* TwentyFourtyGame.GAP);
		stage.addActor(label);

		textField.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- textField.getPrefWidth() / 2);
		textField.setY(label.getY() - 12 * TwentyFourtyGame.GAP);
		stage.addActor(textField);

		stage.addActor(cancel);
		stage.addActor(play);
		play.setVisible(false);
	}

	/** Sets the listener for the playButton */
	private void addPlayButtonListener() {
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				String text = textField.getText();

				if (Networking.isValidHost(text)) {
					Networking.startClient(text);
				} else
					label.setText(StringConstants.HOST_INVALID);
			}
		});
	}

	@Override
	public void update() {
		super.update();

		String text = textField.getText();

		if (Networking.isConnected()) {
			// proceed
		} else {

			String error = Networking.getLastError();

			if (error.compareTo("") != 0)
				label.setText(error);
			else if (text.compareTo("") == 0) {
				label.setText(StringConstants.OPPONENT_HOSTADDR);
				play.setVisible(false);
			} else {
				play.setVisible(true);
			}
		}
	}
}
