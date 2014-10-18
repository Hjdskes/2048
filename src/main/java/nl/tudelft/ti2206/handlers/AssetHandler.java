package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.log.Logger;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * The AssetHandler is used to load and hold all the assets (textures and fonts)
 * the game requires.
 */
public class AssetHandler {
	/** The unique singleton instance of this class. */
	private static AssetHandler instance = new AssetHandler();

	/** The AssetManager is used to load and get all our textures and font. */
	private AssetManager manager = new AssetManager();

	/** The Skin contains all our textures and fonts. */
	private Skin skin = new Skin();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** Overrides the default constructor. */
	private AssetHandler() {
	}

	/**
	 * @return The singleton instance of this class.
	 */
	public static AssetHandler getInstance() {
		return instance;
	}

	/**
	 * Instructs the AssetManager to load all the required assets (textures and
	 * fonts).
	 * 
	 * Blocks until the AssetManager is done.
	 */
	public void load() {

		logger.debug(className, "Loading assets...");

		/*
		 * Queue all of these items for loading, order does not really matter I
		 * think, since we wait for everything to be done anyway.
		 */
		manager.load("fonts/fonts.atlas", TextureAtlas.class);
		manager.load("images/icons/icons.atlas", TextureAtlas.class);
		manager.load("images/tiles/tiles.atlas", TextureAtlas.class);
		manager.load("images/scoretiles/scoretiles.atlas", TextureAtlas.class);
		manager.load("images/buttons/buttons.atlas", TextureAtlas.class);
		manager.load("images/cursor.png", Texture.class);
		manager.load("images/button.png", Texture.class);
		manager.load("images/grid.png", Texture.class);
		manager.load("images/overlays/lostoverlay.png", Texture.class);
		manager.load("images/overlays/wonoverlay.png", Texture.class);
		manager.load("images/overlays/multilostoverlay.png", Texture.class);
		manager.load("images/overlays/multiwonoverlay.png", Texture.class);
		manager.load("images/overlays/connectionlostoverlay.png", Texture.class);

		manager.load("images/overlays/getreadyoverlay.png", Texture.class);
		manager.load("images/overlays/setoverlay.png", Texture.class);
		manager.load("images/overlays/gooverlay.png", Texture.class);
		/*
		 * Instruct the asset manager to load everything in its queue, block
		 * until this is done.
		 */
		manager.finishLoading();

		/* Load all the textures into the Skin object. */
		setupSkin();
	}

	/**
	 * Uses the AssetManager to get all the textures and fonts into the Skin.
	 */
	private void setupSkin() {
		TextureAtlas fonts = manager.get("fonts/fonts.atlas",
				TextureAtlas.class);
		TextureAtlas icons = manager.get("images/icons/icons.atlas",
				TextureAtlas.class);
		TextureAtlas tiles = manager.get("images/tiles/tiles.atlas",
				TextureAtlas.class);
		TextureAtlas scoretiles = manager.get(
				"images/scoretiles/scoretiles.atlas", TextureAtlas.class);
		TextureAtlas buttons = manager.get("images/buttons/buttons.atlas",
				TextureAtlas.class);

		skin.addRegions(fonts);
		skin.addRegions(icons);
		skin.addRegions(tiles);
		skin.addRegions(scoretiles);
		skin.addRegions(buttons);
		skin.add("cursor", manager.get("images/cursor.png", Texture.class));
		skin.add("button", manager.get("images/button.png", Texture.class));
		skin.add("grid", manager.get("images/grid.png", Texture.class));
		skin.add("lostoverlay",
				manager.get("images/overlays/lostoverlay.png", Texture.class));
		skin.add("wonoverlay",
				manager.get("images/overlays/wonoverlay.png", Texture.class));
		skin.add("multilostoverlay", manager.get(
				"images/overlays/multilostoverlay.png", Texture.class));
		skin.add("multiwonoverlay", manager.get(
				"images/overlays/multiwonoverlay.png", Texture.class));
		skin.add("connectionlostoverlay", manager.get(
				"images/overlays/connectionlostoverlay.png", Texture.class));

		skin.add("getreadyoverlay", manager.get(
				"images/overlays/getreadyoverlay.png", Texture.class));
		skin.add("setoverlay",
				manager.get("images/overlays/setoverlay.png", Texture.class));
		skin.add("gooverlay",
				manager.get("images/overlays/gooverlay.png", Texture.class));
	}

	/**
	 * Returns the Skin object, so other classes can load their assets.
	 * 
	 * @return The Skin object.
	 */
	public Skin getSkin() {
		return skin;
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
	public void setAssetManager(AssetManager assetManager) {
		manager = assetManager;
	}

	/**
	 * Sets the Skin to be used by the AssetHandler. Needed for the headless
	 * application which is used for DevHub. Since DevHub cannot make use of GL
	 * related classes, it is necessary to test the AssetHandler in a very
	 * specific way. See {@link AssetHandlerTest}.
	 * 
	 * @param newSkin
	 *            The Skin to be used by the AssetHandler.
	 */
	public void setSkin(Skin newSkin) {
		skin = newSkin;
	}

	/**
	 * Makes the Skin load a file that contains all resources. Required for
	 * headless testing.
	 * 
	 * @param file
	 *            The file containing the resources.
	 */
	public void loadSkinFile(FileHandle file) {
		skin.load(file);
	}

	/**
	 * Disposes of all the textures, the font and the AssetManager.
	 */
	public void dispose() {
		manager.dispose();
	}
}