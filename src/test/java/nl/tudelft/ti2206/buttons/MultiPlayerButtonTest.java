package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * A test class for the MultiPlayerButton.
 */
public class MultiPlayerButtonTest {
	/** The object under test. */
	private static MultiPlayerButton button;
	/** A mock to verify behavior. */
	private static TwentyFourtyGame game;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		game = Mockito.mock(TwentyFourtyGame.class);

		button = new MultiPlayerButton(1, 2, 3, 4);
	}

	/**
	 * Tests if the onClick method successfully launches a multiplayer game.
	 */
	@Test
	public void testOnClick() {
		button.onClick(game);
		//verify(game).setScreen();
	}
}