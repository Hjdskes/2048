package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WinScreen implements Screen {
	private Stage stage;

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		RestartButton restartButton = new RestartButton();
		ContinueButton continueButton = new ContinueButton();

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
}
