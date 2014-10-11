package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;

public class MoveDownCommand extends Command {

	/**
	 * Creates a MoveDownCommand object that executes the moveDown command of the tileHanlder.
	 * @param grid
	 */
	public MoveDownCommand(Grid grid) {
		super(grid);
	}

	/**
	 * Calls the moveDown method and adds the previous grid to the undo stack.
	 */
	@Override
	public void execute() {
		this.grid.getUndoStack().push(grid.toString());
		tileHandler.moveDown();
		updateAndSpawn();
	}

}