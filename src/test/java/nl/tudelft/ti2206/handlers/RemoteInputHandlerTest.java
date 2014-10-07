package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.log.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RemoteInputHandlerTest {

	@Mock
	private Grid grid;
	@Mock
	private Tile tile;
	@Mock
	private Logger logger;

	private RemoteInputHandler handler;

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		handler = new RemoteInputHandler(grid);
		handler.setLogger(logger);
		
		tile = Mockito.mock(Tile.class);
		Tile[] tiles = new Tile[16];
		for (int i = 0; i < 16; i++) {
			tiles[i] = tile;
		}
		when(grid.getTiles()).thenReturn(tiles);
		when(grid.getTileHandler()).thenReturn(new TileHandler(tiles));
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
		assertTrue(handler.getCommand() instanceof MoveUpCommand);
	}

	@Test
	public void testMoveDown() {
		handler.moveDown();
		assertTrue(handler.getCommand() instanceof MoveDownCommand);
	}

	@Test
	public void testMoveRight() {
		handler.moveRight();
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
	}

	@Test
	public void testMoveLeft() {
		handler.moveLeft();
		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
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
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
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
		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
	}

	@Test
	public void testHandleRemoteInputMoveRight() {
		handler.handleRemoteInput("MOVE[R]");
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
	}

	@Test
	public void testHandleRemoteInputMoveUp() {
		handler.handleRemoteInput("MOVE[U]");
		assertTrue(handler.getCommand() instanceof MoveUpCommand);
	}

	@Test
	public void testHandleRemoteInputMoveDown() {
		handler.handleRemoteInput("MOVE[D]");
		assertTrue(handler.getCommand() instanceof MoveDownCommand);
	}

	@Test
	public void testHandleRemoteInputRandom() {
		handler.handleRemoteInput("random]");
		verify(logger)
				.error("RemoteInputHandler",
						"Unrecognised remote string in protocol: random], closing tag is at position 6");
	}
}
