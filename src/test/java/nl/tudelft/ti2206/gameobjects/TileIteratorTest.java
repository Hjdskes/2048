package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TileIteratorTest {
	private Skin skin;
	private static TextureRegion texture;
	private Tile[] tiles;

	/** The object under test. */
	private TileIterator iterator;

	@Before
	public void setUp() {
		skin = mock(Skin.class);
		texture = mock(TextureRegion.class);
		LabelStyle style = new LabelStyle();
		style.font = mock(BitmapFont.class);
		when(skin.get(LabelStyle.class)).thenReturn(style);
		when(skin.getRegion(anyString())).thenReturn(texture);
		AssetHandler.getInstance().setSkin(skin);
		new HeadlessLauncher().launch();

		tiles = new Tile[16];
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i, i+1);
		}
		iterator = new TileIterator(tiles);
	}


	@Test
	public void testTileIterator() {
		assertNotNull(iterator);
	}

	@Test
	public void testHasNext() {
		assertTrue(iterator.hasNext());
		for (int i = 0; i < tiles.length; i++) {
			iterator.next();
		}
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testNext() {
		Tile t = iterator.next();
		assertEquals(1, t.getValue());
		assertEquals(0, t.getIndex());
	}

	@Test
	public void testReset() {
		for (int i = 0; i < tiles.length; i++) {
			iterator.next();
		}
		assertFalse(iterator.hasNext());
		iterator.reset();
		assertTrue(iterator.hasNext());
	}

	@Test
	public void testGetIndex() {
		int index = 0;
		for (int i = 0; i < 5; i++) {
			iterator.next();
		}
		index = iterator.next().getIndex();
		assertEquals(5, index);
	}
}
