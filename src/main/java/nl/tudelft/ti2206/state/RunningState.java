package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;

/**
 * The RunningState class is used to define a possible state of the game. It is
 * the state where the game is active and the player has not lost yet.
 */

public class RunningState implements GameState {

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The singleton reference to the ScreenHandler class. */
	private ScreenHandler screenHandler = ScreenHandler.getInstance();

	public RunningState() {}
	/** Constructor for mock insertion only. */
	public RunningState(ScreenHandler h) {
		this.screenHandler = h;
	}
	
	@Override
	public void update(Grid grid) {
		if (grid.getCurrentHighestTile() == 11) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			screenHandler.getScreen().addLWOverlay(false, true, grid);
		} else if (grid.getPossibleMoves() == 0) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
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

		/* Losing condition: Same score, but he was first. */
		else if ((localGrid.getPossibleMoves() == 0)
				&& (remoteGrid.getPossibleMoves() == 0)
				&& (localGrid.getScore() < remoteGrid.getScore())) {
			remoteScoreHigher(remoteGrid);
		}
		
		/* Win condition: Same score, but I was first. */
		else if ((localGrid.getPossibleMoves() == 0)
				&& (remoteGrid.getPossibleMoves() == 0)
				&& (localGrid.getScore() > remoteGrid.getScore())) {
			localScoreHigher(localGrid);
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

		TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
		screenHandler.getScreen().addLWOverlay(true, true, null);
	}
	
	private void localLost(Grid localGrid) {
		logger.info(className,
				"Local player ran out of moves. The score of the local player: "
						+ Integer.toString(localGrid.getScore()));
		
		screenHandler.getScreen().addMultiWaitScreenOverlay(true);
		TwentyFourtyGame.setState(TwentyFourtyGame.getWaitingState());
	}
	
	private void remoteWon(Grid remoteGrid) {
		logger.info(className,
				"Local player lost the multiplayer game. The score of the remote player: "
						+ Integer.toString(remoteGrid.getScore()));

		TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
		screenHandler.getScreen().addLWOverlay(true, false, null);
	}
	
	private void remoteScoreHigher(Grid remoteGrid) {
		logger.info(className,
				"Local player lost the multiplayer game. The score of the remote player: "
						+ Integer.toString(remoteGrid.getScore()));

		TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
		screenHandler.getScreen().addLWOverlay(true, false, null);
	}
	
	private void localScoreHigher(Grid localGrid) {
		logger.info(className,
				"Local player won the multiplayer game. The score of the local player: "
						+ Integer.toString(localGrid.getScore()));

		TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
		screenHandler.getScreen().addLWOverlay(true, true, null);
	}
}