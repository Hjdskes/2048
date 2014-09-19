package nl.tudelft.ti2206.net;

import java.io.Serializable;

import nl.tudelft.ti2206.gameobjects.Grid;

public abstract class Sendable implements Serializable {
	
	private Grid grid;

	public Sendable(Grid grid) {
		this.setGrid(grid);
	}

	/**
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	
}
