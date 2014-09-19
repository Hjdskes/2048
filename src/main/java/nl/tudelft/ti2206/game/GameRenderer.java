package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.DrawableGrid;
import nl.tudelft.ti2206.gameobjects.OverlayDisplay;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.ButtonHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The GameRenderer is responsible for rendering all our game objects. It
 * creates all objects needed for drawing. It also sets the camera to
 * orthographic, meaning that the game is drawn in a 3D scene which appears to
 * be 2D.
 */
public class GameRenderer {

	/** A reference to the GameWorld, which is used to interact with the game. */
	private GameWorld world;

	/** A reference to the Grid. */
	private DrawableGrid grid;

	/** The SpriteBratch is used to draw all Sprite objects. */
	private SpriteBatch batch;

	private OverlayDisplay overlays;
	private ScoreDisplay scores;
	
	/**
	 * Constructs a GameRenderer object.
	 * 
	 * @param world
	 *            A reference to the GameWorld, used to retrieve objects and states for rendering.
	 * @param gameWidth
	 *            The game window's width.
	 * @param gameHeight
	 *            The game window's height.
	 */
	public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
		this.world = world;
		this.grid = world.getGrid();

		OrthographicCamera cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);

		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		overlays = new OverlayDisplay(world, true);
		scores = new ScoreDisplay(world, true);
	}

	/**
	 * Renders all sprites and strings at the rate of 1000/delta times
	 * per second. It also draws a beige background to prevent flickering of the
	 * screen.
	 */
	public void render() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Draw all the sprites and strings. */
		batch.begin();
		drawSpriteBatches();
		batch.end();
	}

	/**
	 * Renders all our sprites.
	 */
	private void drawSpriteBatches() {
		grid.draw(batch);

		for (int i = 0; i < grid.getTiles().length; i++) {
			grid.getTiles()[i].draw(batch);
		}
		
		scores.draw(batch);
		overlays.draw(batch);
		
		if (world.isWon())
			drawContinueButton();
		drawRestartButton();
	}

	/**
	 * Renders the button that will initiate a restart of the game.
	 */
	private void drawRestartButton() {
		ButtonHandler.getRestartButton().draw(batch);
	}

	/**
	 * Renders the button that will allow to continue playing after winning.
	 */
	private void drawContinueButton() {
		ButtonHandler.getContinueButton().draw(batch);
	}
}
