package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.MultiPlayerButton;
import nl.tudelft.ti2206.buttons.SinglePlayerButton;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;

public class MenuScreen implements Screen {
	/** The width of the game window. */
	private static final int GAME_WIDTH = 600;

	/** The height of the game window. */
	private static final int GAME_HEIGHT = 600;

	/** The width of a gap, which is between all the tiles. */
	private static final int GAP = 15;

	/** The button to choose a singleplayer game. */
	private SinglePlayerButton single;

	/** The button to choose a multiplayer game. */
	private MultiPlayerButton multi;

	/** The SpriteBratch is used to draw the buttons. */
	private SpriteBatch batch;

	/**
	 * Launches an instance of MenuScreen. The menu provides two buttons: one to
	 * launch a singleplayer game, and another to launch a multiplayer game.
	 * 
	 * @param game
	 *            A reference to the running game, to launch a new game in it.
	 */
	public MenuScreen(final TwentyFourtyGame game) {
		single = new SinglePlayerButton(GAME_WIDTH / 2
				- AssetHandler.single.getWidth() / 2, GAME_HEIGHT / 2
				- (AssetHandler.single.getHeight() / 2 + 3 * GAP),
				AssetHandler.single.getWidth(), AssetHandler.single.getHeight());

		multi = new MultiPlayerButton(GAME_WIDTH / 2
				- AssetHandler.multi.getWidth() / 2, GAME_HEIGHT / 2
				- (AssetHandler.multi.getHeight() / 2 - 3 * GAP),
				AssetHandler.multi.getWidth(), AssetHandler.multi.getHeight());

		/* Sets up an InputHandler on this screen. */
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				if (single.isTouchDown(screenX, screenY)) {
					single.onClick(game);
					return true;
				}

				if (multi.isTouchDown(screenX, screenY)) {
					multi.onClick(game);
					return true;
				}

				return false;
			}
		});

		OrthographicCamera cam = new OrthographicCamera();
		cam.setToOrtho(true, GAME_WIDTH, GAME_HEIGHT);

		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
	}

	@Override
	public void render(float delta) {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Draw the buttons. */
		batch.begin();
		single.draw(batch);
		multi.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
