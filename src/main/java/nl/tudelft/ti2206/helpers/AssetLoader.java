package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * This class is used for loading all assets by the game 2048.
 * 
 * @author group-21
 */

public class AssetLoader {
	/** The font used in the game (Tahoma). */
	public static BitmapFont font, whiteFont;
	/** All sprites used in the game, should be publicly accessible. */
	public static Sprite grid, t2, t4, t8, t16, t32, t64, t128, t256, t512,
			t1024, t2048, empty, score, highscore, highest, newgame, lost, won;
	/** All textures used to load the sprites. */
	private static Texture gridTex, t2Tex, t4Tex, t8Tex, t16Tex, t32Tex,
			t64Tex, t128Tex, t256Tex, t512Tex, t1024Tex, t2048Tex, emptyTex,
			scoreTex, highscoreTex, highestTex, newgameTex, lostTex, wonTex;

	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 600;
	private static final int GAP = 15;
	private static final int BASE_X = 100;
	private static final int BASE_Y = 100;

	private static Preferences prefs;

	/**
	 * Checks if LibGDX is initialized correctly.
	 * 
	 * @return true if the app reference exists, false otherwise.
	 */
	public static boolean isLibraryInitialized() {
		return Gdx.app != null;
	}

	/**
	 * Loads all assets needed for the game.
	 */
	public static void load() {
		loadTiles();

		loadScoreTiles();

		loadNewGameTile();

		loadFonts();

		loadOverlays();

		setPrefs();
	}

	private static void loadOverlays() {
		lostTex = new Texture(
				Gdx.files.internal("src/main/resources/images/lostoverlay.png"));
		lost = new Sprite(lostTex);
		lost.flip(false, true);

		wonTex = new Texture(
				Gdx.files.internal("src/main/resources/images/wonoverlay.png"));
		won = new Sprite(wonTex);
		won.flip(false, true);
	}

	/**
	 * Load the number tiles.
	 */
	private static void loadTiles() {
		gridTex = new Texture(
				Gdx.files.internal("src/main/resources/images/grid.png"));
		grid = new Sprite(gridTex);

		t2Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile2.png"));
		t4Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile4.png"));
		t8Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile8.png"));
		t16Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile16.png"));
		t32Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile32.png"));
		t64Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile64.png"));
		t128Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile128.png"));
		t256Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile256.png"));
		t512Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile512.png"));
		t1024Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile1024.png"));
		t2048Tex = new Texture(
				Gdx.files.internal("src/main/resources/images/tile2048.png"));
		emptyTex = new Texture(
				Gdx.files.internal("src/main/resources/images/empty.png"));

		t2 = new Sprite(t2Tex);
		t4 = new Sprite(t4Tex);
		t8 = new Sprite(t8Tex);
		t16 = new Sprite(t16Tex);
		t32 = new Sprite(t32Tex);
		t64 = new Sprite(t64Tex);
		t128 = new Sprite(t128Tex);
		t256 = new Sprite(t256Tex);
		t512 = new Sprite(t512Tex);
		t1024 = new Sprite(t1024Tex);
		t2048 = new Sprite(t2048Tex);
		empty = new Sprite(emptyTex);
	}

	/**
	 * Load the score and highscore tiles.
	 */
	private static void loadScoreTiles() {
		scoreTex = new Texture(
				Gdx.files.internal("src/main/resources/images/score.png"));
		score = new Sprite(scoreTex);
		setAssetLocation(score, BASE_Y, GAP, false, true);

		highscoreTex = new Texture(
				Gdx.files.internal("src/main/resources/images/highscore.png"));
		highscore = new Sprite(highscoreTex);
		setAssetLocation(highscore,
				AssetLoader.score.getWidth() + BASE_X + GAP, GAP, false, true);

		highestTex = new Texture(
				Gdx.files.internal("src/main/resources/images/highest.png"));
		highest = new Sprite(highestTex);
		setAssetLocation(highest, BASE_X + AssetLoader.score.getWidth() * 2
				+ GAP * 2, GAP, false, true);
	}

	/**
	 * Load the new game tile.
	 */
	public static void loadNewGameTile() {
		newgameTex = new Texture(
				Gdx.files.internal("src/main/resources/images/newgame.png"));
		newgame = new Sprite(newgameTex);
		setAssetLocation(newgame, GAME_WIDTH / 2 - newgame.getWidth() / 2,
				GAME_HEIGHT - GAP - newgame.getHeight(), false, true);
	}

