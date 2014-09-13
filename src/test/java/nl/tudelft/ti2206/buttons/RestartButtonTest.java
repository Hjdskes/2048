package nl.tudelft.ti2206.buttons;

import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameWorld;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A test class for the RestartButton.
 * 
 * @author group-21
 */
public class RestartButtonTest {
	/** A mock for the sprite object. */
	private static Sprite sprite;
	/** A mock for the game world. */
	private static GameWorld world;
	/** The object under test. */
	private static RestartButton button;

	/**
	 * Initializes all the mocks and create the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sprite = Mockito.mock(Sprite.class);
		button = new RestartButton(1, 2, 3, 4, sprite, sprite);
		world = Mockito.mock(GameWorld.class);
	}

	/**
	 * Tests if the onClick method successfully calls the world.restart method.
	 */
	@Test
	public void testOnClick() {
		button.onClick(world);
		verify(world).restart();
	}
}
