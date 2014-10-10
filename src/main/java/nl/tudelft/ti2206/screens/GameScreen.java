package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.drawables.DrawableGrid;
import nl.tudelft.ti2206.drawables.Scores;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.handlers.ProgressHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * The GameScreen is the screen for a singleplayer game.
 */
public class GameScreen extends Screen {
	/** The grid holding all the Tiles. */
	private Grid grid;

	/** The score tiles above the Grid. */
	private Scores scores;

	/** The grid that is actually drawn. */
	private DrawableGrid drawableGrid;

	/** The button to restart the current game. */
	private RestartButton restartButton;

	/** The singleton reference to the ProgressHandler class. */
	private ProgressHandler progressHandler = ProgressHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();
	
	/** Constructs a new GameScreen. */
	public GameScreen() {
		stage = new Stage();
		grid = progressHandler.loadGame();
		drawableGrid = new DrawableGrid(grid.getTiles());
		restartButton = new RestartButton(grid);
		scores = new Scores();

		grid.addObserver(scores);
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor to insert Mock objects. For testing only. */
	public GameScreen(Stage stage, Grid grid, RestartButton button,
			Scores scores) {
		this.stage = stage;
		this.grid = grid;
		this.restartButton = button;
		this.scores = scores;
		grid.addObserver(scores);
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();
		stage.addListener(new InputHandler(grid));

		/* Create the main group and pack everything in it. */
		stage.addActor(drawableGrid);
		stage.addActor(restartButton);
		stage.addActor(scores);		
	}

	@Override
	public void update() {
		super.update();
 
		if (grid.getCurrentHighestTile() == 11
				&& !TwentyFourtyGame.isContinuing()) {
			TwentyFourtyGame.setState(GameState.WON);
			screenHandler.add(new WinScreen(grid));
		} else if (grid.getPossibleMoves() == 0) {
			TwentyFourtyGame.setState(GameState.LOST);
			screenHandler.add(new LoseScreen(grid));
		}
	}
	
	@Override
	public void dispose() {
		progressHandler.saveGame(grid);
		super.dispose();
	}
}
