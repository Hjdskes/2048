package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.log.Logger;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class SlidingTile extends Actor {

	/** The width of the Tile. */
	private static final int TILE_WIDTH = 81;

	/** The height of the Tile. */
	private static final int TILE_HEIGHT = 81;

	private static Logger logger = Logger.getInstance();

	private MoveToAction move;
	private TextureRegion tile;

	public SlidingTile(Tile start) {
		this.setVisible(false);
	}

	private void setCoordinates(Tile start) {
		setX(start.getX());
		setY(start.getX());
	}

	public void move(Tile start, float destX, float destY) {
		setValue(start.getValue());
		setCoordinates(start);
		this.setVisible(true);
		
		move = new MoveToAction();
		move.setDuration(.3f);
		move.setPosition(destX, destY);
		
		Action action = new Action() {
			@Override
			public boolean act(float delta) {
				reset();
				return true;
			}
		};
		
		this.addAction(new SequenceAction(move, action));
		
		logger.debug("SlidingTile", "Moving to (" + destX + ", " + destY + ")");
	}

	/** Sets the tile according to the value provided. */
	private void setValue(int value) {
		tile = AssetHandler.getInstance().getSkin().getRegion("tile" + value);
	}

	public void reset() {
		this.setVisible(false);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isVisible()) {
			batch.draw(tile, getX(), getY(), TILE_WIDTH, TILE_HEIGHT);
		}
	}
}
