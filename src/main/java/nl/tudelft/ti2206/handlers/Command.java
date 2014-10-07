package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
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
	public String gridString;
	
	public abstract void execute();
	
	public Command(Grid grid) {
		this.tileHandler = grid.getTileHandler();
		this.grid = grid;
		this.gridString = gridToString();
	}
	
	public String gridToString() {
		return grid.toString();
	}
	
	public void setStringAsGrid(String string) {
		String[] temp = string.split(",");
		for(int i = 0; i < temp.length; i++) {
			grid.setTile(i, Integer.parseInt(temp[i]));
		}
	}
	
	public void undo() {
		setStringAsGrid(gridString);
	}
	
	public void updateAndSpawn() {
		grid.updateMove();
		if (tileHandler.isMoveMade()) {
			logger.info(objectName, "Move succesfully made.");
			tileHandler.reset();
		}
	}
}
