package nl.tudelft.ti2206.handlers;

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
			grid.move(Direction.DOWN);
			Networking.sendString("MOVE[D]\r\n");
			sendGrid();
			return true;
		case Keys.DPAD_UP:
			grid.move(Direction.UP);
			Networking.sendString("MOVE[U]\r\n");
			sendGrid();
			return true;
		case Keys.DPAD_LEFT:
			grid.move(Direction.LEFT);
			Networking.sendString("MOVE[L]\r\n");
			sendGrid();
			return true;
		case Keys.DPAD_RIGHT:
			grid.move(Direction.RIGHT);
			Networking.sendString("MOVE[R]\r\n");
			sendGrid();
			return true;
		}
		return false;
	}

	/**
	 * Sends the local Grid over the network.
	 */
	private void sendGrid() {
		Networking.sendString("GRID[" + grid.toString() + "]\r\n");
	}
}