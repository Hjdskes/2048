package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.LocalInputHandler;
import nl.tudelft.ti2206.handlers.RemoteInputHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * The MultiGameScreen is the screen for a multiplayer game.
 */
public class MultiGameScreen extends Screen {
	private Grid localGrid;
	private Grid remoteGrid;
	private Label you;
	private Label opponent;
	private Group localGroup;
	private Group remoteGroup;
	private ScoreDisplay localScores;
	private ScoreDisplay remoteScores;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton Networking instance. */
	private static Networking networking = Networking.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	private RemoteInputHandler remoteInput;

	/** Constructs a new MultiGameScreen. */
	public MultiGameScreen() {
		Gdx.graphics.setDisplayMode(2 * TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();

		localGrid = new Grid(false);
		remoteGrid = new Grid(false);

		you = new Label("You", assetHandler.getSkin());
		opponent = new Label("Opponent", assetHandler.getSkin());

		localGroup = new Group();
		remoteGroup = new Group();

		localScores = new ScoreDisplay(localGrid);
		remoteScores = new ScoreDisplay(remoteGrid);

		remoteInput = new RemoteInputHandler(remoteGrid);
		networking.addObserver(remoteInput);
	}

	/** Constructor for testing purposes only */
	public MultiGameScreen(Stage stage, Grid grid, Label label, Group group,
			ScoreDisplay scores) {
		this.stage = stage;
		this.localGrid = grid;
		this.remoteGrid = grid;
		this.you = label;
		this.opponent = label;
		this.localGroup = group;
		this.remoteGroup = group;
		this.localScores = scores;
		this.remoteScores = scores;
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
		remoteGroup.addActor(remoteGrid);
		remoteGroup.addActor(opponent);
		remoteGroup.setX(600);

		stage.addActor(localGroup);
		stage.addActor(remoteGroup);

		stage.addListener(new LocalInputHandler(localGrid));
	}

	@Override
	public void update() {
		super.update();

		if (networking.isConnectionLost()) {
			screenHandler.add(new ConnectionLostScreen());
		}

		if (localGrid.getCurrentHighestTile() == 2048
				|| (remoteGrid.isFull() && remoteGrid.getPossibleMoves() == 0)) {
			Gdx.app.log(this.getClass().getSimpleName(),
					"Local player won the multiplayer game. The score of the local player: "
							+ Integer.toString(localGrid.getScore()));
			TwentyFourtyGame.setState(GameState.WON);
			screenHandler.add(new MultiWinScreen());
		} else if ((localGrid.isFull() && localGrid.getPossibleMoves() == 0)
				|| remoteGrid.getCurrentHighestTile() == 2048) {
			Gdx.app.setLogLevel(Application.LOG_INFO);
			Gdx.app.log(this.getClass().getSimpleName(),
					"Local player lost the multiplayer game. The score of the remote player: "
							+ Integer.toString(remoteGrid.getScore()));
			TwentyFourtyGame.setState(GameState.LOST);
			screenHandler.add(new MultiLoseScreen());
		}
	}

	@Override
	public void dispose() {
		networking.deleteObserver(remoteInput);
	}
}
