package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import nl.tudelft.ti2206.gameobjects.Grid;

public class RedoCommandTest {

	/** The grid on which we are going to preform different commands. */
	private Grid grid;
	
	/** The redo command we are going to test. */
	private RedoCommand redoCommand;
	/** The undo command so we can test the redo command. */
	private UndoCommand undoCommand;
	/** MoveUpCommand so we can perform the undo command. */
	private Command moveUpCommand;
	/** MoveDownCommand so we can perform the undo command. */
	private Command moveDownCommand;
	/** MoveLeftCommand so we can perform the undo command. */
	private Command moveLeftCommand;

	/**
	 * Creates a grid and the necessary commands for the tests.
	 */
	@Before
	public void setUp() {
		grid = new Grid(true);
		undoCommand = new UndoCommand(grid);
		redoCommand = new RedoCommand(grid);
		moveUpCommand = new MoveUpCommand(grid);
		moveLeftCommand = new MoveLeftCommand(grid);
		moveDownCommand = new MoveDownCommand(grid);

	}

	/**
	 * Tests if the redo command is correctly executed.
	 */
	@Test
	public void testRedo() {

		grid.setScore(500);

		moveUpCommand.execute();
		undoCommand.execute();

		int score = grid.getScore();
		String gridBefore = grid.toString();
		redoCommand.execute();

		assertEquals(grid.getScore(), score * 2);
		assertEquals(grid.toString(), gridBefore);
	}

	/**
	 * Tests if the redo command is correctly executed multiple times.
	 */
	@Test
	public void testRedoMultipleTimes() {

		grid.setScore(500);

		moveUpCommand.execute();
		moveDownCommand.execute();
		moveLeftCommand.execute();

		undoCommand.execute();
		int score = grid.getScore() - 2;
		String gridBefore = grid.toString();

		undoCommand.execute();
		int score2 = grid.getScore() - 1;
		String gridBefore2 = grid.toString();

		undoCommand.execute();
		int score3 = grid.getScore();
		String gridBefore3 = grid.toString();

		redoCommand.execute();
		assertEquals(grid.getScore(), score3 * 2);
		assertEquals(grid.toString(), gridBefore3);

		redoCommand.execute();
		assertEquals(grid.getScore(), score2 * 2);
		assertEquals(grid.toString(), gridBefore2);

		redoCommand.execute();
		assertEquals(grid.getScore(), score * 2);
		assertEquals(grid.toString(), gridBefore);
	}

	/**
	 * Tests if the redo command is correctly executed when the redo stack is
	 * empty.
	 */
	@Test
	public void testRedoEmptyStack() {
		String gridBefore = grid.toString();
		redoCommand.execute();

		assertEquals(grid.getScore(), 0);
		assertEquals(grid.toString(), gridBefore);

	}

}