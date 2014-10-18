package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.commands.MoveDownCommand;
import nl.tudelft.ti2206.utils.commands.MoveLeftCommand;
import nl.tudelft.ti2206.utils.commands.MoveRightCommand;
import nl.tudelft.ti2206.utils.commands.MoveUpCommand;
import nl.tudelft.ti2206.utils.input.LocalInputHandler;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;

public class LocalInputHandlerTest {

	/** The local Grid on which a move must be conducted. */
	private Grid grid;
	/** The remoteInputHandler which takes remote input and we are going to test. */
	private LocalInputHandler handler;

	/**
	 * Launches a headless launcher and creates the necessary objects before
	 * each test.
	 */
	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		grid = new Grid(false);
		handler = new LocalInputHandler(grid);
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testKeyDownDOWN() {
		handler.keyDown(null, Keys.DPAD_DOWN);
		assertTrue(handler.getCommand() instanceof MoveDownCommand);
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testKeyDownUP() {
		handler.keyDown(null, Keys.DPAD_UP);
		assertTrue(handler.getCommand() instanceof MoveUpCommand);
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testKeyDownLEFT() {
		handler.keyDown(null, Keys.DPAD_LEFT);
		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
	}

	/**
	 * Checks if the current command is correctly set.
	 */
	@Test
	public void testKeyDownRIGHT() {
		handler.keyDown(null, Keys.DPAD_RIGHT);
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
	}

	/**
	 * Checks if nothing happens when an invalid key is pressed.
	 */
	@Test
	public void testKeyDownNONE() {
		assertFalse(handler.keyDown(null, Keys.A));
	}
}
