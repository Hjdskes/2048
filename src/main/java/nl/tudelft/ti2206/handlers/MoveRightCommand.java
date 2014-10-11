package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;

public class MoveRightCommand extends Command {

	/**
	 * Creates a MoveRightCommand object that executes the moveRight command of the tileHanlder.
	 * @param grid
	 */
	public MoveRightCommand(Grid grid) {
		super(grid);
	}

	/**
	 * Calls the moveRight method and adds the previous grid to the undo stack.
	 */
	@Override
	public void execute() {
		this.grid.getUndoStack().push(grid.toString());
		tileHandler.moveRight();
		updateAndSpawn();
	}
}
