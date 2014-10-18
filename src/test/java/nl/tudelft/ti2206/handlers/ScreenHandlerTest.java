package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class ScreenHandlerTest {
	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Screen needed for testing, we are going to mock this. */
	private static Screen screen;

	/**
	 * Set up before the tests. We mock all the graphics and the screen.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		Gdx.graphics = mock(Graphics.class);
		when(Gdx.graphics.getWidth()).thenReturn(0);
		when(Gdx.graphics.getHeight()).thenReturn(0);

		screen = mock(Screen.class);
		screenHandler.set(screen);
	}

	/**
	 * Checks if the ScreenHandler correctly sets the screen by verifying that
	 * the proper methods of screen are called.
	 */
	@Test
	public void testSet() {
		// Reset screen to remove any interaction counters already present.
		reset(screen);
		screenHandler.set(screen);
		verify(screen).create();
		verify(screen).resize(anyInt(), anyInt());
	}

	/**
	 * Checks if the ScreenHandler correctly disposes the screen by verifying
	 * that the proper methods of screen are called.
	 */
	@Test
	public void testDispose() {
		// Reset screen to remove any interaction counters already present.
		reset(screen);
		screenHandler.dispose();
		verify(screen).dispose();
	}

	/**
	 * Checks if the ScreenHandler correctly draws the screen by verifying that
	 * the proper methods of screen are called.
	 */
	@Test
	public void testDraw() {
		screenHandler.draw();
		verify(screen).draw();
	}

	/**
	 * Checks if the ScreenHandler correctly pauses the screen by verifying that
	 * the proper methods of screen are called.
	 */
	@Test
	public void testPause() {
		screenHandler.pause();
		verify(screen).pause();
	}

	/**
	 * Checks if the ScreenHandler correctly resizes the screen by verifying
	 * that the proper methods of screen are called.
	 */
	@Test
	public void testResize() {
		screenHandler.resize(0, 0);
		verify(screen).resize(0, 0);
	}

	/**
	 * Checks if the ScreenHandler correctly resumes the screen by verifying
	 * that the proper methods of screen are called.
	 */
	@Test
	public void testResume() {
		screenHandler.resume();
		verify(screen).resume();
	}

	/**
	 * Checks if the ScreenHandler correctly updates the screen by verifying
	 * that the proper methods of screen are called.
	 */
	@Test
	public void testUpdate() {
		screenHandler.update();
		verify(screen).update();
	}

	/**
	 * Checks if the ScreenHandler correctly restarts the screen by verifying
	 * that the proper methods of screen are called.
	 */
	@Test
	public void testRestart() {
		screenHandler.restart();
		verify(screen).restart();
	}

	/**
	 * Checks if the getter of screen does indeed return the current screen.
	 */
	@Test
	public void testGetScreen() {
		assertEquals(screen, screenHandler.getScreen());
	}
}
