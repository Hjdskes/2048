package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

	private GameWorld world;
	private RestartButton restartButton;
	private ContinueButton continueButton;

	public InputHandler(GameWorld world) {
		this.world = world;
		//this.restartButton = ButtonHandler.getRestartButton();
		//this.continueButton = ButtonHandler.getContinueButton();
		setRestartButton(ButtonHandler.getRestartButton());
		setContinueButton(ButtonHandler.getContinueButton());
	}


	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.DPAD_DOWN:
			world.getGrid().move(Direction.DOWN);
			return true;
		case Keys.DPAD_UP:
			world.getGrid().move(Direction.UP);
			return true;
		case Keys.DPAD_LEFT:
			world.getGrid().move(Direction.LEFT);
			return true;
		case Keys.DPAD_RIGHT:
			world.getGrid().move(Direction.RIGHT);
			return true;
		case Keys.ESCAPE:
			// forward to restart button
			restartButton.onClick(world);
			return true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (restartButton.isTouchDown(screenX, screenY)) {
			restartButton.onClick(world);
			return true;
		}
		
		if (continueButton.isTouchDown(screenX, screenY)) {
			continueButton.onClick(world);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	public void setRestartButton(RestartButton restartButton) {
		this.restartButton = restartButton;
	}
	
	public void setContinueButton(ContinueButton continueButton) {
		this.continueButton = continueButton;
	}
}
