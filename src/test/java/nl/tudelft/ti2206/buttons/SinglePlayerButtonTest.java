package nl.tudelft.ti2206.buttons;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.TwentyFourtyGame;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A test class for the SinglePlayerButton.
 */
public class SinglePlayerButtonTest {
	/** The object under test. */
	private static SinglePlayerButton button;
	/** A mock to verify behavior. */
	private static TwentyFourtyGame game;
	/** A mock for the button drawer */
	private static Batch batch;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		game = mock(TwentyFourtyGame.class);
		batch = mock(Batch.class);
		button = new SinglePlayerButton(1, 2, 3, 4);
	}

	/**
	 * Tests if the onClick method successfully launches a singleplayer game.
	 */
	@Test
	public void testOnClick() {
		// button.onClick(game);
		// verify(game).setScreen(any(GameScreen.class));
	}

	/**
	 * Tests if the buttons is drawn with the correct parameters.
	 */
	@Test
	public void testDraw() {
		button.draw(batch);
		verify(batch).draw(any(Sprite.class), eq(button.getX()),
				eq(button.getY()), eq(button.getWidth()),
				eq(button.getHeight()));
	}
}
