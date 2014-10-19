package nl.tudelft.ti2206.utils.commands;

import nl.tudelft.ti2206.gameobjects.Grid;

public class MoveLeftCommand extends Command {

	/**
	 * Creates a MoveLeftCommand object that executes the moveLeft command of
	 * the tileHandler.
	 * 
	 * @param grid
	 */
	public MoveLeftCommand(Grid grid) {
		super(grid);
	}

	/**
	 * Calls the moveLeft method and adds the previous grid to the undo stack.
	 */
	@Override
	public void execute() {
		this.grid.getUndoStack().push(grid.toString());
		tileHandler.moveLeft();
		updateAndSpawn();
		this.grid.getRedoStack().clear();
	}
}
