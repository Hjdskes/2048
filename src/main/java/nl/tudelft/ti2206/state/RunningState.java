package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.overlays.LoseScreen;
import nl.tudelft.ti2206.screens.overlays.MultiLoseScreen;

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

	@Override
	public void update(Grid grid) {
		if (grid.getCurrentHighestTile() == 11
				&& !TwentyFourtyGame.isContinuing()) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			screenHandler.findScreen("GameScreen")
					.addOverlay(false, true, grid);
		} else if (grid.getPossibleMoves() == 0) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			screenHandler.add(new LoseScreen(grid));
		}
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {
		/* Win condition: I merged 2048 */
		if (localgrid.getCurrentHighestTile() == 11) {
			logger.info(className,
					"Local player won the multiplayer game. The score of the local player: "
							+ Integer.toString(localgrid.getScore()));

			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			screenHandler.findScreen("MultiGameScreen").addOverlay(true, true,
					null);
		}

		/* Waiting condition: I am out of moves */
		if (localgrid.getPossibleMoves() == 0) {
			logger.info(className,
					"Local player ran out of moves. The score of the local player: "
							+ Integer.toString(localgrid.getScore()));

			TwentyFourtyGame.setState(TwentyFourtyGame.getWaitingState());
		}

		/* Losing condition: He merged 2048 */
		if (remotegrid.getCurrentHighestTile() == 11) {
			logger.info(className,
					"Local player lost the multiplayer game. The score of the remote player: "
							+ Integer.toString(remotegrid.getScore()));

			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			screenHandler.add(new MultiLoseScreen());
		}

		/* Losing condition: Same score, but he was first. */
		if ((localgrid.getPossibleMoves() == 0)
				&& (remotegrid.getPossibleMoves() == 0)
				&& (localgrid.getScore() < remotegrid.getScore())) {

			logger.info(className,
					"Local player lost the multiplayer game. The score of the remote player: "
							+ Integer.toString(remotegrid.getScore()));

			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			screenHandler.add(new MultiLoseScreen());
		}
	}
}