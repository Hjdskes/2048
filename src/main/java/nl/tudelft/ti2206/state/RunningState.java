package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.GameScreen;
import nl.tudelft.ti2206.screens.LoseScreen;
import nl.tudelft.ti2206.screens.MultiGameScreen;
import nl.tudelft.ti2206.screens.MultiLoseScreen;
import nl.tudelft.ti2206.screens.MultiWinScreen;
import nl.tudelft.ti2206.screens.WinScreen;

/**
 * The RunningState class is used to define a possible state of the game.
 * It is the state where the game is active and the player has not lost yet.
 */

public class RunningState implements GameState {

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();
	
	@Override
	public void update(Grid grid) {
		if (grid.getCurrentHighestTile() == 2048) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			GameScreen.screenHandler.add(new WinScreen());
		} else if (grid.getPossibleMoves() == 0) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			GameScreen.screenHandler.add(new LoseScreen());
		}
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {
		/* Win condition: I merged 2048 */
		if (localgrid.getCurrentHighestTile() == 2048) { 
			
			logger.info(className,
					"Local player won the multiplayer game. The score of the local player: "
					+ Integer.toString(localgrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			MultiGameScreen.screenHandler.add(new MultiWinScreen());

		}
		
		/* Waiting condition: I am out of moves */
		if (localgrid.getPossibleMoves() == 0) {
			
			logger.info(className,
					"Local player ran out of moves. The score of the local player: "
					+ Integer.toString(localgrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getWaitingState());
		}
		
		/* Losing condition: He merged 2048 */
		if(remotegrid.getCurrentHighestTile() == 2048) {
			
			logger.info(className,
					"Local player lost the multiplayer game. The score of the remote player: "
					+ Integer.toString(remotegrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			MultiGameScreen.screenHandler.add(new MultiLoseScreen());
		}
		
		/* Losing condition: Same score, but he was first. */
		if ((localgrid.getPossibleMoves() == 0)  && 
			(remotegrid.getPossibleMoves() == 0) &&
			(localgrid.getScore() < remotegrid.getScore())) { 
			
			logger.info(className,
					"Local player lost the multiplayer game. The score of the remote player: "
					+ Integer.toString(remotegrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			MultiGameScreen.screenHandler.add(new MultiLoseScreen());
		}
	}
}