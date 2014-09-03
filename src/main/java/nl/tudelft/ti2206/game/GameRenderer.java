package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.helpers.AssetLoader;
import nl.tudelft.ti2206.helpers.XYCalculator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameRenderer {

	private static final int GAP = 15;
	private static final int BASE_X = 100;
	private static final int BASE_Y = 100;
	private static final int SCORE_HEIGHT = 50;

	private static final BitmapFont BROWN_F = AssetLoader.font;
	private static final BitmapFont WHITE_F = AssetLoader.whiteFont;

	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer renderer;
	private SpriteBatch batch;

	/**
	 * Constructor for GameRenderer object, creating all objects needed for
	 * drawing. It also sets the camera to Orthgraphic and orthogonal to the
	 * scene, meaning that the game is drawn in a 3D scene swhich seems to be
	 * 2D.
	 * 
	 * @param world
	 *            the world
	 * @param gameWidth
	 *            the game's width
	 * @param gameHeight
	 *            the game's height
	 */
	public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
		this.world = world;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);

		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(cam.combined);

		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
	}

	/**
	 * Render all sprites, shapes and strings at the rate of 1000/delta times
	 * per second. It also draws a black background to prevent flickering of the
	 * screen.
	 * 
	 * @param delta
	 * @param runTime
	 */
	public void render(float delta, float runTime) {
		// draw beige screen to avoid flickering
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render shapes
		drawShapes(delta);

		// begin drawing sprites, strings, etc
		batch.begin();
		drawSpriteBatches(delta);
		drawText(delta);
		batch.end();
	}

	/**
	 * 
	 * @param delta
	 */
	private void drawShapes(float delta) {

	}

	/**
	 * Render all sprites.
	 * 
	 * @param delta
	 */
	private void drawSpriteBatches(float delta) {
		drawGrid();
		drawTiles();
		drawScoreTile();
		drawHighscoreTile();
		drawHighestTile();
	}

	/**
	 * Render the grid.
	 */
	private void drawGrid() {
		batch.draw(AssetLoader.grid, XYCalculator.getGridX(),
				XYCalculator.getGridY(), XYCalculator.getGridWidth(),
				XYCalculator.getGridWidth());
	}

	/**
	 * Render the squares.
	 */
	private void drawTiles() {
		for (int i = 0; i < world.getGrid().getTiles().length; i++) {
			batch.draw(AssetLoader.getTile(world.getGrid().getTiles()[i]
					.getValue()), XYCalculator.getTileX(i), XYCalculator
					.getTileY(i), XYCalculator.getTileWidth(), XYCalculator
					.getTileHeight());
			drawTileValue(XYCalculator.getCenterTileX(i),
					XYCalculator.getCenterTileY(i),
					world.getGrid().getTiles()[i].getValue());
		}
	}

	/**
	 * Render the Tile values, exactly in the middle of the Tile
	 * 
	 * @param x
	 *            center x coordinate of the Tile
	 * @param y
	 *            center y coordinate of the Tile
	 * @param value
	 *            value of the Tile
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
		batch.draw(AssetLoader.score, BASE_Y, GAP,
				AssetLoader.score.getWidth(), AssetLoader.score.getHeight());
	}

	/**
	 * Render the square in which the highscore should appear.
	 */
	private void drawHighscoreTile() {
		batch.draw(AssetLoader.highscore, AssetLoader.score.getWidth() + BASE_X
				+ GAP, GAP, AssetLoader.highscore.getWidth(),
				AssetLoader.highscore.getHeight());
	}

	/**
	 * Render the square in which the highest tile number achieved should
	 * appear.
	 */
	private void drawHighestTile() {
		batch.draw(AssetLoader.highest, BASE_X + AssetLoader.score.getWidth()
				* 2 + GAP * 2, GAP, AssetLoader.highest.getWidth(),
				AssetLoader.highest.getHeight());
	}

	/**
	 * Render the strings using the specified font.
	 */
	private void drawText(float delta) {
		drawScore();
		drawHighscore();
		drawHighest();
	}

	/**
	 * Render the score in its square.
	 */
	private void drawScore() {
		AssetLoader.whiteFont.draw(
				batch,
				Integer.toString(world.getScore()),
				BASE_X + AssetLoader.score.getWidth() / 2
						- getTextCenterX(WHITE_F, world.getScore()),
				SCORE_HEIGHT);
	}

	/**
	 * Render highscore in its square.
	 */
	private void drawHighscore() {
		WHITE_F.draw(batch, Integer.toString(AssetLoader.getHighscore()),
				BASE_X + AssetLoader.score.getWidth() + GAP
						+ AssetLoader.highscore.getWidth() / 2
						- getTextCenterX(WHITE_F, AssetLoader.getHighscore()),
				SCORE_HEIGHT);
	}

	/**
	 * Render highest tile ever scored in its square.
	 */
	private void drawHighest() {
		WHITE_F.draw(
				batch,
				Integer.toString(AssetLoader.getHighest()),
				BASE_X + AssetLoader.score.getWidth() * 2 + GAP * 2
						+ AssetLoader.highest.getWidth() / 2
						- getTextCenterX(WHITE_F, AssetLoader.getHighest()),
				SCORE_HEIGHT);
	}

	/**
	 * Calculates the center x coordinate of a String.
	 * 
	 * @param text
	 *            the text to be centered
	 * @return the center x coordinate of the provided String
	 */
	private float getTextCenterX(BitmapFont f, String text) {
		return f.getBounds(text).width / 2;
	}

	/**
	 * Calculates the center x coordinate of an integer.
	 * 
	 * @param value
	 *            the integer to be centered
	 * @return the center x coordinate of the provided integer
	 */
	private float getTextCenterX(BitmapFont f, int value) {
		return f.getBounds(Integer.toString(value)).width / 2;
	}

	/**
	 * Calculates the center y coordinate of a String.
	 * 
	 * @param text
	 *            the text to be centered
	 * @return the center y coordinate of the provided Strings
	 */
	private float getTextCenterY(BitmapFont f, String text) {
		return f.getBounds(text).height / 2;
	}

	/**
	 * Calculates the center y coordinate of an integer.
	 * 
	 * @param value
	 *            the integer to be centered
	 * @return the center y coordinate of the provided integer
	 */
	private float getTextCenterY(BitmapFont f, int value) {
		return f.getBounds(Integer.toString(value)).height / 2;
	}
}
