package nl.tudelft.ti2206.utils.commands;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.log.Logger;

/**
 * The UndoCommand enables the undo-possibility in the singleplayer game.
 */
public class UndoCommand extends Command {
	/**
	 * The name of the instance, initialized to the name of the class. Used for
	 * logging.
	 */
	private static final String CLASSNAME = UndoCommand.class.getSimpleName();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Creates a new UndoCommand. */
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
			logger.info(CLASSNAME, "Undo succesfully conducted, new score is "
					+ score);
		} else {
			logger.info(CLASSNAME, "Undo Stack is empty! Undo not conducted.");
		}

	}
}
