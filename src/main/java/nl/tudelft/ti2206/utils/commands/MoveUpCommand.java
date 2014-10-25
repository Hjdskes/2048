package nl.tudelft.ti2206.utils.commands;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The MoveUpCommand executes the moveUp command of the TileHandler.
 */
public class MoveUpCommand extends Command {
	/**
	 * Creates a MoveUpCommand.
	 * 
	 * @param grid The grid to move on.
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
		this.grid.getRedoStack().clear();
	}
}