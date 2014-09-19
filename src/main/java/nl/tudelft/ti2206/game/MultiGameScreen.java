package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.handlers.InputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MultiGameScreen implements Screen {
	private Stage stage;
	private Group localGroup;
	private Group remoteGroup;
//	private ButtonDisplay buttons;
//	private OverlayDisplay overlays;

	public MultiGameScreen() {
		Gdx.graphics.setDisplayMode(2 * TwentyFourtyGame.GAME_WIDTH, TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		/* Create our local groups and actors. */
		Grid localGrid = new Grid(false);
		localGrid.setName("local");
		localGroup = new Group();
		localGroup.addActor(new ScoreDisplay(localGrid));
		localGroup.addActor(localGrid);
		stage.addListener(new InputHandler(localGrid));
//		overlays = new OverlayDisplay();
//		buttons = new ButtonDisplay();

		/* Create our remote groups and actors. */
		Grid remoteGrid = new Grid(true);
		remoteGrid.setName("remote");
		remoteGroup = new Group();
		remoteGroup.addActor(new ScoreDisplay(remoteGrid));
		remoteGroup.addActor(remoteGrid);
		remoteGroup.setX(600);
		remoteGroup.setY(0);

//		group.addActor(buttons);
//		group.addActor(scores);
//		group.addActor(overlays);
		stage.addActor(localGroup);
		stage.addActor(remoteGroup);
//		stage.addActor(buttons);
	}

	@Override
	public void render(float delta) {
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
		stage.getViewport().update(2 * TwentyFourtyGame.GAME_WIDTH, TwentyFourtyGame.GAME_HEIGHT, true);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
