package nl.tudelft.ti2206.utils.commands;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.handlers.TileHandler;

/**
 * The Command class defines an abstract execute method, which enables subclasses
 * to define and perform various "commands".
 */
public abstract class Command {
	/** The current grid on which the move has to take place. */
	protected Grid grid;

	/** The current TileHandler that has to conduct the move. */
	protected TileHandler tileHandler;

	/**
	 * Common constructor for all subclasses.
	 * @param grid The grid to execute the command on.
	 */
	public Command(Grid grid) {
		this.tileHandler = grid.getTileHandler();
		this.grid = grid;
	}

	/**
	 * Sets the string representation of a grid as the grid.
	 * 
	 * @param string
	 *            that represents the old grid.
	 */
	public void setStringAsGrid(String string) {
		String[] temp = string.split(",");
		for (int i = 0; i < temp.length; i++) {
			grid.setTile(i, Integer.parseInt(temp[i]));
		}
	}

	/**
	 * Calls grid to update everything after a move and resets the TileHandler.
	 */
	public void updateAndSpawn() {
		grid.updateAfterMove();
	}

	/**
	 * The subclasses have to implement an execute method.
	 */
	public abstract void execute();
}
