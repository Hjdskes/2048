package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.ButtonDisplay;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.OverlayDisplay;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.handlers.ProgressHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The Game class is used to hook into LibGDX. It implements LibGDX's
 * ApplicationListener interface.
 * 
 * Through implementing this interface, we receive events from LibGDX by which
 * we control the game.
 */
public class Game implements ApplicationListener {
	/** Enumeration indicating what state the game is currently in. */
	public static enum GameState {
		RUNNING, LOST, WON, CONTINUING
	}

	/** The current state of the game. */
	private static GameState curState;
	/** The width of the game */
	public static final int GAME_WIDTH = 600;
	/** The height of the game */
	public static final int GAME_HEIGHT = 600;

	private Stage stage;
	private Group group;
	private ButtonDisplay buttons;
	private ScoreDisplay scores;
	private OverlayDisplay overlays;

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage); 

		/* Load all our assets. */
		AssetHandler.loadSkinFile(Gdx.files.internal("skin.json"));
		AssetHandler.load();

		/* Create our groups and actors. */
		Grid grid = ProgressHandler.loadGame();
		grid.setName("Grid");
		Tile[] tiles = grid.getTiles();
		scores = new ScoreDisplay(grid);
		overlays = new OverlayDisplay();
		buttons = new ButtonDisplay();

		/* Create the main group and pack everything in it. */
		group = new Group();
		group.addActor(grid);
		for (int i = 0; i < tiles.length; i++) {
			group.addActor(tiles[i]);
		}
		
		group.addActor(buttons);
		group.addActor(scores);
		group.addActor(overlays);
		stage.addActor(group);
		stage.addActor(buttons);
		stage.addListener(new InputHandler(grid));
	}

	@Override
	public void dispose() {
		AssetHandler.dispose();
		ProgressHandler.saveGame((Grid) group.findActor("Grid"));
		stage.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Tell all actors to update... */
		stage.act();
		/* ... and to redraw themselves. */
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		/* Center camera: true. */
		stage.getViewport().update(GAME_WIDTH, GAME_HEIGHT, true);
	}

	@Override
	public void resume() {
	}

	/**
	 * @return The current game state.
	 */
	public static GameState getState() {
		return curState;
	}

	/**
	 * @param state
	 *            The new state of the game.
	 */
	public static void setState(GameState state) {
		curState = state;
	}
}
