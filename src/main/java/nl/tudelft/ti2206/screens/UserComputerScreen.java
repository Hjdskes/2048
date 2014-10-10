package nl.tudelft.ti2206.screens;

import java.util.TimerTask;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.LocalInputHandler;
import nl.tudelft.ti2206.handlers.RemoteInputHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.net.Networking;
import nl.tudelft.ti2206.solver.GridSolver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UserComputerScreen extends Screen {
		
	/** The local grid holding all the local Tiles. */
	private Grid localGrid;

	/** The remote grid holding all the remote Tiles. */
	private Grid computerGrid;

	/** The ScoreDisplay for the local Grid. */
	private ScoreDisplay localScores;

	/** The ScoreDisplay for the remote Grid. */
	private ScoreDisplay remoteScores;

	/** The label indicating which Grid is yours. */
	private Label you;

	/** The label indicating which Grid is your opponent's. */
	private Label opponent;

	/** The Group packing all local elements. */
	private Group localGroup;

	/** The Group packing all remote elements. */
	private Group remoteGroup;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the Networking instance. */
	private static Networking networking = Networking.getInstance();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** The InputHandler for the remote Grid. */
	private RemoteInputHandler remoteInput;
	
	private GridSolver gridSolver;

	/** Constructs a new MultiGameScreen. */
	public UserComputerScreen() {
		Gdx.graphics.setDisplayMode(2 * TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();

		localGrid = new Grid(false);
		computerGrid = new Grid(false);
		
		/* Sets the name of the objects. Used for logging */
		localGrid.setObjectName("LocalGrid");
		computerGrid.setObjectName("RemoteGrid");

		you = new Label("You", assetHandler.getSkin());
		opponent = new Label("Computer", assetHandler.getSkin());

		localGroup = new Group();
		remoteGroup = new Group();

		localScores = new ScoreDisplay(localGrid);
		remoteScores = new ScoreDisplay(computerGrid);
		
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only */
	public UserComputerScreen(Stage stage, Grid grid, Label label, Group group,
			ScoreDisplay scores, Networking netMock) {
		this.stage = stage;
		this.localGrid = grid;
		this.computerGrid = grid;
		this.you = label;
		this.opponent = label;
		this.localGroup = group;
		this.remoteGroup = group;
		this.localScores = scores;
		this.remoteScores = scores;
		networking = netMock;
		this.setDrawBehavior( new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		/* Create our local groups and actors. */
		you.setX(TwentyFourtyGame.GAME_WIDTH / 2 - you.getPrefWidth() / 2);
		you.setY(2.5f * TwentyFourtyGame.GAP);
		localGroup.addActor(localScores);
		localGroup.addActor(localGrid);
		localGroup.addActor(you);

		/* Create our remote groups and actors. */
		opponent.setX(TwentyFourtyGame.GAME_WIDTH / 2 - you.getPrefWidth() / 2);
		opponent.setY(2.5f * TwentyFourtyGame.GAP);
		remoteGroup.addActor(remoteScores);
		remoteGroup.addActor(computerGrid);
		remoteGroup.addActor(opponent);
		remoteGroup.setX(600);

		stage.addActor(localGroup);
		stage.addActor(remoteGroup);

		stage.addListener(new LocalInputHandler(localGrid));
		
		gridSolver = new GridSolver(computerGrid, GridSolver.Strategy.HUMAN, 1, 6);
		gridSolver.start();
	}

	@Override
	public void update() {
		super.update();

		if (localGrid.getCurrentHighestTile() == 2048
				|| computerGrid.getPossibleMoves() == 0) {
			logger.info(className,
					"Local player won the multiplayer game. The score of the local player: "
							+ Integer.toString(localGrid.getScore()));
			TwentyFourtyGame.setState(GameState.WON);
			screenHandler.add(new MultiWinScreen());
			
			gridSolver.stop();
		} else if (localGrid.getPossibleMoves() == 0
				|| computerGrid.getCurrentHighestTile() == 2048) {
			logger.info(className,
					"Local player lost the multiplayer game. The score of the remote player: "
							+ Integer.toString(computerGrid.getScore()));
			TwentyFourtyGame.setState(GameState.LOST);
			screenHandler.add(new MultiLoseScreen());
			
			gridSolver.stop();
		}
	}

	@Override
	public void dispose() {
		gridSolver.stop();
	}
}	