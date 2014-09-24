package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Input.Keys;

public class LocalInputHandlerTest {

	@Mock
	private Grid grid;

	private LocalInputHandler handler;

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		handler = new LocalInputHandler(grid);
	}

	@Test
	public void testKeyDownDOWN() {
		handler.keyDown(null, Keys.DPAD_DOWN);
		verify(grid).move(Direction.DOWN);
	}

	@Test
	public void testKeyDownUP() {
		handler.keyDown(null, Keys.DPAD_UP);
		verify(grid).move(Direction.UP);
	}

	@Test
	public void testKeyDownLEFT() {
		handler.keyDown(null, Keys.DPAD_LEFT);
		verify(grid).move(Direction.LEFT);
	}

	@Test
	public void testKeyDownRIGHT() {
		handler.keyDown(null, Keys.DPAD_RIGHT);
		verify(grid).move(Direction.RIGHT);
	}

	@Test
	public void testKeyDownNONE() {
		assertFalse(handler.keyDown(null, Keys.A));
		verifyNoMoreInteractions(grid);
	}
}
