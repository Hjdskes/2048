package nl.tudelft.ti2206.handlers;

import old.handlers.AssetHandlerTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * The AssetHandler is used to load and hold all the assets (textures and fonts)
 * the game requires.
 * 
 * @author group-21
 */
public class AssetHandler {
	/** The font used in the game, in two colors. */
	public static BitmapFont font, whiteFont;
	/** All sprites used in the game, which should be publicly accessible. */
	public static Sprite grid, t2, t4, t8, t16, t32, t64, t128, t256, t512,
			t1024, t2048, empty, score, highscore, highest, newgame,
			continuebutton, lost, won;

	/** The AssetManager is used to load and get all our textures and font. */
	private static AssetManager manager = new AssetManager();

	private static Skin skin = new Skin(Gdx.files.internal("skin.json"));

	/**
	 * Checks if the manager is done loading all the textures and font.
	 * 
	 * @return true If the manager is done, false otherwise.
	 */
	public static boolean isLibraryInitialized() {
		/* The check for Gdx.app is required to test on headless mode. */
		return Gdx.app != null && manager.update();
	}

	/**
	 * Instructs the AssetManager to load all the required assets (textures and
	 * fonts).
	 * 
	 * Blocks until the AssetManager is done.
	 */
	public static void load() {
		/*
		 * Queue all of these items for loading, order does not really matter I
		 * think, since we wait for everything to be done anyway.
		 */
		manager.load("src/main/resources/fonts/fonts.atlas", TextureAtlas.class);
		manager.load("src/main/resources/images/icons/icons.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/tiles/tiles.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/scoretiles/scoretiles.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/buttons/buttons.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/grid.png", Texture.class);
		manager.load("src/main/resources/images/lostoverlay.png", Texture.class);
		manager.load("src/main/resources/images/wonoverlay.png", Texture.class);

		/*
		 * Instruct the asset manager to load everything in its queue, block
		 * until this is done.
		 */
		manager.finishLoading();

		/* Load all the textures into the Skin object. */
		setupSkin();
	}

	private static void setupSkin() {
		TextureAtlas fonts = manager.get(
				"src/main/resources/fonts/fonts.atlas", TextureAtlas.class);
		TextureAtlas icons = manager.get(
				"src/main/resources/images/icons/icons.atlas",
				TextureAtlas.class);
		TextureAtlas tiles = manager.get(
				"src/main/resources/images/tiles/tiles.atlas",
				TextureAtlas.class);
		TextureAtlas scoretiles = manager.get(
				"src/main/resources/images/scoretiles/scoretiles.atlas",
				TextureAtlas.class);
		TextureAtlas buttons = manager.get(
				"src/main/resources/images/buttons/buttons.atlas",
				TextureAtlas.class);

		// skin = new Skin(Gdx.files.internal("skin.json"));
		skin.addRegions(fonts);
		skin.addRegions(icons);
		skin.addRegions(tiles);
		skin.addRegions(scoretiles);
		skin.addRegions(buttons);
		skin.add("grid", manager.get("src/main/resources/images/grid.png",
				Texture.class));
		skin.add("lostoverlay", manager.get(
				"src/main/resources/images/lostoverlay.png", Texture.class));
		skin.add("wonoverlay", manager.get(
				"src/main/resources/images/wonoverlay.png", Texture.class));
	}

	public static Skin getSkin() {
		return skin;
	}

	/**
	 * Returns the Sprite belonging to the provided value.
	 * 
	 * @param value
	 *            The value indicating which Sprite to return.
	 * @return The Sprite belonging to the required value.
	 */
	public static Sprite getTile(int value) {
		switch (value) {
		case 2:
			return t2;
		case 4:
			return t4;
		case 8:
			return t8;
		case 16:
			return t16;
		case 32:
			return t32;
		case 64:
			return t64;
		case 128:
			return t128;
		case 256:
			return t256;
		case 512:
			return t512;
		case 1024:
			return t1024;
		case 2048:
			return t2048;
		default:
			return empty;
		}
	}

	/**
	 * Sets the AssetManager to be used by the AssetHandler. Needed for the
	 * headless application which is used for DevHub. Since DevHub cannot make
	 * use of GL related classes, it is necessary to test the AssetHandler in a
	 * very specific way. See {@link AssetHandlerTest}.
	 * 
	 * @param assetManager
	 *            The AssetManager to be used by the AssetHandler.
	 */
	public static void setAssetManager(AssetManager assetManager) {
		manager = assetManager;
	}

	/**
	 * Dispose of all the textures, the font and the AssetManager.
	 */
	public static void dispose() {
		manager.dispose();
	}
}
