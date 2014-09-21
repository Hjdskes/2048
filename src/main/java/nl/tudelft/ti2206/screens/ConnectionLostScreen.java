package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ConnectionLostScreen extends Screen {

	private Label label;
	private TextButton okButton;

	/**
	 * Creates a new ConnectionLostScreen.
	 */
	public ConnectionLostScreen() {
		stage = new Stage();
		label = new Label("Connection lost...", AssetHandler.getSkin());
		okButton = new TextButton("OK", AssetHandler.getSkin());
	}
	
	/** Constructor for testing purposes. */
	public ConnectionLostScreen(Stage stage, Label label, TextButton button) {
		this.stage = stage;
		this.label = label;
		this.okButton = button;
	}

	@Override
	public void create() {
		super.create();
		label.setAlignment(Align.center);
		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT * 3 / 4);

		okButton.setX(TwentyFourtyGame.GAME_WIDTH / 2 - okButton.getWidth() / 2);
		okButton.setY(TwentyFourtyGame.GAME_HEIGHT / 4);
		okButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new MenuScreen());
			}
		});

		stage.addActor(label);
		stage.addActor(okButton);
	}
}
