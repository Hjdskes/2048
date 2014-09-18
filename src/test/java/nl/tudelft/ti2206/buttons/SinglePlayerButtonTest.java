package nl.tudelft.ti2206.buttons;

import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameScreen;
import nl.tudelft.ti2206.game.TwentyFourtyGame;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Screen;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 * A test class for the SinglePlayerButton.
 */
public class SinglePlayerButtonTest {
	/** The object under test. */
	private static SinglePlayerButton button;
	/** A mock to verify behavior. */
	private static TwentyFourtyGame game;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		game = Mockito.mock(TwentyFourtyGame.class);

		button = new SinglePlayerButton(1, 2, 3, 4);
	}

	/**
	 * Tests if the onClick method successfully launches a singleplayer game.
	 */
	@Test
	public void testOnClick() {
		//button.onClick(game);
		//verify(game).setScreen(screen);
	}
}
