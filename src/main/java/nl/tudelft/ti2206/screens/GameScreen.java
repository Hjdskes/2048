package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.RedoButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.buttons.UndoButton;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
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
	private ScoreDisplay scores;

	/** The button to undo the last move. */
	private UndoButton undoButton;

	/** The button to redo the last move. */
	private RedoButton redoButton;

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
		undoButton = new UndoButton();
		redoButton = new RedoButton();
		restartButton = new RestartButton();
		scores = new ScoreDisplay(grid);
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor to insert Mock objects. For testing only. */
	public GameScreen(Stage stage, Grid grid, UndoButton undoButton,
			RedoButton redoButton, RestartButton button, ScoreDisplay scores) {
		this.stage = stage;
		this.grid = grid;
		this.undoButton = undoButton;
		this.redoButton = redoButton;
		this.restartButton = button;
		this.scores = scores;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();
		stage.addListener(new InputHandler(grid));

		/* Create the main group and pack everything in it. */
		grid.setName("Grid");
		stage.addActor(grid);
		stage.addActor(undoButton);
		stage.addActor(redoButton);
		stage.addActor(restartButton);
		stage.addActor(scores);
	}

	@Override
	public void update() {
		super.update();

		if (grid.getCurrentHighestTile() == 2048
				&& !TwentyFourtyGame.isContinuing()) {
			TwentyFourtyGame.setState(GameState.WON);
			screenHandler.add(new WinScreen());
		} else if (grid.getPossibleMoves() == 0) {
			TwentyFourtyGame.setState(GameState.LOST);
			screenHandler.add(new LoseScreen());
		}
	}

	@Override
	public void dispose() {
		progressHandler.saveGame(grid);
		super.dispose();
	}
}
