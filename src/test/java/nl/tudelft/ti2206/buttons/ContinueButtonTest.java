package nl.tudelft.ti2206.buttons;

import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * A test class for the ContinueButton.
 */
public class ContinueButtonTest {
	/** A mock for the game world. */
	private static GameWorld world;

	/** The object under test. */
	private static ContinueButton button;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		button = new ContinueButton(1, 2, 3, 4);
		world = Mockito.mock(GameWorld.class);
	}

	/**
	 * Tests if the onClick method successfully sets the game state.
	 */
	@Test
	public void testOnClick() {
		button.onClick(world);
		verify(world).setGameState(GameState.CONTINUING);
	}
}
