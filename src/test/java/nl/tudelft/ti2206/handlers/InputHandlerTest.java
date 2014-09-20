package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
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

	/**
	 * Initialize all mocks and creates a new InputHandler.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		grid = Mockito.mock(Grid.class);

		handler = new InputHandler(grid);
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
		verify(grid).move(Direction.DOWN);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * up arrow key is pressed.
	 */
	@Test
	public void testKeyDownUp() {
		handler.keyDown(null, Keys.DPAD_UP);
		verify(grid).move(Direction.UP);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * left arrow key is pressed.
	 */
	@Test
	public void testKeyDownLeft() {
		handler.keyDown(null, Keys.DPAD_LEFT);
		verify(grid).move(Direction.LEFT);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * right arrow key is pressed.
	 */
	@Test
	public void testKeyDownRight() {
		handler.keyDown(null, Keys.DPAD_RIGHT);
		verify(grid).move(Direction.RIGHT);
	}

	/**
	 * Make sure the keyDown method returns false when the key is invalid.
	 */
	@Test
	public void testKeyDownInvalid() {
		assertEquals(false, handler.keyDown(null, Keys.BACKSLASH));
	}
}
