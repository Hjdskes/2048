package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TwentyFourtyGame implements ApplicationListener {

	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 600;

	private Stage stage;
	private Group group;

	public TwentyFourtyGame() {
	}

	@Override
	public void create() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		AssetHandler.load();

		group = new Group();
		Grid grid = new Grid(true);
		//grid.setBounds(grid.getX(), grid.getY(), 400, 400);
		group.addActor(grid);
		Tile[] tiless = grid.getTiles();
		for (int i = 0; i < tiless.length; i++) {
			//tiles[i].setBounds(tiles[i].getX(), tiles[i].getY(), 81, 81);
			group.addActor(tiless[i]);
		}

		stage.addActor(group);
	}

	@Override
	public void dispose() {
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

		Label label = new Label("Hello!", AssetHandler.getSkin());
		stage.addActor(label);

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
