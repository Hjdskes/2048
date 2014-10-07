package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.handlers.InputHandler;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Input.Keys;

/**
 * Test class for the InputHandler.
 * 
 * @author group-21
 */
public class InputHandlerTest {
	/** The object under test. */
	private static InputHandler handler;
	/** A mock to verify behavior. */
	private static Grid grid;
	/** A mock to for the getTiles() */
	private static Tile tile;

	/**
	 * Initialize all mocks and creates a new InputHandler.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		grid = Mockito.mock(Grid.class);
		handler = new InputHandler(grid);
		tile = Mockito.mock(Tile.class);
		Tile[] tiles = new Tile[16];
		for (int i = 0; i < 16; i++) {
			tiles[i] = tile;
		}
		when(grid.getTiles()).thenReturn(tiles);
		when(grid.getTileHandler()).thenReturn(new TileHandler(tiles));
	}

	/**
	 * Tests if the constructor successfully creates a new SimpleButton object.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(handler);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * down arrow key is pressed.
	 */
	@Test
	public void testKeyDownDown() {
		handler.keyDown(null, Keys.DPAD_DOWN);
		assertTrue(handler.getCommand() instanceof MoveDownCommand);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * up arrow key is pressed.
	 */
	@Test
	public void testKeyDownUp() {
		handler.keyDown(null, Keys.DPAD_UP);
		assertTrue(handler.getCommand() instanceof MoveUpCommand);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * left arrow key is pressed.
	 */
	@Test
	public void testKeyDownLeft() {
		handler.keyDown(null, Keys.DPAD_LEFT);
		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * right arrow key is pressed.
	 */
	@Test
	public void testKeyDownRight() {
		handler.keyDown(null, Keys.DPAD_RIGHT);
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
	}

	/**
	 * Make sure the keyDown method returns false when the key is invalid.
	 */
	@Test
	public void testKeyDownInvalid() {
		assertEquals(false, handler.keyDown(null, Keys.BACKSLASH));
	}
}
