package nl.tudelft.ti2206.graphics.screens.gamescreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.drawables.DrawableGrid;
import nl.tudelft.ti2206.graphics.drawables.Scores;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawSimple;
import nl.tudelft.ti2206.utils.ai.solvers.GridSolver;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.input.InputHandler;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.states.RunningState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * The UserComputerScreen is the screen for a user versus the computer game.
 */
public class UserComputerScreen extends Screen {
	/** This enumeration indicates the speed with which the solver shall play. */
	public enum Difficulty {
		EASY, MEDIUM, HARD, EXTREME
	}

	/** Get current class name, used for logging output. */
	private static final String CLASSNAME = UserComputerScreen.class
			.getSimpleName();
	
	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();
	/** The singleton reference to the ScreenHandler class. */
	private ScreenHandler screenHandler = ScreenHandler.getInstance();
	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();
	/** The reference to the class handling the AI. */
	private static GridSolver gridSolver = GridSolver.getInstance();

	/** The local grid holding all the local Tiles. */
	private Grid localGrid;
	/** The computer grid holding all the computer Tiles. */
	private Grid computerGrid;

	/** The ScoreDisplay for the local Grid. */
	private Scores localScores;
	/** The ScoreDisplay for the computer Grid. */
	private Scores computerScores;

	/** The label indicating which Grid is yours. */
	private Label you;
	/** The label indicating which Grid is your opponent's. */
	private Label opponent;

	/** The Group packing all local elements. */
	private Group localGroup;
	/** The Group packing all computer elements. */
	private Group computerGroup;
	
	/** The difficulty level of the CPU. */
	private Difficulty difficulty;

	/** The graphical grids. */
	private DrawableGrid localGridDraw;
	private DrawableGrid computerGridDraw;

	/** Constructs a new MultiGameScreen. */
	public UserComputerScreen(Difficulty difficulty) {
		Gdx.graphics.setDisplayMode(2 * TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();

		localGrid = new Grid(false);
		computerGrid = new Grid(false);

		localGridDraw = new DrawableGrid(localGrid.getTiles());
		computerGridDraw = new DrawableGrid(computerGrid.getTiles());

		/* Sets the name of the objects. Used for logging */
		localGrid.setGridName("LocalGrid");
		computerGrid.setGridName("ComputerGrid");

		you = new Label("You", assetHandler.getSkin());
		opponent = new Label("Computer", assetHandler.getSkin());

		localGroup = new Group();
		computerGroup = new Group();

		localScores = new Scores();
		localGrid.addObserver(localScores);
		computerScores = new Scores();
		computerGrid.addObserver(computerScores);

		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only */
	public UserComputerScreen(Stage stage, Grid grid, Label label, Group group,
			Scores scores, Difficulty difficulty, GridSolver solver,
			ScreenHandler handler) {
		this.stage = stage;
		this.localGrid = grid;
		this.computerGrid = grid;
		this.you = label;
		this.opponent = label;
		this.localGroup = group;
		this.computerGroup = group;
		this.localScores = scores;
		this.computerScores = scores;
		gridSolver = solver;
		this.screenHandler = handler;
		this.setDrawBehavior(new DrawSimple(stage));
	}

	@Override
	public void create() {
		super.create();

		setUpLocalActors();
		setUpRemoteActors();

		stage.addActor(localGroup);
		stage.addActor(computerGroup);
		stage.addListener(new InputHandler(localGrid));

		adjustToDifficulty();
		gridSolver.start(computerGrid);

		/* After creating the screen, start the game. */
		TwentyFourtyGame.setState(RunningState.getInstance());
	}

	/** Create our local groups and actors. */
	private void setUpLocalActors() {
		you.setX(TwentyFourtyGame.GAME_WIDTH / 2 - you.getPrefWidth() / 2);
		you.setY(2.5f * TwentyFourtyGame.GAP);
		localGroup.addActor(localScores);
		localGroup.addActor(localGridDraw);
		localGroup.addActor(you);
	}

	/** Create our computer groups and actors. */
	private void setUpRemoteActors() {
		opponent.setX(TwentyFourtyGame.GAME_WIDTH / 2 - opponent.getPrefWidth()
				/ 2);
		opponent.setY(2.5f * TwentyFourtyGame.GAP);
		computerGroup.addActor(computerScores);
		computerGroup.addActor(computerGridDraw);
		computerGroup.addActor(opponent);
		computerGroup.setX(600);
	}

	/**
	 * Sets the solver's delay, recursion depth and strategy w.r.t the set
	 * difficulty, i.e. sets the number of brain cells for the AI solver.
	 */
	private void adjustToDifficulty() {
		int delay = 1900; // 1.9 seconds
		int depth = 1;

		if (difficulty == Difficulty.EASY) {
			delay = 1600;
			depth = 1;
		} else if (difficulty == Difficulty.MEDIUM) {
			delay = 1200;
			depth = 3;
		} else if (difficulty == Difficulty.HARD) {
			delay = 900;
			depth = 5;
		} else {
			delay = 650;
			depth = 6;
		}

		gridSolver.setDepth(depth);
		gridSolver.setDelay(delay);
	}

	/**
	 * Sets the label indicating your Grid to the supplied text and color.
	 * 
	 * @param string
	 *            The text to set.
	 * @param color
	 *            The color to use.
	 */
	public void setYouLabel(String string, Color color) {
		you.setText(string);
		you.setColor(color);
		you.setX(TwentyFourtyGame.GAME_WIDTH / 2 - you.getPrefWidth() / 2);
	}

	/**
	 * Sets the label indicating your opponent's Grid to the supplied text and
	 * color.
	 * 
	 * @param string
	 *            The text to set.
	 * @param color
	 *            The color to use.
	 */
	public void setOpponentLabel(String string, Color color) {
		opponent.setText(string);
		opponent.setColor(color);
		opponent.setX(TwentyFourtyGame.GAME_WIDTH / 2 - opponent.getPrefWidth()
				/ 2);
	}

	@Override
	public void update() {
		super.update();

		if (TwentyFourtyGame.isWaiting()) {
			this.setYouLabel("WAITING", Color.RED);
		} else if (computerGrid.getPossibleMoves() == 0
				&& !screenHandler.getScreen().hasOverlay()) {
			logger.info(CLASSNAME,
					"Computer is out of moves! Waiting for the player...");
			this.setOpponentLabel("WAITING", Color.RED);
			screenHandler.getScreen().addBoardOverlay(true, false);
			gridSolver.stop();
		}

		TwentyFourtyGame.getState().update(localGrid, computerGrid);
	}

	@Override
	public void dispose() {
		super.dispose();
		gridSolver.stop();
	}
}