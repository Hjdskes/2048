package nl.tudelft.ti2206.utils.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.commands.MoveDownCommand;
import nl.tudelft.ti2206.utils.commands.MoveLeftCommand;
import nl.tudelft.ti2206.utils.commands.MoveRightCommand;
import nl.tudelft.ti2206.utils.commands.MoveUpCommand;
import nl.tudelft.ti2206.utils.input.InputHandler;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;

/**
 * Test class for the InputHandler.
 * 
 * @author group-21
 */
public class InputHandlerTest {
	/** The object under test. */
	private InputHandler inputHandler;
	/** The grid that is used for the input handler */
	private Grid grid;
	
	/**
	 * Initializes the test class.
	 */
	@Before
	public void setUp() {
		grid = new Grid(false);
		inputHandler = new InputHandler(grid);
	}

	/**
	 * Tests if the constructor successfully creates a new SimpleButton object.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(inputHandler);
	}

	/**
	 * Make sure the MoveDownCommand is set for moving the tiles when the down
	 * arrow key is pressed.
	 */
	@Test
	public void testKeyDownDown() {
		inputHandler.keyDown(null, Keys.DPAD_DOWN);
		assertTrue(inputHandler.getCommand() instanceof MoveDownCommand);
	}

	/**
	 * Make sure the MoveUpCommand is set for moving the tiles when the up arrow
	 * key is pressed.
	 */
	@Test
	public void testKeyDownUp() {
		inputHandler.keyDown(null, Keys.DPAD_UP);
		assertTrue(inputHandler.getCommand() instanceof MoveUpCommand);
	}

	/**
	 * Make sure the MoveLeftCommand is set for moving the tiles when the left
	 * arrow key is pressed.
	 */
	@Test
	public void testKeyDownLeft() {
		inputHandler.keyDown(null, Keys.DPAD_LEFT);
		assertTrue(inputHandler.getCommand() instanceof MoveLeftCommand);
	}

	/**
	 * Make sure the MoveRightCommand is set for moving the tiles when the right
	 * arrow key is pressed.
	 */
	@Test
	public void testKeyDownRight() {
		inputHandler.keyDown(null, Keys.DPAD_RIGHT);
		assertTrue(inputHandler.getCommand() instanceof MoveRightCommand);
	}

	/**
	 * Make sure the keyDown method returns false when the key is invalid.
	 */
	@Test
	public void testKeyDownInvalid() {
		assertEquals(false, inputHandler.keyDown(null, Keys.BACKSLASH));
	}
}
