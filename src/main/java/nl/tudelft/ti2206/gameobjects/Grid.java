package nl.tudelft.ti2206.gameobjects;

public class Grid {
	private Square[] grid;
	
	public Grid() {
		grid = new Square[4];
	}
	
	public Square[] getGrid() {
		return grid;
	}
	
	public void occupySquare(int index, Square occupant) {
		grid[index] = occupant;
	}
}
