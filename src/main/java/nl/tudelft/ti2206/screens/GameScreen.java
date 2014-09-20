package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.handlers.ProgressHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen {
	private Stage stage;
	private Grid grid;
	private ScoreDisplay scores;
	private RestartButton restartButton;

	public GameScreen() {
		stage = new Stage();
		grid = ProgressHandler.loadGame();
		restartButton = new RestartButton();
		scores = new ScoreDisplay(grid);
	}

	/** Constructor to insert Mock objects. For testing only. */
	public GameScreen(Stage stage, Grid grid, RestartButton button,
			ScoreDisplay scores) {
		this.stage = stage;
		this.grid = grid;
		this.restartButton = button;
		this.scores = scores;
	}

	@Override
	public void create() {
		Gdx.input.setInputProcessor(stage);

		stage.addListener(new InputHandler(grid));

		/* Create the main group and pack everything in it. */
		stage.addActor(grid);
		stage.addActor(restartButton);
		stage.addActor(scores);
	}

	@Override
	public void draw() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	@Override
	public boolean isOverlay() {
		return false;
	}

	@Override
	public void pause() {
		ProgressHandler.saveGame(grid);
	}

	@Override
	public void resize(int width, int height) {
		/* Center camera: true. */
		stage.getViewport().update(TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, true);
	}

	@Override
	public void resume() {
	}

	@Override
	public void update() {
		if (TwentyFourtyGame.getState() == TwentyFourtyGame.GameState.WON) {
			ScreenHandler.add(new WinScreen());
		}
		stage.act();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
