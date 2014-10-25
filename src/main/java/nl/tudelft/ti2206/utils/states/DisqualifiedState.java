package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.gameobjects.Grid;
/**
 * The DisqualifiedState class is used to define a possible state of the game. It is
 * the state where a player has been disqualified for cheating.
 */
public class DisqualifiedState implements GameState{

	/** The unique singleton instance of this class. */
	private static DisqualifiedState instance = new DisqualifiedState();
	
	/** Overrides the default constructor. */
	private DisqualifiedState() {		
	}
	
	/** @return The singleton instance of the state*/
	public static DisqualifiedState getInstance() {
		return instance;
	}
	
	@Override
	public void update(Grid grid) {	
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {		
	}

}
