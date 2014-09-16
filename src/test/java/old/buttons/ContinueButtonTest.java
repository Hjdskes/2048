package old.buttons;

import static org.mockito.Mockito.verify;
import old.buttons.ContinueButton;
import old.game.GameWorld;
import old.game.GameWorld.GameState;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A test class for the ContinueButton.
 * 
 * @author group-21
 */
public class ContinueButtonTest {
	/** A mock for the sprite object. */
	private static Sprite sprite;
	/** A mock for the game world. */
	private static GameWorld world;
	/** The object under test. */
	private static ContinueButton button;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		sprite = Mockito.mock(Sprite.class);
		button = new ContinueButton(1, 2, 3, 4, sprite, sprite);
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
