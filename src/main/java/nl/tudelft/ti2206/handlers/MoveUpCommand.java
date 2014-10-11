package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;

public class MoveUpCommand extends Command {

	/**
	 * Creates a MoveUpCommand object that executes the moveUp command of the tileHanlder.
	 * @param grid
	 */
	public MoveUpCommand(Grid grid) {
		super(grid);
	}
	
	/**
	 * Calls the moveUp method and adds the previous grid to the undo stack.
	 */
	@Override
	public void execute() {
		this.grid.getUndoStack().push(grid.toString());
		tileHandler.moveUp();
		updateAndSpawn();
	}
}