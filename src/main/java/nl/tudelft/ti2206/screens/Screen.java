package nl.tudelft.ti2206.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

/**
 * An abstract class for screens.
 * 
 * Code based on: http://gamedev.stackexchange.com/questions/75902/how-to-design-transparent-screen-in-libgdx
 */
public abstract class Screen implements Disposable {
	protected Stage stage;
	
	/**
	 * Called when the screen is shown. Used for initialization.
	 */
	public void create() {
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Draws the screen.
	 */
	public void draw() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	/**
	 * Determines if the screen is an overlay or not. Overlays will not cause
	 * screens below it to automatically exit.
	 *
	 * @return True if the screen is an overlay, false otherwise.
	 */
	public boolean isOverlay() {
		return false;
	}

	/**
	 * Pauses the screen.
	 */
	public void pause() {
	}

	/**
	 * Called when the screen is resized.
	 *
	 * @param width
	 *            The new game window width (in pixels).
	 * @param height
	 *            The new game window height (in pixels).
	 */
	public void resize(int width, int height) {
	}

	/**
	 * Resumes the screen after a pause.
	 */
	public void resume() {
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Updates the screen.
	 */
	public void update() {
		stage.act();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	/**
	 * @return The stage.
	 */
	public Stage getStage() {
		return stage;
	}
}