package nl.tudelft.ti2206.buttons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameWorld;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A test class for the SimpleButton.
 */
public class SimpleButtonTest {
	/** A mock for the sprite object. */
	private static Sprite sprite;

	/** The object under test. */
	private static SimpleButton button;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		sprite = Mockito.mock(Sprite.class);
		button = new SimpleButton(1, 2, 3, 4, sprite, sprite) {
			@Override
			public void onClick(GameWorld world) {
			}
		};
	}

	/**
	 * Tests if the constructor successfully creates a new SimpleButton object.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(button);
	}

	/**
	 * Tests if the button reports being clicked if it gets the right
	 * coordinates.
	 */
	@Test
	public void testIsTouchDown() {
		assertTrue(button.isTouchDown(2, 3));
	}

	/**
	 * Tests if the button reports not being clicked if it gets the wrong
	 * coordinates.
	 */
	@Test
	public void testIsTouchDownFalse() {
		assertFalse(button.isTouchDown(20, 30));
	}

	/**
	 * Tests if the button reports being released if it gets the right
	 * coordinates.
	 */
	@Test
	public void testIsTouchUp() {
		assertTrue(button.isTouchUp(2, 3));
	}

	/**
	 * Tests if the button reports not being released if it gets the wrong
	 * coordinates.
	 */
	@Test
	public void testIsTouchUpFalse() {
		assertFalse(button.isTouchUp(20, 30));
	}

	/**
	 * Tests if the draw method is called correctly when the button is not
	 * pressed.
	 */
	@Test
	public void testDrawNotPressed() {
		SpriteBatch batcher = Mockito.mock(SpriteBatch.class);
		button.isPressed = false;
		button.draw(batcher);
		verify(batcher).draw(sprite, 1, 2, 3, 4);
	}

	/**
	 * Tests if the draw method is called correctly when the button is pressed.
	 */
	@Test
	public void testDrawPressed() {
		SpriteBatch batcher = Mockito.mock(SpriteBatch.class);
		button.isPressed = true;
		button.draw(batcher);
		verify(batcher).draw(sprite, 1, 2, 3, 4);
	}
}
