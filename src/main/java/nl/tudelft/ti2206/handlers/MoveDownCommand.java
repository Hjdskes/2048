package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;

public class MoveDownCommand extends Command {

	public MoveDownCommand(Grid grid) {
		super(grid);
	}

	@Override
	public void execute() {
		tileHandler.moveDown();
		updateAndSpawn();
	}

}