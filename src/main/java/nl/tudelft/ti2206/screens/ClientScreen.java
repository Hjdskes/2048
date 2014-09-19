package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ClientScreen extends ScreenAdapter {
	private Stage stage;
	private Label label;
	private TextField textField;
	private TextButton cancel;
	private TextButton play;

	public ClientScreen() {
		stage = new Stage();
		label = new Label("   Enter the IP address to\nwhich you want to connect:", AssetHandler.getSkin());
		textField = new TextField("127.0.0.1", AssetHandler.getSkin());
		cancel = new TextButton("Cancel", AssetHandler.getSkin());
		play = new TextButton("Continue", AssetHandler.getSkin());
	}

	@Override
	public void render(float delta) {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Tell all actors to update... */
		stage.act();
		/* ... and to redraw themselves. */
		stage.draw();
	}

	@Override
	public void show() {
		play.setVisible(false);

		label.setX(TwentyFourtyGame.GAME_WIDTH / 2 - label.getPrefWidth() / 2);
		label.setY(TwentyFourtyGame.GAME_HEIGHT - label.getPrefHeight() - 6 * TwentyFourtyGame.GAP);
		stage.addActor(label);

		textField.setX(TwentyFourtyGame.GAME_WIDTH / 2 - textField.getPrefWidth() / 2);
		textField.setY(label.getY() - 12 * TwentyFourtyGame.GAP);
		stage.addActor(textField);

		cancel.setX(TwentyFourtyGame.GAME_WIDTH / 4 - cancel.getPrefWidth() / 2);
		cancel.setY(5 * TwentyFourtyGame.GAP);
		stage.addActor(cancel);

		play.setX((TwentyFourtyGame.GAME_WIDTH / 4) * 3 - play.getPrefWidth() / 2);
		play.setY(5 * TwentyFourtyGame.GAP);
		stage.addActor(play);

		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((TwentyFourtyGame) Gdx.app.getApplicationListener())
						.popScreen();
			}
		});
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//((TwentyFourtyGame) Gdx.app.getApplicationListener())
				//		.addScreen(new WaitScreen());
			}
		});

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
