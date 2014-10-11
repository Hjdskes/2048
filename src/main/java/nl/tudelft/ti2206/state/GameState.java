package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.gameobjects.Grid;

public interface GameState {

	public void update(Grid grid);
	
	public void update(Grid localgrid, Grid remotegrid);
}
