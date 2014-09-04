package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.GameRenderer;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.helpers.InputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	/** The width of the game. */
	private static final int GAME_WIDTH = 600;
	/** The height of the game. */
	private static final int GAME_HEIGHT = 600;

	private float runTime;
	private GameWorld world;
	private GameRenderer renderer;

	/**
	 * Constructs a GameScreen with a GameWorld, GameRenderer and an input
	 * processor to handle input events.
	 */
	public GameScreen() {
		world = new GameWorld(GAME_WIDTH, GAME_HEIGHT);
		Gdx.input.setInputProcessor(new InputHandler(world, Gdx.graphics
				.getWidth()));
		renderer = new GameRenderer(world, GAME_WIDTH, GAME_HEIGHT);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	/**
	 * The game loop. Renders and updates the game 1000/delta times per second.
	 */
	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
	}
}