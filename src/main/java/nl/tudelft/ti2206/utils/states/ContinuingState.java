package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;

/**
 * The ContinuingState class is used to define a possible state of the game. It
 * is the state where the player has chosen to continue after he has won in a
 * singleplayer game.
 */
public class ContinuingState implements GameState {

	/** The unique singleton instance of this class. */
	private static ContinuingState instance = new ContinuingState();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Overrides the default constructor. */
	private ContinuingState() {
	}

	/** @return The singleton instance of the state */
	public static ContinuingState getInstance() {
		return instance;
	}

	@Override
	public void update(Grid grid) {
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {
		/*
		 * In a multiplayer mode a player can enter Continueing if the other
		 * player has cheater because of that we need to add the overlay after
		 * the screen has been reset
		 */
		if (!screenHandler.getScreen().hasOverlay()) {
			screenHandler.getScreen().addBoardOverlay(false, false);

		}

	}

}
