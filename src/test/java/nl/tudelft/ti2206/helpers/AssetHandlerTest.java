package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.HeadlessLauncher;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetHandlerTest {

	private static AssetManager manager;
	private static Texture texture;
	private static BitmapFont font;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new HeadlessLauncher().launch();
		
		manager = mock(AssetManager.class);
		AssetHandler.setAssetManager(manager);
		
		texture = mock(Texture.class);
		font = mock(BitmapFont.class);
		
		stub(manager.get(anyString(), eq(Texture.class))).toReturn(texture);
		stub(manager.get(anyString(), eq(BitmapFont.class))).toReturn(font);
		
	}

	@Test
	public void testIsLibraryInitialized() {
		assertFalse(AssetHandler.isLibraryInitialized());
	}
	
	@Test
	public void testLoad() {
		AssetHandler.load();
		verify(manager, times(20)).load(anyString(), eq(Texture.class));
		verify(manager, times(2)).load(anyString(), eq(BitmapFont.class));
	}
	
	@Test
	public void testGetTile() {
		assertNotNull(AssetHandler.getTile(0));
		
		for (int i = 2; i < 2049; i*= 2) {
			assertNotNull(AssetHandler.getTile(i));
		}
	}
	
	@Test
	public void testDispose() {
		AssetHandler.dispose();
		verify(manager).dispose();
	}
}
