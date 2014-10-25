package nl.tudelft.ti2206.utils.commands;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The MoveRightCommand executes the moveRight command of the TileHandler.
 */
public class MoveRightCommand extends Command {
	/**
	 * Creates a MoveRightCommand.
	 * 
	 * @param grid The grid to move on.
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
		this.grid.getRedoStack().clear();
	}
}
