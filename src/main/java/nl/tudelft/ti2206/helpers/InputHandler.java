package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

	private GameWorld world;

	// private float scale;

	public InputHandler(GameWorld world, int screenWidth) {
		this.world = world;
		// scale = 600f / screenWidth;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.DPAD_DOWN) {
			world.getGrid().move(Direction.DOWN);
			return true;
		} else if (keycode == Keys.DPAD_UP) {
			world.getGrid().move(Direction.UP);
			return true;
		} else if (keycode == Keys.DPAD_LEFT) {
			world.getGrid().move(Direction.LEFT);
			return true;
		} else if (keycode == Keys.DPAD_RIGHT) {
			world.getGrid().move(Direction.RIGHT);
			return true;
		}

		if (keycode == Keys.ESCAPE) {
			world.restart();
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
}
