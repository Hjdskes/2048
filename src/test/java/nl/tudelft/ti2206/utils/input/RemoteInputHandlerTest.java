package nl.tudelft.ti2206.utils.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.commands.MoveDownCommand;
import nl.tudelft.ti2206.utils.commands.MoveLeftCommand;
import nl.tudelft.ti2206.utils.commands.MoveRightCommand;
import nl.tudelft.ti2206.utils.commands.MoveUpCommand;
import nl.tudelft.ti2206.utils.input.RemoteInputHandler;
import nl.tudelft.ti2206.utils.log.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RemoteInputHandlerTest {

	/** The remote Grid on which a move must be conducted. */
	private Grid grid;
	/** The remoteInputHandler which takes remote input and we are going to test. */
	private RemoteInputHandler handler;

	/** Mocked logger to check if a update is received. */
	@Mock
	private Logger logger;

	/**
	 * Launches a headless launcher and creates the necessary objects before
	 * each test.
	 */
	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		// create an empty grid
		grid = new Grid(true);
		handler = new RemoteInputHandler(grid);
		handler.setLogger(logger);
	}

	/**
	 * Test for the fillGrid() method. This test only fills the grid with two
	 * tiles.
	 */
	@Test
	public void testFillGrid() {
		handler.fillGrid("2,11");
		assertEquals(2, grid.getTiles()[0].getValue());
		assertEquals(11, grid.getTiles()[1].getValue());
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testMoveUp() {
		handler.moveUp();
		assertTrue(handler.getCommand() instanceof MoveUpCommand);
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testMoveDown() {
		handler.moveDown();
		assertTrue(handler.getCommand() instanceof MoveDownCommand);
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testMoveRight() {
		handler.moveRight();
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testMoveLeft() {
		handler.moveLeft();
		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
	}

	/**
	 * Checks if the update() method does work.
	 */
	@Test
	public void testUpdate() {
		handler.update(null, "MOVE[R]");
		verify(logger).debug("RemoteInputHandler", "update received: MOVE[R]");
	}

	/**
	 * Test for the validateGrid() method.
	 */
	@Test
	public void testValidateGridValid() {
		String validGrid = "2,4,0,2,0,2,8,16,16,2,4,1024,2048,2,2,2";
		assertTrue(handler.validateGrid(validGrid));
	}

	/**
	 * Test for the validateGrid() method.
	 */
	@Test
	public void testValidateGridInValid() {
		String validGrid = "2,4,0,2,0,2,8,16,,2,2";
		assertFalse(handler.validateGrid(validGrid));
	}

	/**
	 * Test for the handling of remote input. This test is with a valid grid.
	 * The grid should be equal to the valid grid.
	 */
	@Test
	public void testHandleRemoteInputValidGrid() {
		String validGrid = "2,4,0,2,0,2,8,16,16,2,4,1024,2048,2,2,2";
		handler.handleRemoteInput("GRID[" + validGrid + "]");
		assertEquals(validGrid, grid.toString());
	}

	/**
	 * Test for the handling of remote input. This is with a invalid grid. The
	 * logger should return a error message.
	 */
	@Test
	public void testHandleRemoteInputInValidGrid() {
		String invalidGrid = "2,4,0,2,0,2,8,16,16,2,4,,2048,2,2,2";
		handler.handleRemoteInput("GRID[" + invalidGrid + "]");
		verify(logger).error(
				"RemoteInputHandler",
				"Protocol parsing failed, malformed remote grid string: "
						+ invalidGrid);
	}

	/**
	 * Test for handling incorrect remote input. The logger should return an
	 * error message.
	 */
	@Test
	public void testHandleRemoteInputError() {
		handler.handleRemoteInput("MOVE[R");
		verify(logger).error("RemoteInputHandler",
				"Protocol parsing failed, no closing bracket found (-1).");
	}

	/**
	 * Test for handling incorrect remote input. The logger should return an
	 * error message.
	 */
	@Test
	public void testHandleRemoteInputMoverError() {
		handler.handleRemoteInput("MOVE[?]");
		verify(logger).error("RemoteInputHandler",
				"Unknown remote direction parameter in protocol: ?");
	}

	/**
	 * Test for handling of correct remote input. Checks if the current command
	 * is correctly set.
	 */
	@Test
	public void testHandleRemoteInputMoveLeft() {
		handler.handleRemoteInput("MOVE[L]");
		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
	}

	/**
	 * Test for handling of correct remote input. Checks if the current command
	 * is correctly set.
	 */
	@Test
	public void testHandleRemoteInputMoveRight() {
		handler.handleRemoteInput("MOVE[R]");
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
	}

	/**
	 * Test for handling of correct remote input. Checks if the current command
	 * is correctly set.
	 */
	@Test
	public void testHandleRemoteInputMoveUp() {
		handler.handleRemoteInput("MOVE[U]");
		assertTrue(handler.getCommand() instanceof MoveUpCommand);
	}

	/**
	 * Test for handling of correct remote input. Checks if the current command
	 * is correctly set.
	 */
	@Test
	public void testHandleRemoteInputMoveDown() {
		handler.handleRemoteInput("MOVE[D]");
		assertTrue(handler.getCommand() instanceof MoveDownCommand);
	}

	/**
	 * Test for handling of incorrect remote input. The logger should return an
	 * error message.
	 */
	@Test
	public void testHandleRemoteInputRandom() {
		handler.handleRemoteInput("random]");
		verify(logger)
				.error("RemoteInputHandler",
						"Unrecognised remote string in protocol: random], closing tag is at position 6");
	}
}
