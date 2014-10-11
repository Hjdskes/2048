package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.screens.Screen;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class ScreenHandlerTest {
	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	private static Screen screen;

	
	@BeforeClass
	public static void setUpBeforeClass() {
		Gdx.graphics = mock(Graphics.class);
		when(Gdx.graphics.getWidth()).thenReturn(0);
		when(Gdx.graphics.getHeight()).thenReturn(0);

		screen = mock(Screen.class);
		screenHandler.set(screen);
	}

	@Test
	public void testSet() {
		// Reset screen to remove any interaction counters already present.
		reset(screen);
		screenHandler.set(screen);
		verify(screen).create();
		verify(screen).resize(anyInt(), anyInt());
	}

	@Test
	public void testDispose() {
		// Reset screen to remove any interaction counters already present.
		reset(screen);
		screenHandler.dispose();
		verify(screen).dispose();
	}
	
	@Test
	public void testDraw() {
		screenHandler.draw();
		verify(screen).draw();
	}

	@Test
	public void testPause() {		
		screenHandler.pause();
		verify(screen).pause();
	}

	@Test
	public void testResize() {
		screenHandler.resize(0, 0);
		verify(screen).resize(0, 0);
	}

	@Test
	public void testResume() {
		screenHandler.resume();
		verify(screen).resume();
	}
	
	@Test
	public void testUpdate() {
		screenHandler.update();
		verify(screen).update();
	}
	
	@Test
	public void testRestart() {
		screenHandler.restart();
		verify(screen).restart();
	}
	
	@Test
	public void testGetScreen() {
		assertEquals(screen, screenHandler.getScreen());
	}
}
