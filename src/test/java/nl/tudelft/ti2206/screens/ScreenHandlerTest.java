package nl.tudelft.ti2206.screens;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class ScreenHandlerTest {

	private static Stack<Screen> screens;
	private static Screen screen;

	@BeforeClass
	public static void setUpBeforeClass() {
		Gdx.graphics = mock(Graphics.class);
		when(Gdx.graphics.getWidth()).thenReturn(0);
		when(Gdx.graphics.getHeight()).thenReturn(0);

		screens = new Stack<Screen>();
		screen = mock(Screen.class);
		ScreenHandler.setScreenStack(screens);
	}

	@Test
	public void testAdd() {
		assertTrue(screens.isEmpty());
		ScreenHandler.add(screen);
		assertFalse(screens.isEmpty());
		verify(screen).create();
		verify(screen).resize(anyInt(), anyInt());
	}

	@Test
	public void testDisposeNotEmpty() {
		screens.push(screen);
		int numScreens = screens.size();
		ScreenHandler.dispose();
		verify(screen, times(numScreens)).dispose();
		assertTrue(screens.isEmpty());
	}

	@Test
	public void testDisposeEmpty() {
		// Reset screen to remove any dispose counters already present.
		reset(screen);

		screens.clear();
		ScreenHandler.dispose();
		verify(screen, times(0)).dispose();
		assertTrue(screens.isEmpty());
	}

	@Test
	public void testDrawNotEmpty() {
		// Reset screen to remove any draw counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		ScreenHandler.draw();
		verify(screen, times(numScreens)).draw();
	}
	
	@Test
	public void testDrawEmpty() {
		// Reset screen to remove any draw counters already present.
		reset(screen);
		
		screens.clear();
		ScreenHandler.draw();
		verify(screen, times(0)).draw();
	}

	@Test
	public void testPauseEmpty() {
		// Reset screen to remove any pause counters already present.
		reset(screen);
		
		screens.clear();
		ScreenHandler.pause();
		verify(screen, times(0)).pause();
	}
	
	@Test
	public void testPauseNotEmpty(){
		// Reset screen to remove any pause counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		ScreenHandler.pause();
		verify(screen, times(numScreens)).pause();
	}

	@Test
	public void testResizeEmpty() {
		// Reset screen to remove any resize counters already present.
		reset(screen);
		
		screens.clear();
		ScreenHandler.resize(0, 0);
		verify(screen, times(0)).resize(0, 0);
	}
	
	@Test
	public void testResizeNonEmpty() {
		// Reset screen to remove any resize counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		ScreenHandler.resize(0, 0);
		verify(screen, times(numScreens)).resize(0, 0);
	}

	@Test
	public void testResumeEmpty() {
		// Reset screen to remove any resume counters already present.
		reset(screen);
		
		screens.clear();
		ScreenHandler.resume();
		verify(screen, times(0)).resume();
	}
	
	@Test
	public void testResumeNonEmpty() {
		// Reset screen to remove any resume counters already present.
		reset(screen);
		
		screens.push(screen);
		int numScreens = screens.size();
		ScreenHandler.resume();
		verify(screen, times(numScreens)).resume();
	}

	@Test
	public void testUpdateNullScreen() {
		screens.push(null);
		assertTrue(screens.size() == 1);
		ScreenHandler.update();
		assertTrue(screens.size() == 0);
	}
	
	@Test
	public void testUpdateNotNullScreen() {
		
		screens.push(screen);
		int numScreens = screens.size();
		ScreenHandler.update();
		verify(screen, times(numScreens)).update();
	}

	@Test
	public void testRemove() {
		// Reset screen to remove any dispose counters already present.
		reset(screen);
		
		ScreenHandler.remove(screen);
		verify(screen).dispose();
		assertFalse(screens.contains(screen));
	}
	
}
