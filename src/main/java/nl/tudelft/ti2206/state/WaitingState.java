package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.MultiGameScreen;
import nl.tudelft.ti2206.screens.MultiLoseScreen;
import nl.tudelft.ti2206.screens.MultiWinScreen;

public class WaitingState implements GameState{

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	@Override
	public void update(Grid grid) {

	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {

		/** Lose condition: I lose while waiting if he gets a higher score than mine*/
		if(localgrid.getScore() < remotegrid.getScore()) {

			logger.info(className,
					"Local player lost the multiplayer game. The score of the remote player: "
					+ Integer.toString(remotegrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			MultiGameScreen.screenHandler.add(new MultiLoseScreen());
		}
		
		/** Win condition: i win because he couldn't beat my score and we both ran out of moves*/
		if(localgrid.getScore() > remotegrid.getScore() 
			&& (remotegrid.getPossibleMoves() == 0)) {

			logger.info(className,
					"Local player won the multiplayer game. The score of the local player: "
					+ Integer.toString(localgrid.getScore()));
			
			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			MultiGameScreen.screenHandler.add(new MultiWinScreen());
		}
		
	}
}
