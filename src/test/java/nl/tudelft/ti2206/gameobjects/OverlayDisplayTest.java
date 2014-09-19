package nl.tudelft.ti2206.gameobjects;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.Game;
import nl.tudelft.ti2206.game.Game.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class OverlayDisplayTest {

	/** The Batch to draw with. */
	private static Batch batch;

	/** The Sprite of the score tile. This is mocked. */
	private static Texture texture;

	private static Skin skin;

	/** The object under test. */
	private static OverlayDisplay overlays;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		batch = mock(Batch.class);
		texture = mock(Texture.class);
		skin = mock(Skin.class);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
		AssetHandler.setSkin(skin);

		overlays = new OverlayDisplay();
	}

	@Test
	public void testDrawWhenWon() {
		// reset to remove possible draw invocation count
		reset(batch);

		Game.setState(GameState.WON);
		overlays.draw(batch, 1);
		verify(batch).draw(eq(texture), anyInt(), anyInt());
	}

	@Test
	public void testDrawWhenLost() {
		// reset to reset draw invocation counter
		reset(batch);

		Game.setState(GameState.LOST);
		overlays.draw(batch, 1);
		verify(batch).draw(eq(texture), anyInt(), anyInt());
	}

	@Test
	public void testDrawWhenRunning() {
		// reset to reset draw invocation counter
		reset(batch);

		Game.setState(GameState.RUNNING);
		overlays.draw(batch, 1);
		verify(batch, times(0)).draw(eq(texture), anyInt(), anyInt());
	}
}