	/**
	 * Load all fonts.
	 */
	private static void loadFonts() {
		font = new BitmapFont(
				Gdx.files.internal("src/main/resources/fonts/tahoma.fnt"));
		font.setScale(.25f, -.25f);

		whiteFont = new BitmapFont(
				Gdx.files.internal("src/main/resources/fonts/tahomaWhite.fnt"));
		whiteFont.setScale(.6f, -.6f);
	}

	/**
	 * Set default values for high scores.
	 */
	private static void setPrefs() {
		prefs = Gdx.app.getPreferences("2048");
		if (!prefs.contains("score")) {
			prefs.putInteger("score", 0);
			prefs.flush();
		}
		if (!prefs.contains("highscore")) {
			prefs.putInteger("highscore", 0);
			prefs.flush();
		}
		if (!prefs.contains("highest")) {
			prefs.putInteger("highest", 0);
			prefs.flush();
		}
	}

	/**
	 * Sets the sprite location and whether it is has a X or Y flip or not.
	 * 
	 * @param sprite
	 * @param x
	 * @param y
	 * @param isXFlipped
	 * @param isYFlipped
	 */
	private static void setAssetLocation(Sprite sprite, float x, float y,
			boolean isXFlip, boolean isYFlip) {
		sprite.setX(x);
		sprite.setY(y);
		sprite.setFlip(isXFlip, isYFlip);
	}

	/**
	 * Gets the Sprite belonging to the provided value.
	 * 
	 * @param val
	 * @return
	 */
	public static Sprite getTile(int val) {
		switch (val) {
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
	 * Save the game.
	 * @param world
	 */
	public static void saveGame(GameWorld world) {
		saveGrid(world.getGrid());
		prefs.putInteger("score", world.getScore());
		prefs.flush();
	}
	
	/**
	 * Save the grid.
	 * @param grid
	 */
	private static void saveGrid(Grid grid) {
		String state = "";
		
		Tile[] tiles = grid.getTiles();

		for (int index = 0; index < tiles.length; index++) {
			
			if (!tiles[index].isEmpty())
				if (state.equals(""))
					state += index + "," + tiles[index].getValue() + ","
							+ tiles[index].isMerged();
				else
					state += "\n" + index + "," + tiles[index].getValue()
							+ "," + tiles[index].isMerged();
		}

		prefs.putString("grid", state);
	}

	/**
	 * Loads the saved grid and score.
	 * 
	 * @param world
	 */
	public static void loadGame(GameWorld world) {
		world.setScore(prefs.getInteger("score"));
		world.setGrid(loadGrid(world));
	}

	/**
	 * Loads the saved grid. If no grid is saved, returns a default grid.
	 * 
	 * @param world
	 * @return the loaded grid.
	 */
	private static Grid loadGrid(GameWorld world) {
		String filledTiles = prefs.getString("grid");
		// If no grid is saved, return a default one.
		if (filledTiles == "")
			return new Grid(world, false);
		// Else, fill the grid with the saved tiles.
		else {
			Grid grid = new Grid(world, true);

			String[] split = filledTiles.split("\n");

			for (String tile : split) {
				String[] tileInfo = tile.split(",");
				grid.setTile(Integer.parseInt(tileInfo[0]),
						Integer.parseInt(tileInfo[1]),
						Boolean.getBoolean(tileInfo[2]));
			}
			return grid;
		}
	}

	public static int getHighscore() {
		return prefs.getInteger("highscore");
	}

	public static int getHighest() {
		return prefs.getInteger("highest");
	}

	public static Preferences getPrefs() {
		return prefs;
	}

	public static void setHighscore(int highscore) {
		prefs.putInteger("highscore", highscore);
		prefs.flush();
	}

	public static void setHighest(int highest) {
		prefs.putInteger("highest", highest);
		prefs.flush();
	}

	/**
	 * Dispose of all textures to decrease memory usage.
	 */
	public static void dispose() {
		gridTex.dispose();
		t2Tex.dispose();
		t4Tex.dispose();
		t8Tex.dispose();
		t16Tex.dispose();
		t32Tex.dispose();
		t64Tex.dispose();
		t128Tex.dispose();
		t256Tex.dispose();
		t512Tex.dispose();
		t1024Tex.dispose();
		t2048Tex.dispose();
		emptyTex.dispose();
		scoreTex.dispose();
		highscoreTex.dispose();
		highestTex.dispose();
		newgameTex.dispose();

		wonTex.dispose();
		lostTex.dispose();
		
		
		font.dispose();
		whiteFont.dispose();
	}
}
