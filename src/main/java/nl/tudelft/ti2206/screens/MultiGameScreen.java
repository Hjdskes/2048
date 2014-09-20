package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.InputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MultiGameScreen extends Screen {
	private Group localGroup;
	private Group remoteGroup;

	public MultiGameScreen() {
		Gdx.graphics.setDisplayMode(2 * TwentyFourtyGame.GAME_WIDTH, TwentyFourtyGame.GAME_HEIGHT, false);
		stage = new Stage();
		localGroup = new Group();
		remoteGroup = new Group();
	}

	// TODO: check if method is used
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(2 * TwentyFourtyGame.GAME_WIDTH, TwentyFourtyGame.GAME_HEIGHT, true);
		System.out.println("method call; \"resize\" in MultiGameScreen.java");
	}

	@Override
	public void create() {
		super.create();
		/* Create our local groups and actors. */
		Grid localGrid = new Grid(false);
		localGrid.setName("local");
		localGroup.addActor(new ScoreDisplay(localGrid));
		localGroup.addActor(localGrid);
		stage.addListener(new InputHandler(localGrid));

		/* Create our remote groups and actors. */
		Grid remoteGrid = new Grid(true);
		remoteGrid.setName("remote");
		remoteGroup.addActor(new ScoreDisplay(remoteGrid));
		remoteGroup.addActor(remoteGrid);
		remoteGroup.setX(600);
		remoteGroup.setY(0);

		stage.addActor(localGroup);
		stage.addActor(remoteGroup);
	}
}
