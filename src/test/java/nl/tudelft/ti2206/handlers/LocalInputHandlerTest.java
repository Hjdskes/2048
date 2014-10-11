package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;

public class LocalInputHandlerTest {

	private Grid grid;
	
	private LocalInputHandler handler;

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		grid = new Grid(false);
		handler = new LocalInputHandler(grid);
	}

	@Test
	public void testKeyDownDOWN() {
		handler.keyDown(null, Keys.DPAD_DOWN);
		assertTrue(handler.getCommand() instanceof MoveDownCommand);
	}

	@Test
	public void testKeyDownUP() {
		handler.keyDown(null, Keys.DPAD_UP);
		assertTrue(handler.getCommand() instanceof MoveUpCommand);
	}

	@Test
	public void testKeyDownLEFT() {
		handler.keyDown(null, Keys.DPAD_LEFT);
		assertTrue(handler.getCommand() instanceof MoveLeftCommand);
	}

	@Test
	public void testKeyDownRIGHT() {
		handler.keyDown(null, Keys.DPAD_RIGHT);
		assertTrue(handler.getCommand() instanceof MoveRightCommand);
	}

	@Test
	public void testKeyDownNONE() {
		// make sure no action is taken on an invalid key
		assertFalse(handler.keyDown(null, Keys.A));
	}
}
