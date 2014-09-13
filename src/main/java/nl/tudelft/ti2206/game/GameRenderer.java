package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.gameobjects.AnimatedGrid;
import nl.tudelft.ti2206.helpers.AssetHandler;
import nl.tudelft.ti2206.helpers.ButtonHandler;
import nl.tudelft.ti2206.helpers.XYCalculator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The GameRenderer is responsible for rendering all our game objects. It
 * creates all objects needed for drawing. It also sets the camera to
 * orthographic, meaning that the game is drawn in a 3D scene which appears to
 * be 2D.
 * 
 * @author group-21
 */
public class GameRenderer {
	/** */
	private static final BitmapFont BROWN_F = AssetHandler.font;
	/** */
	private static final BitmapFont WHITE_F = AssetHandler.whiteFont;
	/** */
	private static final int SCORE_HEIGHT = 50;
	/** A reference to the GameWorld, which is used to interact with the game. */
	private GameWorld world;

	private AnimatedGrid grid;
	/** The orthographic camera, to make the scene appear 2D. */
	private OrthographicCamera cam;
	/** */
	private SpriteBatch batch;
	/** The button used to initiate a restart. */
	private RestartButton restartButton;
	/** The button used to continue the game after winning. */
	private ContinueButton continueButton;

	/**
	 * Constructs a GameRenderer object.
	 * 
	 * @param world
	 *            The world.
	 * @param gameWidth
	 *            The game's width.
	 * @param gameHeight
	 *            The game's height.
	 */
	public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
		this.world = world;
		this.grid = world.getGrid();

		restartButton = ButtonHandler.getRestartButton();
		continueButton = ButtonHandler.getContinueButton();
		cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);

		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
	}

	/**
	 * Render all sprites, shapes and strings at the rate of 1000/delta times
	 * per second. It also draws a black background to prevent flickering of the
	 * screen.
	 */
	public void render() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Draw all the sprites and strings. */
		batch.begin();
		drawSpriteBatches();
		drawText();
		batch.end();
	}

	/**
	 * Render all our sprites.
	 */
	private void drawSpriteBatches() {
		drawGrid();
		drawTiles();
		drawScoreTile();
		drawHighscoreTile();
		drawHighestTile();
		drawRestartButton();

		if (world.isLost()) {
			drawLostOverlay();
		} else if (world.isWon()) {
			drawWonOverlay();
		}
	}

	/**
	 * Render the grid.
	 */
	private void drawGrid() {
		batch.draw(AssetHandler.grid, XYCalculator.getGridX(),
				XYCalculator.getGridY(), XYCalculator.getGridWidth(),
				XYCalculator.getGridHeight());
	}

	/**
	 * Render the tiles.
	 */
	private void drawTiles() {

		for (int i = 0; i < grid.getTiles().length; i++) {
			batch.draw(AssetHandler.getTile(grid.getTiles()[i].getValue()),
					grid.getTileX(i), grid.getTileY(i),
					grid.getTileWidth(i), grid.getTileHeight(i));
			drawTileValue(XYCalculator.getCenterTileX(i),
					XYCalculator.getCenterTileY(i),
					grid.getTiles()[i].getValue());
		}
	}

	/**
	 * Render the Tile values, exactly in the middle of the Tile.
	 * 
	 * @param x
	 *            The center x-coordinate of the Tile.
	 * @param y
	 *            The center y-coordinate of the Tile.
	 * @param value
	 *            The value of the Tile.
	 */
	private void drawTileValue(float x, float y, int value) {
		if (value != 0) {
			String val = Integer.toString(value);
			BROWN_F.draw(batch, val, x - getTextCenterX(BROWN_F, val), y
					+ getTextCenterY(BROWN_F, val));
		}
	}

	/**
	 * Render the square in which the current score should appear.
	 */
	private void drawScoreTile() {
		batch.draw(AssetHandler.score, AssetHandler.score.getX(),
				AssetHandler.score.getY(), AssetHandler.score.getWidth(),
				AssetHandler.score.getHeight());
	}

	/**
	 * Render the square in which the highscore should appear.
	 */
	private void drawHighscoreTile() {
		batch.draw(AssetHandler.highscore, AssetHandler.highscore.getX(),
				AssetHandler.highscore.getY(),
				AssetHandler.highscore.getWidth(),
				AssetHandler.highscore.getHeight());
	}

	/**
	 * Render the square in which the highest tile number achieved should
	 * appear.
	 */
	private void drawHighestTile() {
		batch.draw(AssetHandler.highest, AssetHandler.highest.getX(),
				AssetHandler.highest.getY(), AssetHandler.highest.getWidth(),
				AssetHandler.highest.getHeight());
	}

	/**
	 * Render the button that will initiate a restart of the game.
	 */
	private void drawRestartButton() {
		restartButton.draw(batch);
	}

	/**
	 * Render the button that will allow to continue playing after winning.
	 */
	private void drawContinueButton() {
		continueButton.draw(batch);
	}

	/**
	 * Draw the lost overlay to show to the user it lost.
	 */
	private void drawLostOverlay() {
		batch.draw(AssetHandler.lost, 0, 0);
		drawRestartButton();
	}

	/**
	 * Draw the won overlay to show to the user it won.
	 */
	private void drawWonOverlay() {
		batch.draw(AssetHandler.won, 0, 0);
		drawRestartButton();
		drawContinueButton();
	}

	/**
	 * Render the strings using the specified font.
	 */
	private void drawText() {
		drawScore();
		drawHighscore();
		drawHighest();
	}

	/**
	 * Render the score in its square.
	 */
	private void drawScore() {
		String score = ((Integer)world.getScore()).toString();
		AssetHandler.whiteFont.draw(batch, score,
				AssetHandler.score.getX() + AssetHandler.score.getWidth() / 2
						- getTextCenterX(WHITE_F, score),
				SCORE_HEIGHT);
	}

	/**
	 * Render the highscore in its square.
	 */
	private void drawHighscore() {
		String highscore = ((Integer)world.getHighscore()).toString();
		WHITE_F.draw(
				batch,
				highscore,
				AssetHandler.highscore.getX()
						+ AssetHandler.highscore.getWidth() / 2
						- getTextCenterX(WHITE_F, highscore), SCORE_HEIGHT);
	}

	/**
	 * Render the highest tile ever scored in its square.
	 */
	private void drawHighest() {
		String highest = ((Integer)world.getHighestTile()).toString();
		WHITE_F.draw(batch, highest,
				AssetHandler.highest.getX() + AssetHandler.highest.getWidth()
						/ 2 - getTextCenterX(WHITE_F, highest), SCORE_HEIGHT);
	}

	/**
	 * Calculates the center x-coordinate of a String.
	 * 
	 * @param text
	 *            The text to be centered.
	 * @return The center x-coordinate of the provided String.
	 */
	private float getTextCenterX(BitmapFont f, String text) {
		return f.getBounds(text).width / 2;
	}

	/**
	 * Calculates the center y-coordinate of a String.
	 * 
	 * @param text
	 *            The text to be centered.
	 * @return The center y-coordinate of the provided String.
	 */
	private float getTextCenterY(BitmapFont f, String text) {
		return f.getBounds(text).height / 2;
	}
}
