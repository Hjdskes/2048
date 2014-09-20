package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The WinScreen is displayed when the player has won. It is semi-transparent,
 * and offers the ability to restart or continue playing.
 */
public class WinScreen implements Screen {
	/** The stage which holds all Actors. */
	private Stage stage;

	/** The button used to put the game into continuing state. */
	private ContinueButton continueButton;

	/** The button used to restart the game. */
	private RestartButton restartButton;

	/** Constructs a new WinScreen. */
	public WinScreen() {
		stage = new Stage();
		restartButton = new RestartButton();
		continueButton = new ContinueButton();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		stage.addActor(new Image(AssetHandler.getSkin(), "wonoverlay"));
		stage.addActor(continueButton);
		stage.addActor(restartButton);
	}

	@Override
	public void draw() {
		stage.draw();
	}

	@Override
	public boolean isOverlay() {
		return true;
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
