package nl.tudelft.ti2206.buttons;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import nl.tudelft.ti2206.game.GameWorld;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A test class for the RestartButton.
 */
public class RestartButtonTest {
	/** A mock for the game world. */
	private static GameWorld world;
	/** A mock for the button drawer. */
	private static Batch batch;
	
	
	/** The object under test. */
	private static RestartButton button;

	/**
	 * Initializes all the mocks and creates the test object.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		button = new RestartButton(1, 2, 3, 4);
		batch = mock(Batch.class);
		world = mock(GameWorld.class);
	}

	/**
	 * Tests if the onClick method successfully calls the GameWorld.restart() method.
	 */
	@Test
	public void testOnClick() {
		button.onClick(world);
		verify(world).restart();
	}
	
	@Test
	public void testDraw() {
		button.draw(batch);
		verify(batch).draw(any(Sprite.class), eq(button.getX()),
				eq(button.getY()), eq(button.getWidth()),
				eq(button.getHeight()));
	}
}
