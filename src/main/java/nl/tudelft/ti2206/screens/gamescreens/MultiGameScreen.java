package nl.tudelft.ti2206.screens.gamescreens;

import nl.tudelft.ti2206.drawables.DrawableGrid;
import nl.tudelft.ti2206.drawables.Scores;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.LocalInputHandler;
import nl.tudelft.ti2206.handlers.RemoteInputHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.net.Networking;
import nl.tudelft.ti2206.screens.Screen;
import nl.tudelft.ti2206.screens.drawbehaviour.DrawBeige;

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

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

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
		localGrid.setObjectName("LocalGrid");
		remoteGrid.setObjectName("RemoteGrid");

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
	public MultiGameScreen(Stage stage, Grid grid, Label label, Group group,
			Scores scores, Networking netMock) {
		this.stage = stage;
		this.localGrid = grid;
		this.remoteGrid = grid;
		this.you = label;
		this.opponent = label;
		this.localGroup = group;
		this.remoteGroup = group;
		this.localScores = scores;
		this.remoteScores = scores;
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

		if (networking.isConnectionLost()) {
			screenHandler.getScreen().addConnectionLostOverlay();
		}

		if (TwentyFourtyGame.isWaiting()) {
			logger.info(className,
					"Local player is out of moves! Waiting for the remote player...");
			this.setYouLabel("WAITING", Color.RED);
		} else if (remoteGrid.getPossibleMoves() == 0) {
			logger.info(className,
					"Remote player is out of moves! Waiting for the local player...");
			this.setOpponentLabel("WAITING", Color.RED);
		}

		TwentyFourtyGame.getState().update(localGrid, remoteGrid);
	}

	@Override
	public void dispose() {
		networking.deleteObserver(remoteInput);
	}
}