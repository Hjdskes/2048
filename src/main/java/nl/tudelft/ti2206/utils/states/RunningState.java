package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.utils.log.Logger;

/**
 * The RunningState class is used to define a possible state of the game. It is
 * the state where the game is active and the player has not lost yet.
 */

public class RunningState implements GameState {
	
	/** The singleton reference to the Logger instance. */
	private static RunningState instance = new RunningState();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Overrides the default constructor. */
	private RunningState() {}
	
	/** @return The singleton instance of the state*/
	public static RunningState getInstance() {
		return instance;
	}
	
	@Override
	public void update(Grid grid) {
		if (grid.getCurrentHighestTile() == 11) {
			TwentyFourtyGame.setState(WonState.getInstance());
			screenHandler.getScreen().addLWOverlay(false, true, grid);
		} else if (grid.getPossibleMoves() == 0) {
			TwentyFourtyGame.setState(LostState.getInstance());
			screenHandler.getScreen().addLWOverlay(false, false, grid);
		}
	}

	@Override
	public void update(Grid localGrid, Grid remoteGrid) {
		/* Win condition: I merged 2048 */
		if (localGrid.getCurrentHighestTile() == 11) {
			localWon(localGrid);
		}

		/* Losing condition: He merged 2048 */
		else if (remoteGrid.getCurrentHighestTile() == 11) {
			remoteWon(remoteGrid);
		}

		/* Losing condition: Both are out of moves, he has a higher score. */
		else if ((localGrid.getPossibleMoves() == 0)
				&& (remoteGrid.getPossibleMoves() == 0)
				&& (localGrid.getScore() < remoteGrid.getScore())) {
			remoteScoreHigher(remoteGrid);
		}
		
		/* Win condition: He is out of moves, i have a higher score. */
		else if ((localGrid.getPossibleMoves() == 0) && 
				(remoteGrid.getPossibleMoves() == 0)
				&& (localGrid.getScore() > remoteGrid.getScore())) {
			localScoreHigher(localGrid);
		}
		
		/* Lose condition: Both ran out of moves, same score but he was first. */
		else if ((localGrid.getPossibleMoves() == 0) && 
				(remoteGrid.getPossibleMoves() == 0)
				&& (localGrid.getScore() == remoteGrid.getScore())) {
			remoteWon(localGrid);
		}
		
		/* Waiting condition: I am out of moves */
		else if (localGrid.getPossibleMoves() == 0) {
			localLost(localGrid);
		}
	}
	
	private void localWon(Grid localGrid) {
		logger.info(className,
				"Local player won the multiplayer game. The score of the local player: "
						+ Integer.toString(localGrid.getScore()));

		TwentyFourtyGame.setState(WonState.getInstance());
		screenHandler.getScreen().addLWOverlay(true, true, null);
	}
	
	private void localLost(Grid localGrid) {
		logger.info(className,
				"Local player ran out of moves. The score of the local player: "
						+ Integer.toString(localGrid.getScore()));
		
		screenHandler.getScreen().addMultiWaitScreenOverlay(true);
		TwentyFourtyGame.setState(WaitingState.getInstance());
	}
	
	private void remoteWon(Grid remoteGrid) {
		logger.info(className,
				"Local player lost the multiplayer game. The score of the remote player: "
						+ Integer.toString(remoteGrid.getScore()));

		TwentyFourtyGame.setState(LostState.getInstance());
		screenHandler.getScreen().addLWOverlay(true, false, null);
	}
	
	private void remoteScoreHigher(Grid remoteGrid) {
		logger.info(className,
				"Local player lost the multiplayer game. The score of the remote player: "
						+ Integer.toString(remoteGrid.getScore()));

		TwentyFourtyGame.setState(LostState.getInstance());
		screenHandler.getScreen().addLWOverlay(true, false, null);
	}
	
	private void localScoreHigher(Grid localGrid) {
		logger.info(className,
				"Local player won the multiplayer game. The score of the local player: "
						+ Integer.toString(localGrid.getScore()));

		TwentyFourtyGame.setState(WonState.getInstance());
		screenHandler.getScreen().addLWOverlay(true, true, null);
	}
	
	/** For testing purposes only */
	public void setScreenHandler(ScreenHandler handler) {
		screenHandler = handler;
	}
}