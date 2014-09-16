package nl.tudelft.ti2206.handlers;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * A test class for the AssetHandler.
 * 
 * @author group-21
 */
public class AssetHandlerTest {
	/** A mock for the AssetManager object. */
	private static AssetManager manager;
	/** A mock for the Skin object. */
	private static Skin skin;
	/** A mock for the TextureAtlas object. */
	private static TextureAtlas textureAtlas;

	/**
	 * Sets up the test environment. Mockito is used extensively to prevent GL
	 * related classes and methods from throwing NullPointerExceptions when
	 * running the HeadlessLauncher.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		new HeadlessLauncher().launch();

		manager = mock(AssetManager.class);
		AssetHandler.setAssetManager(manager);

		skin = mock(Skin.class);
		AssetHandler.setSkin(skin);

		textureAtlas = mock(TextureAtlas.class);
		stub(manager.get(anyString(), eq(TextureAtlas.class))).toReturn(textureAtlas);
	}

	/**
	 * Tests if isLibraryInitialized() behaves correctly.
	 */
	@Test
	public void testIsLibraryInitialized() {
		assertFalse(AssetHandler.isLibraryInitialized());
	}

	/**
	 * Tests whether the LibGDX methods of the AssetManager are called every
	 * time a Texture or Font is loaded. In other words, it makes sure that all
	 * Textures and BitmapFonts needed for the application are loaded.
	 */
	@Test
	public void testLoad() {
		AssetHandler.load();
		verify(manager, times(5)).load(anyString(), eq(TextureAtlas.class));
		verify(skin, times(5)).addRegions(textureAtlas);
	}

	/**
	 * Tests if the manager is disposed.
	 */
	@Test
	public void testDispose() {
		AssetHandler.dispose();
		verify(manager).dispose();
	}
}