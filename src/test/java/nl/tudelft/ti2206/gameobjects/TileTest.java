package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Test class for the Tile class.
 * 
 * @author group-21
 */
public class TileTest {
	private Skin skin;

	/** The object under test. */
	private Tile tile;

	/** TextureRegion object which we will mock. */
	private static TextureRegion texture;

	/** Batch object which will be mocked to test the draw method. */
	private static Batch batch;

	/**
	 * Initializes the test object.
	 */
	@Before
	public void setup() {	
		skin = mock(Skin.class);
		batch = mock(Batch.class);
		texture = mock(TextureRegion.class);
		LabelStyle style = new LabelStyle();
		style.font = mock(BitmapFont.class);
		when(skin.get(LabelStyle.class)).thenReturn(style);
		when(skin.getRegion(anyString())).thenReturn(texture);
		AssetHandler.getInstance().setSkin(skin);
		new HeadlessLauncher().launch();
		tile = new Tile(0, 0, skin, texture);
	}

	/**
	 * Tests if the constructor successfully creates a new Tile object.
	 */
	@Test
	public void testConstructor() {
		assertNotNull(tile);
	}

	/**
	 * Tests if getValue() correctly returns the current value.
	 */
	@Test
	public void testGetValue() {
		tile.setValue(2048);
		assertEquals(2048, tile.getValue());
	}

	/**
	 * Tests if setValue() correctly sets the value.
	 */
	@Test
	public void testSetValue() {
		tile.setValue(10);
		assertEquals(10, tile.getValue());
	}

	/**
	 * Tests if isEmpty() correctly returns true if the tile is empty.
	 */
	@Test
	public void testIsEmptyTrue() {
		assertEquals(true, tile.isEmpty());
	}

	/**
	 * Tests if isEmpty() correctly returns false if the tile is not empty.
	 */
	@Test
	public void testIsEmptyFalse() {
		tile.setValue(10);
		assertEquals(false, tile.isEmpty());
	}

	/**
	 * Tests if isMerged() correctly returns false if the tile has not merged.
	 */
	@Test
	public void testIsMerged() {
		assertEquals(false, tile.isMerged());
	}

	/**
	 * Tests if testMerged() correctly sets the isMerged variable.
	 */
	@Test
	public void testSetMerged() {
		tile.setMerged(true);
		assertEquals(true, tile.isMerged());
	}

	/**
	 * Tests if doubleValue() correctly doubles the value of the tile.
	 */
	@Test
	public void testDoubleValue() {
		tile.setValue(1);
		tile.doubleValue();
		assertEquals(2, tile.getValue());
	}

	/**
	 * Tests if the width and height is returned correctly.
	 */
	@Test
	public void testWidthHeight() {
		int i = 81;
		int width = (int) tile.getWidth();
		int heigth = (int) tile.getHeight();
		assertEquals(i, width);
		assertEquals(i, heigth);
	}

	/**
	 * Tests if the getter of x and y are behaving correctly.
	 */
	@Test
	public void testGetXY() {
		tile.setIndex(5);
		int x = (int) tile.getX();
		int y = (int) tile.getY();
		assertEquals(x, 211);
		assertEquals(y, 307);

		tile.setIndex(14);
		x = (int) tile.getX();
		y = (int) tile.getY();
		assertEquals(x, 307);
		assertEquals(y, 115);

		tile.setIndex(0);
		x = (int) tile.getX();
		y = (int) tile.getY();
		assertEquals(x, 115);
		assertEquals(y, 403);

		tile.setIndex(11);
		x = (int) tile.getX();
		y = (int) tile.getY();
		assertEquals(x, 403);
		assertEquals(y, 211);
	}

	@Test
	public void testActSpawnNotEmpty() {
		tile.setValue(4);
		tile.spawn();
		assertEquals(.6f, tile.getScaleX(), .01);
		tile.act(.15f);
		assertNotEquals(.6f, tile.getScaleX(), .01);
	}
	
	@Test
	public void testActSpawnEmpty() {
		tile.setValue(0);
		tile.spawn();
		assertEquals(.6f, tile.getScaleX(), .01);
		tile.act(.15f);
		assertEquals(1, tile.getScaleX(), .01);
	}
	
	@Test
	public void testActMerge() {
		tile.merge();
		assertEquals(1.4f, tile.getScaleX(), .01);
		tile.act(.15f);
		assertNotEquals(1.4f, tile.getScaleX(), .01);
	}
	
	/**
	 * Tests if the draw calls the draw method of Batch.class.
	 */
	@Test
	public void testDraw() {
		tile.draw(batch, 1);
		verify(batch).draw(eq(texture), anyInt(), anyInt(), anyInt(), anyInt(),
				anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
	}
}
