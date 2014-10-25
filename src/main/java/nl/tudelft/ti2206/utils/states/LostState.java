package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The LostState class is used to define a possible state of the game.
 * It is the state where the player has lost according to a lose condition.
 */
public class LostState implements GameState{
	/** The unique singleton instance of this class. */
	private static LostState instance = new LostState();

	/** Overrides the default constructor. */
	private LostState() {}

	/** @return The singleton instance of the state*/
	public static LostState getInstance() {
		return instance;
	}

	@Override
	public void update(Grid grid) {	
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {
	}
}
