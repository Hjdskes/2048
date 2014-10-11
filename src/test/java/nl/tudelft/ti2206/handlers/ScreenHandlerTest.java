package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Stack;

import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.screens.Screen;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class ScreenHandlerTest {
	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();
	
	private static Stack<Screen> screens;
	private static Screen screen;

	
	@BeforeClass
	public static void setUpBeforeClass() {
		Gdx.graphics = mock(Graphics.class);
		when(Gdx.graphics.getWidth()).thenReturn(0);
		when(Gdx.graphics.getHeight()).thenReturn(0);

		screens = new Stack<Screen>();
		screen = mock(Screen.class);
		screenHandler.setScreenStack(screens);
	}

	@Test
	public void testAdd() {
		assertTrue(screens.isEmpty());
		screenHandler.add(screen);
		assertFalse(screens.isEmpty());
		verify(screen).create();
		verify(screen).resize(anyInt(), anyInt());
	}

	@Test
	public void testDisposeNotEmpty() {
		screens.push(screen);
		int numScreens = screens.size();
		screenHandler.dispose();
		verify(screen, times(numScreens)).dispose();
		assertTrue(screens.isEmpty());
	}

	@Test
	public void testDisposeEmpty() {
		// Reset screen to remove any dispose counters already present.
		reset(screen);

		screens.clear();
		screenHandler.dispose();
		verify(screen, times(0)).dispose();
		assertTrue(screens.isEmpty());
	}

	@Test
	public void testDrawNotEmpty() {
		// Reset screen to remove any draw counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		screenHandler.draw();
		verify(screen, times(numScreens)).draw();
	}
	
	@Test
	public void testDrawEmpty() {
		// Reset screen to remove any draw counters already present.
		reset(screen);
		
		screens.clear();
		screenHandler.draw();
		verify(screen, times(0)).draw();
	}

	@Test
	public void testPauseEmpty() {
		// Reset screen to remove any pause counters already present.
		reset(screen);
		
		screens.clear();
		screenHandler.pause();
		verify(screen, times(0)).pause();
	}
	
	@Test
	public void testPauseNotEmpty(){
		// Reset screen to remove any pause counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		screenHandler.pause();
		verify(screen, times(numScreens)).pause();
	}

	@Test
	public void testResizeEmpty() {
		// Reset screen to remove any resize counters already present.
		reset(screen);
		
		screens.clear();
		screenHandler.resize(0, 0);
		verify(screen, times(0)).resize(0, 0);
	}
	
	@Test
	public void testResizeNonEmpty() {
		// Reset screen to remove any resize counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		screenHandler.resize(0, 0);
		verify(screen, times(numScreens)).resize(0, 0);
	}

	@Test
	public void testResumeEmpty() {
		// Reset screen to remove any resume counters already present.
		reset(screen);
		
		screens.clear();
		screenHandler.resume();
		verify(screen, times(0)).resume();
	}
	
	@Test
	public void testResumeNonEmpty() {
		// Reset screen to remove any resume counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		screenHandler.resume();
		verify(screen, times(numScreens)).resume();
	}
	
	@Test
	public void testUpdateNotNullScreen() {
		
		screens.push(screen);
		screenHandler.update();
		verify(screen).update();
	}

	@Test
	public void testRemove() {
		// Reset screen to remove any dispose counters already present.
		reset(screen);
		
		screenHandler.remove(screen);
		verify(screen).dispose();
		assertFalse(screens.contains(screen));
	}
	
	@Test
	public void testRemoveTopWhen1Screen() {
		// reset the screen stack and add 1 screen to it
		screens.clear();
		screenHandler.add(screen);
		// Reset the screen to remove any method invocation counters
		reset(screen);
		
		screenHandler.removeTop();
		// make sure nothing happens
		verify(screen).restart();
	}
	
	@Test
	public void testRemoveTopWhenMoreScreens() {
		screens.clear();
		screens.add(screen);
		screens.add(screen);
		
		screenHandler.removeTop();
		verify(screen).resume();
	}
	
	@Test
	public void testGetNegative() {
		assertNull(screenHandler.get(-1));
	}
	
	@Test
	public void testGetTooLarge() {
		assertNull(screenHandler.get(19000));
	}
	
	@Test
	public void testGetCorrect() {
		screens.add(screen);
		assertEquals(screen, screenHandler.get(0));
	}
}
