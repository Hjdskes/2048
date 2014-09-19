package nl.tudelft.ti2206.gameobjects;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OverlayDisplayTest {

	/** The Batch to draw with. */
	private static Batch batch;

	/** The Sprite of the score tile. This is mocked. */
	private static Sprite sprite;

	/** The mocked GameWorld. Used to set GameState */
	private static GameWorld world;

	/** The object under test. */
	private static OverlayDisplay overlays;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		batch = mock(Batch.class);
		sprite = mock(Sprite.class);
		AssetHandler.won = sprite;
		AssetHandler.lost = sprite;
		world = mock(GameWorld.class);

		overlays = new OverlayDisplay(world, true);
	}

	@Test
	public void testDrawWhenWon() {
		// reset to remove possible gamestate
		reset(world);
		// reset to remove possible draw invocation count
		reset(batch);

		when(world.isWon()).thenReturn(true);
		overlays.draw(batch);
		verify(batch).draw(eq(sprite), anyInt(), anyInt());
	}

	@Test
	public void testDrawWhenLost() {
		// reset to remove won state
		reset(world);
		// reset to reset draw invocation counter
		reset(batch);

		when(world.isLost()).thenReturn(true);
		overlays.draw(batch);
		verify(batch).draw(eq(sprite), anyInt(), anyInt());
	}

	@Test
	public void testDrawWhenRunning() {
		// reset to remove won state
		reset(world);
		// reset to reset draw invocation counter
		reset(batch);

		when(world.isRunning()).thenReturn(true);
		overlays.draw(batch);
		verify(batch, times(0)).draw(eq(sprite), anyInt(), anyInt());
	}
}
