package nl.tudelft.ti2206.drawables;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.gameobjects.Tile;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DrawableTileTest {
	private DrawableTile tile;

	private Skin skin;

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
		tile = new DrawableTile(5, 0, skin, texture);
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
		int x = (int) tile.getX();
		int y = (int) tile.getY();
		assertEquals(x, 211);
		assertEquals(y, 307);
	}

	@Test
	public void testActSpawnNotEmpty() {
		tile.spawn();
		assertEquals(.6f, tile.getScaleX(), .01);
		tile.act(.15f);
		assertNotEquals(.6f, tile.getScaleX(), .01);
	}
	
	@Test
	public void testActSpawnEmpty() {
		Tile normal = new Tile(0, 0);
		normal.addObserver(tile);
		normal.notifyObservers();
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
