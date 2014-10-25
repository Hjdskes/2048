package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The ContinuingState class is used to define a possible state of the game.
 * It is the state where the player has chosen to continue after he has won
 * in a singleplayer game.
 */
public class ContinuingState implements GameState{
	/** The unique singleton instance of this class. */
	private static ContinuingState instance = new ContinuingState();

	/** Overrides the default constructor. */
	private ContinuingState() {		
	}

	/** @return The singleton instance of the state*/
	public static ContinuingState getInstance() {
		return instance;
	}

	@Override
	public void update(Grid grid) {	
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {	
	}
}
