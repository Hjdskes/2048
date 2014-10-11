package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.overlays.MultiLoseScreen;
import nl.tudelft.ti2206.screens.overlays.MultiWinScreen;

/**
 * The WaitingState class is used to define a possible state of the game.
 * It is the state where a player must wait for the opponent to play until he is our of legal moves.
 */

public class WaitingState implements GameState{
	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	@Override
	public void update(Grid grid) {
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {
		/* Lose condition: I lose while waiting if he gets a higher score than mine*/
		if (localgrid.getScore() < remotegrid.getScore()) {
			logger.info(className,
					"Local player lost the multiplayer game. The score of the remote player: "
					+ Integer.toString(remotegrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			screenHandler.add(new MultiLoseScreen());
		}

		/* Win condition: i win because he couldn't beat my score and we both ran out of moves*/
		if (localgrid.getScore() > remotegrid.getScore() 
			&& (remotegrid.getPossibleMoves() == 0)) {
			logger.info(className,
					"Local player won the multiplayer game. The score of the local player: "
					+ Integer.toString(localgrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			screenHandler.add(new MultiWinScreen());
		}
	}
}
