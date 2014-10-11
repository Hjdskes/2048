package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;

public class UndoCommand extends Command {

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * The name of the instance, initialized to the name of the class. Used for
	 * logging.
	 */
	private String objectName = this.getClass().getSimpleName();

	public UndoCommand(Grid grid) {
		super(grid);
	}

	@Override
	public void execute() {
		if (!grid.getUndoStack().isEmpty()) {
			this.grid.getRedoStack().push(grid.toString());
			String oldGrid = grid.getUndoStack().pop();
			int score = grid.getScore() / 2;
			grid.setScore(score);
			setStringAsGrid(oldGrid);
			logger.info(objectName, "Undo succesfully conducted, new score is "
					+ score);
		} else {
			logger.info(objectName, "Undo Stack is empty! Undo not conducted.");
		}

	}

}
