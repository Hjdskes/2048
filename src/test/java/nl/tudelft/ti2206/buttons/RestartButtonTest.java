package nl.tudelft.ti2206.buttons;

import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameWorld;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * A test class for the RestartButton.
 */
public class RestartButtonTest {
	/** A mock for the game world. */
	private static GameWorld world;

	/** The object under test. */
	private static RestartButton button;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		button = new RestartButton(1, 2, 3, 4);
		world = Mockito.mock(GameWorld.class);
	}

	/**
	 * Tests if the onClick method successfully calls the GameWorld.restart() method.
	 */
	@Test
	public void testOnClick() {
		button.onClick(world);
		verify(world).restart();
	}
}
