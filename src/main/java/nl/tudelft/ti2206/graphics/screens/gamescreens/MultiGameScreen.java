package nl.tudelft.ti2206.graphics.screens.gamescreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.drawables.DrawableGrid;
import nl.tudelft.ti2206.graphics.drawables.Scores;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.input.LocalInputHandler;
import nl.tudelft.ti2206.utils.input.RemoteInputHandler;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.net.Networking;
import nl.tudelft.ti2206.utils.states.RunningState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * The MultiGameScreen is the screen for a multiplayer game.
 */
public class MultiGameScreen extends Screen {
	/** The local grid holding all the local Tiles. */
	private Grid localGrid;
	private DrawableGrid localDrawableGrid;

	/** The remote grid holding all the remote Tiles. */
	private Grid remoteGrid;
	private DrawableGrid remoteDrawableGrid;

	/** The ScoreDisplay for the local Grid. */
	private Scores localScores;

	/** The ScoreDisplay for the remote Grid. */
	private Scores remoteScores;

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

	/** The singleton reference to the ScreenHandler class. */
	private ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The InputHandler for the remote Grid. */
	private RemoteInputHandler remoteInput;

	/** Constructs a new MultiGameScreen. */
	public MultiGameScreen() {
		Gdx.graphics.setDisplayMode(2 * TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();

		localGrid = new Grid(false);
		remoteGrid = new Grid(false);

		/* Sets the name of the objects. Used for logging */
		localGrid.setGridName("LocalGrid");
		remoteGrid.setGridName("RemoteGrid");

		localDrawableGrid = new DrawableGrid(localGrid.getTiles());
		remoteDrawableGrid = new DrawableGrid(remoteGrid.getTiles());
		you = new Label("You", assetHandler.getSkin());
		opponent = new Label("Opponent", assetHandler.getSkin());

		localGroup = new Group();
		remoteGroup = new Group();

		localScores = new Scores();
		localGrid.addObserver(localScores);
		remoteScores = new Scores();
		remoteGrid.addObserver(remoteScores);

		remoteInput = new RemoteInputHandler(remoteGrid);
		networking.addObserver(remoteInput);

		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only */
	public MultiGameScreen(ScreenHandler handler, Stage stage, Grid grid,
			Label label, Group group, Scores scores, RemoteInputHandler remoteInput, Networking netMock) {
		this.screenHandler = handler;
		this.stage = stage;
		this.localGrid = grid;
		this.remoteGrid = grid;
		this.you = label;
		this.opponent = label;
		this.localGroup = group;
		this.remoteGroup = group;
		this.localScores = scores;
		this.remoteScores = scores;
		this.remoteInput = remoteInput;
		networking = netMock;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		/* Create our local groups and actors. */
		you.setX(TwentyFourtyGame.GAME_WIDTH / 2 - you.getPrefWidth() / 2);
		you.setY(2.5f * TwentyFourtyGame.GAP);
		localGroup.addActor(localScores);
		localGroup.addActor(localDrawableGrid);
		localGroup.addActor(you);

		/* Create our remote groups and actors. */
		opponent.setX(TwentyFourtyGame.GAME_WIDTH / 2 - opponent.getPrefWidth()
				/ 2);
		opponent.setY(2.5f * TwentyFourtyGame.GAP);
		remoteGroup.addActor(remoteScores);
		remoteGroup.addActor(remoteDrawableGrid);
		remoteGroup.addActor(opponent);
		remoteGroup.setX(600);

		stage.addActor(localGroup);
		stage.addActor(remoteGroup);

		stage.addListener(new LocalInputHandler(localGrid));

		/* After creating the screen, start the game. */
		TwentyFourtyGame.setState(RunningState.getInstance());
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

		if (!screenHandler.getScreen().hasOverlay()
				&& networking.isConnectionLost()) {
			screenHandler.getScreen().addConnectionLostOverlay();
		}

		if (remoteInput.getMoveValidator().getIrregularity() &&
			!screenHandler.getScreen().hasOverlay() &&
			!TwentyFourtyGame.isContinuing()) {
			screenHandler.getScreen().addBoardOverlay(false,false);
			screenHandler.getScreen().addLWOverlay(false, true, localGrid);
			this.setOpponentLabel("CHEATER", Color.RED);
		}
		
		if (TwentyFourtyGame.isDisqualified() &&
				!screenHandler.getScreen().hasOverlay()) {
				screenHandler.getScreen().addBoardOverlay(false, true);
				this.setYouLabel("CHEATER", Color.RED);
			}
		
		if (TwentyFourtyGame.isWaiting()) {
			this.setYouLabel("WAITING", Color.RED);
		}

		// add a waiting overlay to the screen to cover the opponents grid.
		else if (remoteGrid.getPossibleMoves() == 0
				&& !screenHandler.getScreen().hasOverlay()) {
			logger.info(className,
					"Opponent is out of moves! Waiting for the player...");
			this.setOpponentLabel("WAITING", Color.RED);
			screenHandler.getScreen().addBoardOverlay(true, false);
		}

		TwentyFourtyGame.getState().update(localGrid, remoteGrid);
	}

	@Override
	public void dispose() {
		networking.deleteObserver(remoteInput);
	}
}