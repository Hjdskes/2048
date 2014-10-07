package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;

public abstract class Command {


	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * The name of the instance, initialized to the name of the class. Used for
	 * logging.
	 */
	private String objectName = this.getClass().getSimpleName();
	
	/** The current grid on which the move has to take place. */
	protected Grid grid;
	
	/** The current TileHandler that has to conduct the move. */
	protected TileHandler tileHandler;
	
	/** String representation of the old grid before the move. */
	protected String gridString;
	
	/**
	 * The subclasses have to implement a execute class.
	 */
	public abstract void execute();
	
	public Command(Grid grid) {
		this.tileHandler = grid.getTileHandler();
		this.grid = grid;
		this.gridString = grid.toString();
		this.grid.addToUndoStack(grid.toString());
		this.grid.clear();
	}
	
	public void gridToString() {
		 grid.addToRedoStack(grid.toString());
	}
	
	/**
	 * Sets the string representation of a grid as the grid.
	 * @param string that represents the old grid.
	 */
	public void setStringAsGrid(String string) {
		String[] temp = string.split(",");
		for(int i = 0; i < temp.length; i++) {
			grid.setTile(i, Integer.parseInt(temp[i]));
		}
	}
	
	/**
	 * Undo the previous move by setting the old grid as the current one.
	 */
	public void undo() {
		String oldGrid = grid.getPreviousGrid();
		setStringAsGrid(oldGrid);
		int score = grid.getScore()/2;
		if(grid.getScore() == grid.getHighscore()) {
			grid.setHighscore(score);
		}
		grid.setScore(score);
		
		logger.info(objectName, "Undo succesfully conducted, new score is " + score);
	}
		
	/**
	 * Calls grid to update everything after a move and resets the TileHandler.
	 */
	public void updateAndSpawn() {
		grid.updateMove();
		if (tileHandler.isMoveMade()) {
			logger.info(objectName, "Move succesfully made.");
			tileHandler.reset();
		}
	}
}
