package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.GameRenderer;
import nl.tudelft.ti2206.game.GameWorld;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 600;
	private float runTime;
	private GameWorld world;
	private GameRenderer renderer;

	public GameScreen() {
		world = new GameWorld(GAME_WIDTH, GAME_HEIGHT);
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