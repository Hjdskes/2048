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
	private float destX, destY;
	private float startX, startY;

	public SlidingTile(Tile start) {
		this.setVisible(false);
		startX = start.getX();
		startY = start.getY();
		setStartCoordinates();
	}

	private void setStartCoordinates() {
		setX(startX);
		setY(startY);
	}

	public void move(Tile start, float destX, float destY) {
		// reset to clear any move still in progress
		this.reset();
		
		setValue(start.getValue());
		this.setVisible(true);
		this.destX = destX;
		this.destY = destY;
		
		move = new MoveToAction();
		move.setDuration(.15f);
		move.setPosition(destX, destY);
	
		this.addAction(move);
		logger.debug("SlidingTile", "Moving from (" + getX() + ", " + getY() + ") to (" + destX + ", " + destY + ")");
	}

	/** Sets the tile according to the value provided. */
	private void setValue(int value) {
		tile = AssetHandler.getInstance().getSkin().getRegion("tile" + value);
	}

	public void reset() {
		this.setVisible(false);
		this.setStartCoordinates();
		move = null;
		destX = 0;
		destY = 0;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (getX() == destX && getY() == destY) {
			reset();
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isVisible()) {
			batch.draw(tile, getX(), getY(), TILE_WIDTH, TILE_HEIGHT);
		}
	}
}
