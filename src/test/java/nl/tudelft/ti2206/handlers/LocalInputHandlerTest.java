package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Stack;

import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Input.Keys;

public class LocalInputHandlerTest {

	@Mock
	private Grid grid;
	@Mock
	private Tile tile;

	private LocalInputHandler handler;

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		handler = new LocalInputHandler(grid);
		Tile[] tiles = new Tile[16];
		for (int i = 0; i < 16; i++) {
			tiles[i] = tile;
		}
		when(grid.getTiles()).thenReturn(tiles);
		when(grid.getTileHandler()).thenReturn(new TileHandler(tiles));
		when(grid.getUndoStack()).thenReturn(new Stack<String>());
		when(grid.getRedoStack()).thenReturn(new Stack<String>());
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
		assertFalse(handler.keyDown(null, Keys.A));
		verifyNoMoreInteractions(grid);
	}
}
