package old.game;

import old.handlers.InputHandler;
import old.handlers.ProgressHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * The main screen. It is the 'controller' of all other classes and contains the
 * main game loop: the render method.
 * 
 * Via this class, all other classes are controlled, updated and rendered.
 * 
 * @author group-21
 */
public class GameScreen implements Screen {
	/** The width of the game window. */
	private static final int GAME_WIDTH = 600;
	/** The height of the game window. */
	private static final int GAME_HEIGHT = 600;
	/** The GameWorld object is used to control our game objects. */
	private GameWorld world;
	/** The GameRenderer object is used to render our game objects. */
	private GameRenderer renderer;

	/**
	 * Constructs a GameScreen with a GameWorld, GameRenderer and an input
	 * processor to handle input events.
	 */
	public GameScreen() {
		world = new GameWorld();
		/* Sets up an InputHandler on this screen. */
		Gdx.input.setInputProcessor(new InputHandler(world));
		renderer = new GameRenderer(world, GAME_WIDTH, GAME_HEIGHT);
	}

	/**
	 * The game loop. Renders and updates the game 1000/delta times per second.
	 * In here, we:
	 * 1) update all our game objects, by using the GameWorld class,
	 * 2) render all our game objects, by using the GameRenderer class.
	 * 
	 * @param delta
	 *            The time in milliseconds since the last render.
	 */
	@Override
	public void render(float delta) {
		world.update();
		renderer.render();
	}

	/*
	 * According to the documentation, this is a good place to save the game
	 * state on the desktop, because it is called prior to dispose(). See:
	 * https://github.com/libgdx/libgdx/wiki/The-life-cycle
	 */
	@Override
	public void pause() {
		ProgressHandler.saveGame(world);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
	}
}