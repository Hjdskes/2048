package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.ButtonDisplay;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.OverlayDisplay;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.handlers.ProgressHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends ScreenAdapter {
	private Stage stage;
	private Group group;
	private ButtonDisplay buttons;
	private ScoreDisplay scores;
	private OverlayDisplay overlays;

	public GameScreen() {
		stage = new Stage(new ScreenViewport());
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
		stage.getViewport().update(TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		/* Create our groups and actors. */
		Grid grid = ProgressHandler.loadGame();
		grid.setName("Grid");
		stage.addListener(new InputHandler(grid));
		scores = new ScoreDisplay(grid);
		overlays = new OverlayDisplay();
		buttons = new ButtonDisplay();

		/* Create the main group and pack everything in it. */
		group = new Group();
		group.addActor(grid);

		group.addActor(buttons);
		group.addActor(scores);
		group.addActor(overlays);
		stage.addActor(group);
		stage.addActor(buttons);
	}

	/*
	 * According to the documentation, this is a good place to save the game
	 * state on the desktop, because it is called prior to dispose(). See:
	 * https://github.com/libgdx/libgdx/wiki/The-life-cycle
	 */
	@Override
	public void pause() {
		ProgressHandler.saveGame((Grid) group.findActor("Grid"));
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
