package nl.tudelft.ti2206.utils.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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

	/** The singleton AssetHandler instance used to access our assets. */
	private static AssetHandler assetHandler = AssetHandler.getInstance();
	
	private static FreeTypeFontGenerator generator;
	private static BitmapFont font;

	/**
	 * Sets up the test environment. Mockito is used extensively to prevent GL
	 * related classes and methods from throwing NullPointerExceptions when
	 * running the HeadlessLauncher.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		new HeadlessLauncher().launch();

		manager = mock(AssetManager.class);
		generator = mock(FreeTypeFontGenerator.class);
		font = mock(BitmapFont.class);
		doReturn(font).when(generator).generateFont(any(FreeTypeFontParameter.class));
		assetHandler.setAssetMocks(manager, generator);

		skin = mock(Skin.class);
		assetHandler.setSkin(skin);

		textureAtlas = mock(TextureAtlas.class);
		when(manager.get(anyString(), eq(TextureAtlas.class))).thenReturn(
				textureAtlas);
	}

	/**
	 * Tests whether the LibGDX methods of the AssetManager are called every
	 * time a Texture or Font is loaded. In other words, it makes sure that all
	 * Textures and BitmapFonts needed for the application are loaded.
	 */
//	@Test
//	public void testLoad() {
//		assetHandler.load();
//		verify(manager, times(5)).load(anyString(), eq(TextureAtlas.class));
//		verify(skin, times(5)).addRegions(textureAtlas);
//	}

	/**
	 * Tests if the getter of the skin does return the correct skin.
	 */
	@Test
	public void testGetSkin() {
		assertEquals(skin, assetHandler.getSkin());
	}

	/**
	 * Test if the skin is loaded when the loadSkinFile() method is called. We
	 * test this by verifying if the load() method is called of the mocked skin.
	 */
	@Test
	public void testLoadSkinFile() {
		assetHandler.loadSkinFile(Gdx.files.internal("resources/skin.json"));
		verify(skin).load(Gdx.files.internal("resources/skin.json"));
	}

	/**
	 * Tests if the manager is disposed.
	 */
	@Test
	public void testDispose() {
		assetHandler.dispose();
		verify(manager).dispose();
	}
}
