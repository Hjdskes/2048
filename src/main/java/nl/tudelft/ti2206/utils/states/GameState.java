package nl.tudelft.ti2206.utils.states;

import nl.tudelft.ti2206.gameobjects.Grid;

/**
 * The GameState interface is used to create a hierarchy of possible states in the game.
 * The state methods are then called polymorphically.
 */
public interface GameState {
	/**
	* The update(grid) method is used to update singleplayer states.
	*/
	public void update(Grid grid);

	/**
	 * The update(grid,grid) method is used to update multiplayer states.
	 */
	public void update(Grid localgrid, Grid remotegrid);
}
