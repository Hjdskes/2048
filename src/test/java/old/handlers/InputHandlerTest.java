package old.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import old.game.GameWorld;
import old.gameobjects.AnimatedGrid;
import old.gameobjects.Grid.Direction;
import old.handlers.InputHandler;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Input.Buttons;
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
	private static GameWorld world;
	/** A mock to verify behavior. */
	private static AnimatedGrid grid;

	/**
	 * Initialize all mocks and creates a new InputHandler.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		world = Mockito.mock(GameWorld.class);
		grid = Mockito.mock(AnimatedGrid.class);
		when(world.getGrid()).thenReturn(grid);

		handler = new InputHandler(world);
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
		handler.keyDown(Keys.DPAD_DOWN);
		verify(grid).move(Direction.DOWN);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * up arrow key is pressed.
	 */
	@Test
	public void testKeyDownUp() {
		handler.keyDown(Keys.DPAD_UP);
		verify(grid).move(Direction.UP);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * left arrow key is pressed.
	 */
	@Test
	public void testKeyDownLeft() {
		handler.keyDown(Keys.DPAD_LEFT);
		verify(grid).move(Direction.LEFT);
	}

	/**
	 * Make sure the move method is called with the correct parameter when the
	 * right arrow key is pressed.
	 */
	@Test
	public void testKeyDownRight() {
		handler.keyDown(Keys.DPAD_RIGHT);
		verify(grid).move(Direction.RIGHT);
	}

	/**
	 * Make sure the keyDown method returns false when the key is invalid.
	 */
	@Test
	public void testKeyDownInvalid() {
		assertEquals(false, handler.keyDown(Keys.BACKSLASH));
	}

	/**
	 * Make sure the keyUp method returns false.
	 */
	@Test
	public void testKeyUp() {
		assertEquals(false, handler.keyUp(Keys.BACKSLASH));
	}

	/**
	 * Make sure the keyTyped method returns false.
	 */
	@Test
	public void testKeyTyped() {
		assertEquals(false, handler.keyTyped('a'));
	}

	/**
	 * Make sure the mouseMoved method returns false.
	 */
	@Test
	public void testMouseMoved() {
		assertEquals(false, handler.mouseMoved(2, 3));
	}

	/**
	 * Make sure the scrolled method returns false.
	 */
	@Test
	public void testScrolled() {
		assertEquals(false, handler.scrolled(5));
	}

//	@Test
//	public void testTouchDown() {
//		handler.touchDown(0, 0, 0, Buttons.LEFT);
//	}

	/**
	 * Make sure the touchDragged method returns false.
	 */
	@Test
	public void testTouchDragged() {
		assertEquals(false, handler.touchDragged(2, 3, 5));
	}

	/**
	 * Make sure the touchUp method returns false.
	 */
	@Test
	public void testTouchUp() {
		assertEquals(false, handler.touchUp(2, 3, 5, Buttons.LEFT));
	}
}
