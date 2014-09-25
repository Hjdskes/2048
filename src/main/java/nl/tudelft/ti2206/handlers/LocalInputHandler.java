package nl.tudelft.ti2206.handlers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.net.Networking;

/**
 * The InputHandler processes input events and sends these over the network to
 * another instance.
 */
public class LocalInputHandler extends InputListener {

	/** The singleton Networking instance. */
	private static Networking networking = Networking.getInstance();

	/**
	 * A reference to the local Grid, so the called objects can interact with
	 * it.
	 */
	private Grid grid;

	/**
	 * Creates a new LocalInputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the local Grid.
	 */
	public LocalInputHandler(Grid grid) {
		this.grid = grid;
		sendGrid();
	}

	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		switch (keycode) {
		case Keys.DPAD_DOWN:
			/* Logging the move of the local player. */
			Gdx.app.setLogLevel(Application.LOG_INFO);
			Gdx.app.log(this.getClass().getSimpleName(),
					"Move is made in the direction DOWN");

			grid.move(Direction.DOWN);
			networking.sendString("MOVE[D]");
			sendGrid();
			return true;
		case Keys.DPAD_UP:
			/* Logging the move of the local player. */
			Gdx.app.setLogLevel(Application.LOG_INFO);
			Gdx.app.log(this.getClass().getSimpleName(),
					"Move is made in the direction UP");

			grid.move(Direction.UP);
			networking.sendString("MOVE[U]");
			sendGrid();
			return true;
		case Keys.DPAD_LEFT:
			/* Logging the move of the local player. */
			Gdx.app.setLogLevel(Application.LOG_INFO);
			Gdx.app.log(this.getClass().getSimpleName(),
					"Move is made in the direction LEFT");

			grid.move(Direction.LEFT);
			networking.sendString("MOVE[L]");
			sendGrid();
			return true;
		case Keys.DPAD_RIGHT:
			/* Logging the move of the local player. */
			Gdx.app.setLogLevel(Application.LOG_INFO);
			Gdx.app.log(this.getClass().getSimpleName(),
					"Move is made in the direction RIGHT");

			grid.move(Direction.RIGHT);
			networking.sendString("MOVE[R]");
			sendGrid();
			return true;
		}
		return false;
	}

	/**
	 * Sends the local Grid over the network.
	 */
	private void sendGrid() {
		networking.sendString("GRID[" + grid.toString() + "]");
	}
}