package nl.tudelft.ti2206.buttons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A test class for the Button class.
 */
public class ButtonTest {
	/** The object under test. */
	private static Button button;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		button = new Button(1, 2, 3, 4);
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
	
	@Test
	public void testGetX() {
		assertTrue(Float.compare(1, button.getX()) == 0);
	}
	
	@Test
	public void testGetY() {
		assertTrue(Float.compare(2, button.getY()) == 0);
	}
	
	@Test
	public void testGetWidth() {
		assertTrue(Float.compare(3, button.getWidth()) == 0);
	}
	
	@Test
	public void testGetHeight() {
		assertTrue(Float.compare(4, button.getHeight()) == 0);
	}
}
