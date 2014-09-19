package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu implements Screen {
	private Stage stage;
	private TextButton singlePlayer;
	private TextButton multiPlayer;

	public Menu() {
		stage = new Stage();
		singlePlayer = new TextButton("Singleplayer", AssetHandler.getSkin());
		multiPlayer = new TextButton("Multiplayer", AssetHandler.getSkin());

		singlePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((TwentyFourtyGame) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen());
			}
		});
		multiPlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((TwentyFourtyGame) Gdx.app.getApplicationListener())
						.setScreen(new MultiGameScreen());
			}
		});

		singlePlayer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- singlePlayer.getPrefWidth() / 2);
		singlePlayer.setY(TwentyFourtyGame.GAME_HEIGHT / 2
				- singlePlayer.getPrefHeight() / 2 + 3 * TwentyFourtyGame.GAP);
		stage.addActor(singlePlayer);

		multiPlayer.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- multiPlayer.getPrefWidth() / 2);
		multiPlayer.setY(TwentyFourtyGame.GAME_HEIGHT / 2
				- multiPlayer.getPrefHeight() / 2 - 3 * TwentyFourtyGame.GAP);
		stage.addActor(multiPlayer);

		Gdx.input.setInputProcessor(stage);
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
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
