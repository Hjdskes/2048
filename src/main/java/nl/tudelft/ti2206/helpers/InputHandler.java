package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

	private GameWorld world;
	private RestartButton restartButton;

	public InputHandler(GameWorld world) {
		this.world = world;
		initRestartButton();
	}

	/**
	 * Initialize a restart button at its designated location.
	 */
	private void initRestartButton() {
		restartButton = new RestartButton(AssetLoader.newgame.getX(),
				AssetLoader.newgame.getY(), AssetLoader.newgame.getWidth(),
				AssetLoader.newgame.getHeight(), AssetLoader.newgame,
				AssetLoader.newgame, world);
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
			restartButton.onClick();
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
			restartButton.onClick();
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

	/**
	 * 
	 * @return the restart button.
	 */
	public RestartButton getRestartButton() {
		return this.restartButton;
	}
}
