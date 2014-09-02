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

	// private int gameWidth, gameHeight;
	
	/**
	 * Constructor for GameRenderer object.
	 * @param world the world
	 * @param gameWidth the game's width
	 * @param gameHeight the game's height
	 */
	public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
		this.world = world;
		// this.gameWidth = gameWidth;
		// this.gameHeight = gameHeight;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);

		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(cam.combined);

		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
	}

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

	private void renderShapes(float delta) {

	}

	private void renderSpriteBatches(float delta) {
		drawGrid();
		drawSquares();
	}
	
	private void drawGrid() {
		batch.draw(AssetLoader.grid, world.getGrid().getX(), world.getGrid()
				.getY(), Grid.WIDTH, Grid.HEIGHT);
	}

	private void drawSquares() {
		for (Square s : world.getGrid().getSquares()) {
			batch.draw(AssetLoader.getTile(s.getValue()), s.getX(), s.getY(),
					Square.WIDTH, Square.HEIGHT);
		}
	}

	private void renderText(float delta) {
		AssetLoader.font.draw(batch, Integer.toString(world.getScore()), 275,
				20);
	}
}
