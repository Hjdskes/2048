//package nl.tudelft.ti2206.drawables;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.anyFloat;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import nl.tudelft.ti2206.game.HeadlessLauncher;
//import nl.tudelft.ti2206.game.TwentyFourtyGame;
//import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
//import nl.tudelft.ti2206.gameobjects.Grid;
//import nl.tudelft.ti2206.gameobjects.Tile;
//import nl.tudelft.ti2206.handlers.AssetHandler;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//
//public class DrawableGridTest {
//	@Mock private Skin skin;
//	@Mock private Texture texture;
//	@Mock private TextureRegion region;
//	@Mock private Batch batch;
//	@Mock private Tile tile;
//
//	/** The object under test. */
//	private DrawableGrid grid;
//
//	/**
//	 * Initializes all the mocks and creates the test object.
//	 */
//	@Before
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//		new HeadlessLauncher().launch();
//
//		skin = mock(Skin.class);
//		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
//		when(skin.getRegion(anyString())).thenReturn(region);
//		AssetHandler.getInstance().setSkin(skin);
//
//		Tile[] tiles = new Tile[Grid.NTILES];
//		for (int i = 0; i < Grid.NTILES; i++) {
//			tiles[i] = tile;
//		}
//		grid = new DrawableGrid(tiles);
//		TwentyFourtyGame.setState(GameState.RUNNING);
//	}
//
//	/**
//	 * Tests if the getters of x, y, width and height are returning the correct
//	 * values.
//	 */
//	@Test
//	public void testGetXYWidthHeight() {
//		int x = (int) grid.getX();
//		int y = (int) grid.getY();
//		int width = (int) grid.getWidth();
//		int height = (int) grid.getHeight();
//		assertEquals(x, 100);
//		assertEquals(y, 100);
//		assertEquals(width, 400);
//		assertEquals(height, 400);
//	}
//
//	@Test
//	public void testDraw() {
//		grid.draw(batch, 1);
//		// verify grid is drawn
//		verify(batch).draw(eq(region), anyInt(), anyInt(), anyInt(), anyInt());
//		// verify tiles are drawn
//		verify(batch, times(Grid.NTILES)).draw(eq(region), anyInt(), anyInt(), anyInt(),
//				anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
//	}
//}
