package nl.tudelft.ti2206.gameobjects;

public class MovingTile extends Tile {

	AnimatedTile tile;
	private float current;
	private float destination;
	private boolean isXMove;
	private boolean isVisible;

	public MovingTile(AnimatedTile tile) {
		super(tile.getValue());
		this.tile = tile;
		current = 0;
		destination = 0;
		isVisible = true;
	}

	public void update() {
		if (current < destination)
			current = current + 3;
		else if (current > destination)
			current = current - 3;
		else {
			isVisible = false;
			tile.setVisible(true);	
		}
	}
	
	public void move(float current, float destination, boolean isXMove) {
		this.isXMove = isXMove;
		tile.setVisible(false);
		this.current = current;
		this.destination = destination;
	}
	
	public boolean isXMove() {
		return isXMove;
	}
	
	public float getCurCoordinate() {
		return current;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
}
