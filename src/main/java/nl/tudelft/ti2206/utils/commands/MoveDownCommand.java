package nl.tudelft.ti2206.utils.commands;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The MoveDownCommand executes the moveDown command of the TileHandler.
 */
public class MoveDownCommand extends Command {
	/**
	 * Creates a MoveDownCommand.
	 * 
	 * @param grid The grid to move on.
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
		this.grid.getRedoStack().clear();
	}

}