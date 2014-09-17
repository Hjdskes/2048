package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ButtonHandler;
import nl.tudelft.ti2206.handlers.ProgressHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TwentyFourtyGame implements ApplicationListener {

	/** The width of the game */
	public static final int GAME_WIDTH = 600;
	/** The height of the game */
	public static final int GAME_HEIGHT = 600;

	private Stage stage;
	private Group group;
	private ScoreDisplay scores;

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		AssetHandler.loadSkinFile(Gdx.files.internal("skin.json"));
		AssetHandler.load();
		ButtonHandler.load();

		group = new Group();

		Grid grid = ProgressHandler.loadGame();
		// set the name so the actor can be found in the group.
		grid.setName("Grid");
		group.addActor(grid);

		Tile[] tiles = grid.getTiles();
		for (int i = 0; i < tiles.length; i++) {
			group.addActor(tiles[i]);
			group.addActor(tiles[i].getLabel());
		}

		scores = new ScoreDisplay(grid);
		group.addActor(scores);
		// add grid and tiles
		stage.addActor(group);
		// add buttons
		stage.addActor(ButtonHandler.getRestartButton());
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

		stage.act();
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
}
