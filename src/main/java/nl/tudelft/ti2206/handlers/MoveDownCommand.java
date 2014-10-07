package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.log.Logger;

public class MoveDownCommand implements Command {

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * The name of the instance, initialized to the name of the class. Used for
	 * logging.
	 */
	private String objectName = this.getClass().getSimpleName();

	private TileHandler tileHandler;
	Tile[] oldTiles = new Tile[16];
	Grid grid;

	public MoveDownCommand(Grid grid) {
		this.tileHandler = grid.getTileHandler();
		this.oldTiles = grid.getTiles();
		this.grid = grid;
	}

	@Override
	public void execute() {
		tileHandler.moveDown();
		grid.updateMove();
		if (tileHandler.isMoveMade()) {
			logger.info(objectName, "Move succesfully made.");
			tileHandler.reset();
		}

	}

	@Override
	public void undo() {
		grid.setTiles(oldTiles);
	}

}