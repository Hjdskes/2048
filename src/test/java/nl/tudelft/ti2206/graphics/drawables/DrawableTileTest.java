package nl.tudelft.ti2206.graphics.drawables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.graphics.drawables.DrawableTile;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DrawableTileTest {
	private DrawableTile tile;

	@Mock
	private Skin skin;

	/** TextureRegion object which we will mock. */
	@Mock
	private static TextureRegion texture;

	/** Batch object which will be mocked to test the draw method. */
	@Mock
	private static Batch batch;

	/**
	 * Initializes the test object.
	 */
	@Before
	public void setup() {	
		MockitoAnnotations.initMocks(this);
		AssetHandler.getInstance().setSkin(skin);
		tile = new DrawableTile(5, 0, texture);
	}

	@Test
	public void testUpdate() {
		Tile normal = new Tile(0, 0);
		normal.addObserver(tile);
		normal.setValue(2048);
		normal.setIndex(15);
		normal.merge();
		normal.spawn();
		normal.move(0);
	
		// update the DrawableTile
		normal.notifyObservers();
		
		assertEquals(0, tile.getIndex());
		assertEquals(2048, tile.getValue());
		assertEquals(3, tile.getActions().size);	
	}
	
	@Test
	public void testFinishActions() {
		Tile normal = new Tile(0, 4);
		normal.addObserver(tile);
		normal.merge();
		normal.move(2);
		normal.spawn();
		assertEquals(3, tile.getActions().size);
		normal.setValue(0);
		tile.act(.15f);
		assertEquals(0, tile.getActions().size);	
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
		Tile normal = new Tile(0, 0);
		normal.addObserver(tile);
		normal.setValue(1024);
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
	
	@Test
	public void testActMove() {
		Tile normal = new Tile(14, 1024);
		normal.addObserver(tile);
		normal.move(15);
		tile.act(0.15f);
		assertNotEquals(411, tile.getX());
	}

	@Test
	public void testSetBaseY() {
		Tile normal = new Tile(14, 1024);
		normal.addObserver(tile);
		normal.setIndex(8);
		normal.changed();
		tile.setBaseY();
		assertEquals(211, tile.getY(), 0.01);
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
