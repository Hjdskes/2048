package nl.tudelft.ti2206.gameobjects;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScoreDisplayTest {

//	/** The Batch to draw with. */
//	@Mock private static Batch batch;
//
//	/** The Sprite of the score tile. This is mocked. */
//	@Mock private static Sprite sprite;
//
//	/** The object under test. */
//	private static ScoreDisplay scores;
//	
//	private static BitmapFont font;
//
//	/**
//	 * Sets up the test environment, mocking all needed objects.
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//	}
//	
//	@Before
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//		sprite = mock(Sprite.class);
//		Skin skin = mock(Skin.class);
//		AssetHandler.setSkin(skin);
//		
//		TextureRegion region = mock(TextureRegion.class);
//		when(skin.getRegion(anyString())).thenReturn(region);
//		Texture texture = mock(Texture.class);
//		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
//		font = mock(BitmapFont.class);
//		when(skin.getFont(anyString())).thenReturn(font);
//
//		Grid grid = mock(Grid.class);
//		Label label = mock(Label.class);
//		LabelStyle style = mock(LabelStyle.class);
//		style.font = font;
//		label.setStyle(style);
//		when(skin.get(anyString(), eq(LabelStyle.class))).thenReturn(style);
//		scores = new ScoreDisplay(grid, label);
//	}
//
//	/**
//	 * Since ScoreDisplay consists mostly of private methods, only the draw
//	 * method can really be tested.
//	 */
//	@Test
//	public void testDraw() {
//		scores.draw(batch, 1);
//		verify(batch, times(3)).draw(eq(sprite), anyInt(), anyInt(), anyInt(),
//				anyInt());
//		verify(font, times(3)).draw(eq(batch), anyString(), anyInt(), anyInt());
//	}

}