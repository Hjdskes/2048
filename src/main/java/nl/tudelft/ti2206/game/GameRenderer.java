package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Square;
import nl.tudelft.ti2206.helpers.AssetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameRenderer {
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer renderer;
	private SpriteBatch batch;

	private int gameWidth/* , gameHeight */;

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
		this.gameWidth = gameWidth;
		/* this.gameHeight = gameHeight; */

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
		// draw black screen to avoid flickering
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render shapes
		renderShapes(delta);

		// begin drawing sprites, strings, etc
		batch.begin();
		renderSpriteBatches(delta);
		renderText(delta);
		batch.end();
	}

	/**
	 * 
	 * @param delta
	 */
	private void renderShapes(float delta) {

	}

	/**
	 * Render all sprites.
	 * 
	 * @param delta
	 */
	private void renderSpriteBatches(float delta) {
		drawGrid();
		drawSquares();
	}

	/**
	 * Render the grid.
	 */
	private void drawGrid() {
		batch.draw(AssetLoader.grid, world.getGrid().getX(), world.getGrid()
				.getY(), Grid.WIDTH, Grid.HEIGHT);
	}

	/**
	 * Render the squares.
	 */
	private void drawSquares() {
		AssetLoader.font.setScale(.25f, -.25f);
		for (Square s : world.getGrid().getSquares()) {
			batch.draw(AssetLoader.getTile(s.getValue()), s.getX(), s.getY(),
					Square.WIDTH, Square.HEIGHT);
			renderSquareValue(s.getCenterX(), s.getCenterY(), s.getValue());
		}
		AssetLoader.font.setScale(.5f, -.5f);
	}

	/**
	 * Render the Square values, exactly in the middle of the Square
	 * 
	 * @param x
	 *            center x coordinate of the Square
	 * @param y
	 *            center y coordinate of the Square
	 * @param value
	 *            value of the Square
	 */
	private void renderSquareValue(float x, float y, int value) {
		if (value != 0) {
			String val = Integer.toString(value);
			AssetLoader.font.draw(batch, val, x - getTextCenterX(val), y
					+ getTextCenterY(val));
		}
	}

	/**
	 * Render the strings using the specified font.
	 */
	private void renderText(float delta) {
		drawScore();
	}

	/**
	 * Render the score.
	 */
	private void drawScore() {
		AssetLoader.font.draw(batch, Integer.toString(world.getScore()),
				gameWidth / 2 - getTextCenterX(world.getScore()), 20);
	}

	/**
	 * Calculates the center x coordinate of a String.
	 * 
	 * @param text
	 *            the text to be centered
	 * @return the center x coordinate of the provided String
	 */
	private float getTextCenterX(String text) {
		return AssetLoader.font.getBounds(text).width / 2;
	}

	/**
	 * Calculates the center x coordinate of an integer.
	 * 
	 * @param value
	 *            the integer to be centered
	 * @return the center x coordinate of the provided integer
	 */
	private float getTextCenterX(int value) {
		return AssetLoader.font.getBounds(Integer.toString(value)).width / 2;
	}

	/**
	 * Calculates the center y coordinate of a String
	 * 
	 * @param text
	 *            the text to be centered
	 * @return the center y coordinate of the provided Strings
	 */
	private float getTextCenterY(String text) {
		return AssetLoader.font.getBounds(text).height / 2;
	}

	/**
	 * Calculates the center y coordinate of an integer.
	 * 
	 * @param value
	 *            the integer to be centered
	 * @return the center y coordinate of the provided integer
	 */
	private float getTextCenterY(int value) {
		return AssetLoader.font.getBounds(Integer.toString(value)).height / 2;
	}
}
