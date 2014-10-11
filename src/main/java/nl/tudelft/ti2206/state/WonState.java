package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The WonState class is used to define a possible state of the game.
 * It is the state where the player has won according to a win condition .
 */
public class WonState implements GameState{
	
	/**
	 * The update(grid) method is used to update singleplayer states.
	 */
	@Override
	public void update(Grid grid) {
	}

	/**
	 * The update(grid,grid) method is used to update multiplayer states.
	 */
	@Override
	public void update(Grid localgrid, Grid remotegrid) {	
	}

}
