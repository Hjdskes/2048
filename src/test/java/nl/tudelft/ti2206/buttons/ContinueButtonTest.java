package nl.tudelft.ti2206.buttons;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A test class for the ContinueButton.
 */
public class ContinueButtonTest {
	/** A mock for the game world. */
	private static GameWorld world;
	/** A mock for the sprite drawer. */
	private static Batch batch;

	/** The object under test. */
	private static ContinueButton button;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		button = new ContinueButton(1, 2, 3, 4);
		world = mock(GameWorld.class);
		batch = mock(Batch.class);
	}

	/**
	 * Tests if the onClick method successfully sets the game state.
	 */
	@Test
	public void testOnClick() {
		button.onClick(world);
		verify(world).setGameState(GameState.CONTINUING);
	}

	/**
	 * Tests if the button is drawn with the correct parameters.
	 */
	@Test
	public void testDraw() {
		button.draw(batch);
		verify(batch).draw(any(Sprite.class), eq(button.getX()),
				eq(button.getY()), eq(button.getWidth()),
				eq(button.getHeight()));
	}
}
