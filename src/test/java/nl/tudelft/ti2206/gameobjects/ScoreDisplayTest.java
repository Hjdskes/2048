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
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScoreDisplayTest {

	/** The Batch to draw with. */
	private static Batch batch;

	/** The Sprite of the score tile. This is mocked. */
	@Mock private static Sprite sprite;
	
	/** The TextureRegion of the score tile. */
	private static TextureRegion region;
	
	/** The Label of the score tile. */
	private static Label label;

	/** The object under test. */
	private static ScoreDisplay scores;
	
	private static BitmapFont font;

	/**
	 * Sets up the test environment, mocking all needed objects.
	 * @throws InterruptedException 
	 */	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		sprite = mock(Sprite.class);
		Skin skin = mock(Skin.class);
		AssetHandler.setSkin(skin);
		
		region = mock(TextureRegion.class);
		when(skin.getRegion(anyString())).thenReturn(region);
		
		Texture texture = mock(Texture.class);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
		
		font = mock(BitmapFont.class);
		when(skin.getFont(anyString())).thenReturn(font);

		Grid grid = mock(Grid.class);
		label = mock(Label.class);
		LabelStyle style = mock(LabelStyle.class);
		style.font = font;
		label.setStyle(style);
		when(skin.get(anyString(), eq(LabelStyle.class))).thenReturn(style);
		
		float[] values = new float[16];
		for(int i = 0; i < 16; i++){
			values[i] = 2;
		}
		Matrix4 matrix = new Matrix4(values);
		batch = mock(Batch.class);
		when(batch.getTransformMatrix()).thenReturn(matrix);
		
		scores = new ScoreDisplay(grid, label);
		
	}

	/**
	 * Since ScoreDisplay consists mostly of private methods, only the draw
	 * method can really be tested.
	 */
	@Test
	public void testDraw() {
		scores.draw(batch, 1);
		verify(batch, times(3)).draw(eq(region), anyInt(), anyInt());
		//verify(font, times(3)).draw(eq(batch), anyString(), anyInt(), anyInt());
	}
	
	/**
	 * Tests if act also invokes the act methods of the three different labels.
	 */
	@Test
	public void testAct() {
		scores.act(0);
		verify(label, times(3)).act(0);
	}

}