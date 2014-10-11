package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;

public class RedoCommand extends Command {

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * The name of the instance, initialized to the name of the class. Used for
	 * logging.
	 */
	private String objectName = this.getClass().getSimpleName();

	public RedoCommand(Grid grid) {
		super(grid);
	}

	@Override
	public void execute() {
		if (!grid.getRedoStack().isEmpty()) {
			String newGrid = grid.getRedoStack().pop();
			setStringAsGrid(newGrid);

			int score = grid.getScore() * 2;
			grid.setScore(score);

			logger.info(objectName, "Redo succesfully conducted, new score is "
					+ score);
		} else {
			logger.info(objectName, "Redo Stack is empty! Redo not conducted.");
		}
	}

}
