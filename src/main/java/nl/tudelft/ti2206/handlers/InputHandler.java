package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 * The InputHandler processes input events, from for example the keyboard or the mice.
 * It delegates these events to the appropriate abstraction classes. Currently this is only the ButtonHandler.
 */
public class InputHandler extends InputAdapter {
	
	/**
	 * A reference to the current GameWorld, so the called objects can interact
	 * with it.
	 */
	private GameWorld world;

	/**
	 * Creates a new InputHandler instance.
	 * 
	 * @param world
	 *            A reference to the current GameWorld. It will be passed to the
	 *            called objects to interact with the GameWorld.
	 */
	public InputHandler(GameWorld world) {
		this.world = world;
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
		}

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return ButtonHandler.touchDown(world, screenX, screenY);
	}
}
