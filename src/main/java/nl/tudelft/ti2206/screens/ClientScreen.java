package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ClientScreen implements Screen {
	private Stage stage;
	private Label label;
	private TextField textField;
	private TextButton cancel;
	private TextButton play;

	public ClientScreen() {
		stage = new Stage();
		label = new Label(
				"   Enter the IP address to\nwhich you want to connect:",
				AssetHandler.getSkin());
		textField = new TextField("127.0.0.1", AssetHandler.getSkin());
		cancel = new TextButton("Cancel", AssetHandler.getSkin());
		play = new TextButton("Continue", AssetHandler.getSkin());
	}
	
	/** Constructor used for mock insertion */
	public ClientScreen(Stage stage, Label label, TextField field, TextButton button) {
		this.stage = stage;
		this.label = label;
		this.textField = field;
		this.cancel = button;
		this.play = button;
	}
	
	@Override
	public void create() {
		play.setVisible(false);

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getPrefWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT - label.getPrefHeight() - 6
				* TwentyFourtyGame.GAP);
		stage.addActor(label);

		textField.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- textField.getPrefWidth() / 2);
		textField.setY(label.getY() - 12 * TwentyFourtyGame.GAP);
		stage.addActor(textField);

		cancel.setX(TwentyFourtyGame.GAME_WIDTH / 4 - cancel.getPrefWidth() / 2);
		cancel.setY(5 * TwentyFourtyGame.GAP);
		stage.addActor(cancel);

		play.setX((TwentyFourtyGame.GAME_WIDTH / 4) * 3 - play.getPrefWidth()
				/ 2);
		play.setY(5 * TwentyFourtyGame.GAP);
		stage.addActor(play);

		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new MenuScreen());
			}
		});
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//ScreenHandler.add(new WaitScreen());
			}
		});

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void draw() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	@Override
	public boolean isOverlay() {
		return false;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void update() {
		stage.act();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}
