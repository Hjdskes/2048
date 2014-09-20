package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class WinScreen implements Screen {
	private Stage stage;
	private ContinueButton continueButton;
	private RestartButton restartButton;
	
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
