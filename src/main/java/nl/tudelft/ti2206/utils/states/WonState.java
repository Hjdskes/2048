package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The WonState class is used to define a possible state of the game.
 * It is the state where the player has won according to a win condition .
 */
public class WonState implements GameState{
	
	/** The unique singleton instance of this class. */
	private static WonState instance = new WonState();
	
	/** Overrides the default constructor. */
	private WonState() {		
	}
	
	/** @return The singleton instance of the state*/
	public static WonState getInstance() {
		return instance;
	}
	
	@Override
	public void update(Grid grid) {
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {	
	}

}
