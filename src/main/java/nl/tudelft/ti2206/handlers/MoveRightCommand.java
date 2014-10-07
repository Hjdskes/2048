package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;

public class MoveRightCommand extends Command {

	public MoveRightCommand(Grid grid) {
		super(grid);
	}
	
	public MoveRightCommand(Grid grid, Boolean test) {
		super(grid, test);
	}

	@Override
	public void execute() {
		tileHandler.moveRight();
		updateAndSpawn();
	}
}
