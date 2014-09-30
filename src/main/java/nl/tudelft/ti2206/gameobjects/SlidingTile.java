package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.log.Logger;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class SlidingTile extends Actor {

	/** The width of the Tile. */
	private static final int TILE_WIDTH = 81;

	/** The height of the Tile. */
	private static final int TILE_HEIGHT = 81;

	private static Logger logger = Logger.getInstance();

	private MoveToAction move;
	private TextureRegion tile;

	public SlidingTile(Tile start) {
		setValue(0);
		setAction();
		setCoordinates(start);
		this.setVisible(false);
	}

	private void setAction() {
		move = new MoveToAction();
		move.setDuration(.3f);
		this.addAction(move);
	}

	private void setCoordinates(Tile start) {
		setX(start.getX());
		setY(start.getY());
	}

	public void move(int value, float destX, float destY) {
		setValue(value);
		this.setVisible(true);
		move.setPosition(destX, destY);

		logger.debug("SlidingTile", "Moving to (" + destX + ", " + destY + ")");
	}

	/** Sets the tile according to the value provided. */
	private void setValue(int value) {
		tile = AssetHandler.getInstance().getSkin().getRegion("tile" + value);
	}
	
	public void restart() {
		this.setVisible(false);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isVisible()) {
			batch.draw(tile, getX(), getY(), TILE_WIDTH, TILE_HEIGHT);
		}
	}
}
