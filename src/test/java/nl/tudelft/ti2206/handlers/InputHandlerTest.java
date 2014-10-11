//package nl.tudelft.ti2206.handlers;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import java.util.Stack;
//
//import nl.tudelft.ti2206.gameobjects.Grid;
//import nl.tudelft.ti2206.gameobjects.Tile;
//import nl.tudelft.ti2206.handlers.InputHandler;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import com.badlogic.gdx.Input.Keys;
//
///**
// * Test class for the InputHandler.
// * 
// * @author group-21
// */
//public class InputHandlerTest {
//	/** The object under test. */
//	private static InputHandler handler;
//	/** A mock to verify behavior. */
//	private static Grid grid;
//	/** A mock to for the getTiles() */
//	private static Tile tile;
//
//	/**
//	 * Initialize all mocks and creates a new InputHandler.
//	 */
//	@Before
//	public void setUp() {
//		grid = Mockito.mock(Grid.class);
//		handler = new InputHandler(grid);
//		tile = Mockito.mock(Tile.class);
//		Tile[] tiles = new Tile[16];
//		for (int i = 0; i < 16; i++) {
//			tiles[i] = tile;
//		}
//		when(grid.getTiles()).thenReturn(tiles);
//		when(grid.getTileHandler()).thenReturn(new TileHandler(grid));
//		when(grid.getUndoStack()).thenReturn(new Stack<String>());
//		when(grid.getRedoStack()).thenReturn(new Stack<String>());
//	}
//
//	/**
//	 * Tests if the constructor successfully creates a new SimpleButton object.
//	 */
//	@Test
//	public void testConstructor() {
//		assertNotNull(handler);
//	}
//
//	/**
//	 * Make sure the MoveDownCommand is set for moving the tiles when the down
//	 * arrow key is pressed.
//	 */
//	@Test
//	public void testKeyDownDown() {
//		handler.keyDown(null, Keys.DPAD_DOWN);
//		assertTrue(handler.getCommand() instanceof MoveDownCommand);
//	}
//
//	/**
//	 * Make sure the MoveUpCommand is set for moving the tiles when the up arrow
//	 * key is pressed.
//	 */
//	@Test
//	public void testKeyDownUp() {
//		handler.keyDown(null, Keys.DPAD_UP);
//		assertTrue(handler.getCommand() instanceof MoveUpCommand);
//	}
//
//	/**
//	 * Make sure the MoveLeftCommand is set for moving the tiles when the left
//	 * arrow key is pressed.
//	 */
//	@Test
//	public void testKeyDownLeft() {
//		handler.keyDown(null, Keys.DPAD_LEFT);
//		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
//	}
//
//	/**
//	 * Make sure the MoveRightCommand is set for moving the tiles when the right
//	 * arrow key is pressed.
//	 */
//	@Test
//	public void testKeyDownRight() {
//		handler.keyDown(null, Keys.DPAD_RIGHT);
//		assertTrue(handler.getCommand() instanceof MoveRightCommand);
//	}
//
//	/**
//	 * Make sure the keyDown method returns false when the key is invalid.
//	 */
//	@Test
//	public void testKeyDownInvalid() {
//		assertEquals(false, handler.keyDown(null, Keys.BACKSLASH));
//	}
//
//	/**
//	 * Make sure the RedoCommand is set for moving the tiles when the R key is
//	 * pressed
//	 */
//	@Test
//	public void testKeyRedo() {
//		handler.keyDown(null, Keys.R);
//		assertTrue(handler.getCommand() instanceof RedoCommand);
//	}
//
//	/**
//	 * Make sure the UndoCommand is set for moving the tiles when the Backspace
//	 * key is pressed.
//	 */
//	@Test
//	public void testKeyUndo() {
//		handler.keyDown(null, Keys.BACKSPACE);
//		assertTrue(handler.getCommand() instanceof UndoCommand);
//	}
//}
