package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.log.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RemoteInputHandlerTest {

	@Mock
	private Grid grid;
	@Mock
	private Logger logger;

	private RemoteInputHandler handler;

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		handler = new RemoteInputHandler(grid);
		handler.setLogger(logger);
	}

	@Test
	public void testFillGrid() {
		handler.fillGrid("2,0");
		verify(grid).setTile(anyInt(), eq(2));
		verify(grid).setTile(anyInt(), eq(0));
	}

	@Test
	public void testMoveUp() {
		handler.moveUp();
		verify(grid).move(Direction.UP);
	}

	@Test
	public void testMoveDown() {
		handler.moveDown();
		verify(grid).move(Direction.DOWN);
	}

	@Test
	public void testMoveRight() {
		handler.moveRight();
		verify(grid).move(Direction.RIGHT);
	}

	@Test
	public void testMoveLeft() {
		handler.moveLeft();
		verify(grid).move(Direction.LEFT);
	}

	@Test
	public void testUpdate() {
		handler.update(null, "MOVE[R]");
		verify(logger).debug("RemoteInputHandler", "update received: MOVE[R]");
	}

	@Test
	public void testValidateGridValid() {
		String validGrid = "2,4,0,2,0,2,8,16,16,2,4,1024,2048,2,2,2";
		assertTrue(handler.validateGrid(validGrid));
	}

	@Test
	public void testValidateGridInValid() {
		String validGrid = "2,4,0,2,0,2,8,16,,2,2";
		assertFalse(handler.validateGrid(validGrid));
	}

	@Test
	public void testHandleRemoteInputMove() {
		handler.handleRemoteInput("MOVE[R]");
		verify(grid).move(Direction.RIGHT);
	}

	@Test
	public void testHandleRemoteInputValidGrid() {
		String validGrid = "2,4,0,2,0,2,8,16,16,2,4,1024,2048,2,2,2";
		handler.handleRemoteInput("GRID[" + validGrid + "]");
		verify(grid, times(16)).setTile(anyInt(), anyInt());
	}

	@Test
	public void testHandleRemoteInputInValidGrid() {
		String invalidGrid = "2,4,0,2,0,2,8,16,16,2,4,,2048,2,2,2";
		handler.handleRemoteInput("GRID[" + invalidGrid + "]");
		verify(logger).error(
				"RemoteInputHandler",
				"Protocol parsing failed, malformed remote grid string: "
						+ invalidGrid);
	}

	@Test
	public void testHandleRemoteInputError() {
		handler.handleRemoteInput("MOVE[R");
		verify(logger).error("RemoteInputHandler",
				"Protocol parsing failed, no closing bracket found (-1).");
	}

	@Test
	public void testHandleRemoteInputMoverError() {
		handler.handleRemoteInput("MOVE[?]");
		verify(logger).error("RemoteInputHandler",
				"Unknown remote direction parameter in protocol: ?");
	}

	@Test
	public void testHandleRemoteInputMoveLeft() {
		handler.handleRemoteInput("MOVE[L]");
		verify(grid).move(Direction.LEFT);
	}

	@Test
	public void testHandleRemoteInputMoveRight() {
		handler.handleRemoteInput("MOVE[R]");
		verify(grid).move(Direction.RIGHT);
	}

	@Test
	public void testHandleRemoteInputMoveUp() {
		handler.handleRemoteInput("MOVE[U]");
		verify(grid).move(Direction.UP);
	}

	@Test
	public void testHandleRemoteInputMoveDown() {
		handler.handleRemoteInput("MOVE[D]");
		verify(grid).move(Direction.DOWN);
	}

	@Test
	public void testHandleRemoteInputRandom() {
		handler.handleRemoteInput("random]");
		verify(logger)
				.error("RemoteInputHandler",
						"Unrecognised remote string in protocol: random], closing tag is at position 6");
	}
}
