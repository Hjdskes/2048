package nl.tudelft.ti2206.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TwentyFourtyGame implements ApplicationListener {

	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 600;

	private Stage stage;

	public TwentyFourtyGame() {
	}

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		/* center camera: true */
		stage.getViewport().update(GAME_WIDTH, GAME_HEIGHT, true);
	}

	@Override
	public void resume() {
	}

}
