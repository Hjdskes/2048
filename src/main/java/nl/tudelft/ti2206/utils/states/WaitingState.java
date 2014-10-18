package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.utils.log.Logger;

/**
 * The WaitingState class is used to define a possible state of the game. It is
 * the state where a player must wait for the opponent to play until he is our
 * of legal moves.
 */

public class WaitingState implements GameState {
	
	/** The unique singleton instance of this class. */
	private static WaitingState instance = new WaitingState();
	
	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Overrides the default constructor. */
	private WaitingState() {}
	
	/** @return The singleton instance of the state*/
	public static WaitingState getInstance() {
		return instance;
	}

	/** Constructor for testing only. */
	public WaitingState(ScreenHandler h) {
		screenHandler = h;
	}

	@Override
	public void update(Grid grid) {
	}

	@Override
	public void update(Grid localGrid, Grid remoteGrid) {
		/*
		 * Lose condition: I lose while waiting if he gets a higher score than
		 * mine
		 */
		if (localGrid.getScore() < remoteGrid.getScore()) {
			lose(remoteGrid);
		}

		/*
		 * Win condition: i win because he couldn't beat my score and we both
		 * ran out of moves
		 */
		else if (localGrid.getScore() > remoteGrid.getScore()
				&& (remoteGrid.getPossibleMoves() == 0)) {
			win(localGrid);
		}
		
		/* Win condition: Both ran out of moves, same score but i was first. */
		else if ((localGrid.getPossibleMoves() == 0) && 
				(remoteGrid.getPossibleMoves() == 0)
				&& (localGrid.getScore() == remoteGrid.getScore())) {
			win(localGrid);
		}
	}

	private void lose(Grid remoteGrid) {
		logger.info(className,
				"Local player lost the multiplayer game. The score of the remote player: "
						+ Integer.toString(remoteGrid.getScore()));

		TwentyFourtyGame.setState(LostState.getInstance());
		screenHandler.getScreen().addLWOverlay(true, false, null);
	}

	private void win(Grid localGrid) {
		logger.info(className,
				"Local player won the multiplayer game. The score of the local player: "
						+ Integer.toString(localGrid.getScore()));

		TwentyFourtyGame.setState(WonState.getInstance());
		screenHandler.getScreen().addLWOverlay(true, true, null);
	}
}
