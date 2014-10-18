package nl.tudelft.ti2206.utils.commands;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.handlers.TileHandler;
import nl.tudelft.ti2206.utils.log.Logger;

public abstract class Command {

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * The name of the instance, initialized to the name of the class. Used for
	 * logging.
	 */
	private String objectName = this.getClass().getSimpleName();

	/** The current grid on which the move has to take place. */
	protected Grid grid;

	/** The current TileHandler that has to conduct the move. */
	protected TileHandler tileHandler;

	/**
	 * The subclasses have to implement a execute class.
	 */
	public abstract void execute();

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
		grid.updateMove();
		if (tileHandler.isMoveMade()) {
			logger.info(objectName, "Move succesfully made.");
			tileHandler.reset();
		}
	}
}
