package nl.tudelft.ti2206.utils.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.commands.Command;
import nl.tudelft.ti2206.utils.commands.MoveDownCommand;
import nl.tudelft.ti2206.utils.commands.MoveLeftCommand;
import nl.tudelft.ti2206.utils.commands.MoveUpCommand;
import nl.tudelft.ti2206.utils.commands.UndoCommand;

public class UndoCommandTest {

	/** The grid on which we are going to perform different commands. */
	private Grid grid;
	
	/** The undo command we are going to test. */
	private UndoCommand undoCommand;
	/** MoveUpCommand so we can test the undo command. */
	private Command moveUpCommand;
	/** MoveDownCommand so we can test the undo command. */
	private Command moveDownCommand;
	/** MoveLeftCommand so we can test the undo command. */
	private Command moveLeftCommand;

	/**
	 * Creates a grid and the necessary commands for the tests.
	 */
	@Before
	public void setUp() {
		grid = new Grid(true);
		undoCommand = new UndoCommand(grid);
		moveUpCommand = new MoveUpCommand(grid);
		moveLeftCommand = new MoveLeftCommand(grid);
		moveDownCommand = new MoveDownCommand(grid);

	}

	/**
	 * Tests if the undo command is correctly executed.
	 */
	@Test
	public void testUndo() {
		String gridBefore = grid.toString();
		grid.setScore(500);

		moveUpCommand.execute();
		int score = grid.getScore();
		undoCommand.execute();

		assertEquals(grid.getScore(), score / 2);
		assertEquals(grid.toString(), gridBefore);
	}

	/**
	 * Tests if the undo command is correctly executed multiple times.
	 */
	@Test
	public void testUndoMultipleTimes() {
		grid.setScore(500);

		String gridBefore = grid.toString();
		moveUpCommand.execute();

		String gridBefore2 = grid.toString();
		moveDownCommand.execute();

		String gridBefore3 = grid.toString();
		moveLeftCommand.execute();
		int score = grid.getScore();

		undoCommand.execute();
		assertEquals(grid.getScore(), score / 2);
		assertEquals(grid.toString(), gridBefore3);

		undoCommand.execute();
		assertEquals(grid.getScore(), score / 4);
		assertEquals(grid.toString(), gridBefore2);

		undoCommand.execute();
		assertEquals(grid.getScore(), score / 8);
		assertEquals(grid.toString(), gridBefore);
	}

	/**
	 * Tests if the undo command is correctly executed when the undo stack is
	 * empty.
	 */
	@Test
	public void testUndoEmptyStack() {
		String gridBefore = grid.toString();
		undoCommand.execute();

		assertEquals(grid.getScore(), 0);
		assertEquals(grid.toString(), gridBefore);
	}

}
