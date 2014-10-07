package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.gameobjects.TileIterator;
import nl.tudelft.ti2206.log.Logger;

public abstract class Command {


	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * The name of the instance, initialized to the name of the class. Used for
	 * logging.
	 */
	private String objectName = this.getClass().getSimpleName();
	
	public Grid grid;
	public TileHandler tileHandler;
	public Tile[] oldTiles;
	
	public abstract void execute();
	
	public Command(Grid grid) {
		this.tileHandler = grid.getTileHandler();
	//	this.oldTiles = copyGrid(grid.getTiles());
		this.grid = grid;
	}
	
	public Command(Grid grid, boolean test) {
		this.tileHandler = grid.getTileHandler();
		if(!test) {
			this.oldTiles = copyGrid(grid.getTiles());
		}
		this.grid = grid;
	}
	
	public Tile[] copyGrid(Tile[] oldTiles) {
		Tile[] res = new Tile[16];
		for(int i = 0; i < res.length; i++) {
			Tile tile = new Tile(i, oldTiles[i].getValue());
			res[i] = tile;
		}
		return res;
	}
	
	public void undo() {
		TileIterator iterator = new TileIterator(oldTiles);
		grid.setTiles(iterator);
	}
	
	public void updateAndSpawn() {
		grid.updateMove();
		if (tileHandler.isMoveMade()) {
			logger.info(objectName, "Move succesfully made.");
			tileHandler.reset();
		}
	}
}
