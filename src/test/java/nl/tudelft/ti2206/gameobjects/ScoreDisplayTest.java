package nl.tudelft.ti2206.gameobjects;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class ScoreDisplayTest {

	/** The Batch to draw with. */
	private static Batch batch;

	/** The Sprite of the score tile. This is mocked. */
	private static Sprite sprite;

	/** The object under test. */
	private static ScoreDisplay scores;

	/**
	 * Sets up the test environment, mocking all needed objects.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sprite = mock(Sprite.class);
		AssetHandler.score = sprite;
		AssetHandler.highscore = sprite;
		AssetHandler.highest = sprite;
		AssetHandler.font = mock(BitmapFont.class);
		TextBounds bounds = mock(TextBounds.class);
		when(AssetHandler.font.getBounds(anyString())).thenReturn(bounds);
		bounds.width = 20;
		batch = mock(Batch.class);
		scores = new ScoreDisplay(new GameWorld(), true);

	}

	/**
	 * Since ScoreDisplay consists mostly of private methods, only the draw
	 * method can really be tested.
	 */
	@Test
	public void testDraw() {
		scores.draw(batch);
		verify(batch, times(3)).draw(eq(sprite), anyInt(), anyInt(), anyInt(),
				anyInt());
		verify(AssetHandler.font, times(3)).draw(eq(batch), anyString(),
				anyInt(), anyInt());
	}

}
