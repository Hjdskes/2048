package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.handlers.LocalInputHandler;
import nl.tudelft.ti2206.handlers.RemoteInputHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * The MultiGameScreen is the screen for a multiplayer game.
 */
public class MultiGameScreen extends Screen {
	/** The Group holding the local Actors. */
	private Group localGroup;

	/** The Group holding all Actors from the remote. */
	private Group remoteGroup;

	/** Constructs a new MultiGameScreen. */
	public MultiGameScreen() {
		Gdx.graphics.setDisplayMode(2 * TwentyFourtyGame.GAME_WIDTH,
				TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();
		localGroup = new Group();
		remoteGroup = new Group();
	}

	@Override
	public void create() {
		super.create();

		/* Create our local groups and actors. */
		Grid localGrid = new Grid(false);
		localGroup.addActor(new ScoreDisplay(localGrid));
		localGroup.addActor(localGrid);
		stage.addListener(new LocalInputHandler(localGrid));

		/* Create our remote groups and actors. */
		Grid remoteGrid = new Grid(true);
		remoteGroup.addActor(new ScoreDisplay(remoteGrid));
		remoteGroup.addActor(remoteGrid);
		remoteGroup.setX(600);

		stage.addActor(localGroup);
		stage.addActor(remoteGroup);

		RemoteInputHandler remoteInput = new RemoteInputHandler(remoteGrid);
		Networking.setRemoteInput(remoteInput);
		
		Networking.sendString("GRID[" + localGrid.toString() + "]\r\n");
	}
}
