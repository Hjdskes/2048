package nl.tudelft.ti2206.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
			t1024, t2048, t0, score, highscore, highest, newgame,
			continuebutton, lost, won;
	/** The width of the game window. */
	private static final int GAME_WIDTH = 600;
	/** The height of the game window. */
	private static final int GAME_HEIGHT = 600;
	/** The width of a gap, which is between all the tiles. */
	private static final int GAP = 15;
	/** */
	private static final int BASE_X = 100;
	/** */
	private static final int BASE_Y = 100;
	/** The AssetManager is used to load and get all our textures and font. */
	private static AssetManager manager = new AssetManager();

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
		manager.load("src/main/resources/images/lostoverlay.png", Texture.class);
		manager.load("src/main/resources/images/wonoverlay.png", Texture.class);
		manager.load("src/main/resources/images/grid.png", Texture.class);
		manager.load("src/main/resources/images/tiles/tile2.png", Texture.class);
		manager.load("src/main/resources/images/tiles/tile4.png", Texture.class);
		manager.load("src/main/resources/images/tiles/tile8.png", Texture.class);
		manager.load("src/main/resources/images/tiles/tile16.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile32.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile64.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile128.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile256.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile512.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile1024.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile2048.png",
				Texture.class);
		manager.load("src/main/resources/images/tiles/tile0.png", Texture.class);
		manager.load("src/main/resources/images/scoretiles/score.png",
				Texture.class);
		manager.load("src/main/resources/images/scoretiles/highscore.png",
				Texture.class);
		manager.load("src/main/resources/images/scoretiles/highest.png",
				Texture.class);
		manager.load("src/main/resources/fonts/tahoma.fnt", BitmapFont.class);
		manager.load("src/main/resources/fonts/tahomaWhite.fnt",
				BitmapFont.class);
		manager.load("src/main/resources/images/buttons/newgame.png",
				Texture.class);
		manager.load("src/main/resources/images/buttons/continue.png",
				Texture.class);

		/*
		 * Instruct the asset manager to load everything in its queue, block
		 * until this is done.
		 */
		manager.finishLoading();

		/* Now get all the required objects. */
		getTiles();
		getScores();
		getButtons();
		getFonts();
		getOverlays();
	}

	/**
	 * Creates all sprites for the tiles.
	 */
	private static void getTiles() {
		t0 = new Sprite(manager.get("src/main/resources/images/tiles/tile0.png",
				Texture.class));
		t2 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile2.png", Texture.class));
		t4 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile4.png", Texture.class));
		t8 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile8.png", Texture.class));
		t16 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile16.png", Texture.class));
		t32 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile32.png", Texture.class));
		t64 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile64.png", Texture.class));
		t128 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile128.png", Texture.class));
		t256 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile256.png", Texture.class));
		t512 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile512.png", Texture.class));
		t1024 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile1024.png", Texture.class));
		t2048 = new Sprite(manager.get(
				"src/main/resources/images/tiles/tile2048.png", Texture.class));
		grid = new Sprite(manager.get("src/main/resources/images/grid.png",
				Texture.class));
	}

	/**
	 * Creates and positions all sprites for the three scores (current score,
	 * highscore and highest tile value ever reached).
	 */
	private static void getScores() {
		score = new Sprite(manager.get("src/main/resources/images/scoretiles/score.png",
				Texture.class));
		highscore = new Sprite(manager.get(
				"src/main/resources/images/scoretiles/highscore.png", Texture.class));
		highest = new Sprite(manager.get(
				"src/main/resources/images/scoretiles/highest.png", Texture.class));

		setAssetLocation(score, BASE_Y, GAP, false, true);
		setAssetLocation(highscore, AssetHandler.score.getWidth() + BASE_X
				+ GAP, GAP, false, true);
		setAssetLocation(highest, BASE_X + AssetHandler.score.getWidth() * 2
				+ GAP * 2, GAP, false, true);
	}

	/**
	 * Creates and positions all sprites for the two buttons.
	 */
	private static void getButtons() {
		newgame = new Sprite(manager.get(
				"src/main/resources/images/buttons/newgame.png", Texture.class));
		setAssetLocation(newgame, GAME_WIDTH / 2 - newgame.getWidth() / 2,
				GAME_HEIGHT - GAP - newgame.getHeight(), false, true);

		continuebutton = new Sprite(manager.get(
				"src/main/resources/images/buttons/continue.png", Texture.class));
		setAssetLocation(continuebutton,
				GAME_WIDTH / 2 - continuebutton.getWidth() / 2, GAME_HEIGHT
						- GAP * 3 - continuebutton.getHeight() * 2, false, true);
	}

	/**
	 * Creates all the fonts.
	 */
	private static void getFonts() {
		font = manager.get("src/main/resources/fonts/tahoma.fnt",
				BitmapFont.class);
		whiteFont = manager.get("src/main/resources/fonts/tahomaWhite.fnt",
				BitmapFont.class);

		font.setScale(.25f, -.25f);
		whiteFont.setScale(.6f, -.6f);
	}

	/**
	 * Creates all the overlay sprites.
	 */
	private static void getOverlays() {
		lost = new Sprite(manager.get(
				"src/main/resources/images/lostoverlay.png", Texture.class));
		lost.flip(false, true);

		won = new Sprite(manager.get(
				"src/main/resources/images/wonoverlay.png", Texture.class));
		won.flip(false, true);
	}

	/**
	 * Sets the sprite location and whether it is has an X or Y flip or not.
	 * 
	 * @param sprite
	 *            The sprite to configure.
	 * @param x
	 *            The x-coordinate of the sprite.
	 * @param y
	 *            The y-coordinate of the sprite.
	 * @param isXFlipped
	 *            If true, flip the sprite vertically.
	 * @param isYFlipped
	 *            If true, flip the sprite horizontally.
	 */
	private static void setAssetLocation(Sprite sprite, float x, float y,
			boolean isXFlip, boolean isYFlip) {
		sprite.setX(x);
		sprite.setY(y);
		sprite.setFlip(isXFlip, isYFlip);
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
			return t0;
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
