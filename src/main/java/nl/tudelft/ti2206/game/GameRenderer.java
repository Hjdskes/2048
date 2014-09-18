package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.DrawableGrid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ButtonHandler;

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
	/** The white font. */
	private static final BitmapFont WHITE_F = AssetHandler.whiteFont;
	/** The gap between the top of the game window and the score text. */
	private static final int SCORE_HEIGHT = 50;
	/** A reference to the GameWorld, which is used to interact with the game. */
	private GameWorld world;
	/** A reference to the Grid. */
	private DrawableGrid grid;
	/** The orthographic camera, to make the scene appear 2D. */
	private OrthographicCamera cam;
	/** The SpriteBratch is used to draw all Sprite objects. */
	private SpriteBatch batch;

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

		cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);

		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
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
		//drawText();
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
	 * Renders the square in which the current score should appear.
	 */
	private void drawScoreTile() {
		batch.draw(AssetHandler.score, AssetHandler.score.getX(),
				AssetHandler.score.getY(), AssetHandler.score.getWidth(),
				AssetHandler.score.getHeight());
	}

	/**
	 * Renders the square in which the highscore should appear.
	 */
	private void drawHighscoreTile() {
		batch.draw(AssetHandler.highscore, AssetHandler.highscore.getX(),
				AssetHandler.highscore.getY(),
				AssetHandler.highscore.getWidth(),
				AssetHandler.highscore.getHeight());
	}

	/**
	 * Renders the square in which the highest tile value ever achieved should
	 * appear.
	 */
	private void drawHighestTile() {
		batch.draw(AssetHandler.highest, AssetHandler.highest.getX(),
				AssetHandler.highest.getY(), AssetHandler.highest.getWidth(),
				AssetHandler.highest.getHeight());
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

	/**
	 * Draws the lost overlay to show to the user it lost.
	 */
	private void drawLostOverlay() {
		batch.draw(AssetHandler.lost, 0, 0);
		drawRestartButton();
	}

	/**
	 * Draws the won overlay to show to the user it won.
	 */
	private void drawWonOverlay() {
		batch.draw(AssetHandler.won, 0, 0);
		drawRestartButton();
		drawContinueButton();
	}

//	/**
//	 * Renders the strings.
//	 */
//	private void drawText() {
//		drawScore();
//		drawHighscore();
//		drawHighest();
//	}
//
//	/**
//	 * Renders the score in its square.
//	 */
//	private void drawScore() {
//		String score = ((Integer)world.getScore()).toString();
//		AssetHandler.whiteFont.draw(batch, score,
//				AssetHandler.score.getX() + AssetHandler.score.getWidth() / 2
//						- getTextCenterX(WHITE_F, score),
//				SCORE_HEIGHT);
//	}
//
//	/**
//	 * Renders the highscore in its square.
//	 */
//	private void drawHighscore() {
//		String highscore = ((Integer)world.getHighscore()).toString();
//		WHITE_F.draw(
//				batch,
//				highscore,
//				AssetHandler.highscore.getX()
//						+ AssetHandler.highscore.getWidth() / 2
//						- getTextCenterX(WHITE_F, highscore), SCORE_HEIGHT);
//	}
//
//	/**
//	 * Renders the highest tile value ever reached in its square.
//	 */
//	private void drawHighest() {
//		String highest = ((Integer)world.getHighestTile()).toString();
//		WHITE_F.draw(batch, highest,
//				AssetHandler.highest.getX() + AssetHandler.highest.getWidth()
//						/ 2 - getTextCenterX(WHITE_F, highest), SCORE_HEIGHT);
//	}
}
