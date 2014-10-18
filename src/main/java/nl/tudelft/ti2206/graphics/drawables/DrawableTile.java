package nl.tudelft.ti2206.graphics.drawables;

import java.util.Observable;
import java.util.Observer;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.log.Logger;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DrawableTile extends Actor implements Observer {
	/** Coordinates and offsets used to position the Tile. */
	public static final int TILE_WIDTH = 81;
	private static final int TILE_HEIGHT = 81;
	public static final int TILE_X = 115;
	private static final int TILE_Y = 403;

	/** The singleton reference to the Logger. */
	private static Logger logger = Logger.getInstance();

	/** The area of a Texture the Tile will use to draw itself. */
	private TextureRegion region;
	/** The Skin to retrieve all textures from. */
	private Skin skin;

	/** The Action used to perform a spawn animation. */
	private ScaleToAction spawnAction;
	/** The Action used to perform a merge animation. */
	private ScaleToAction mergeAction;
	/** The Action used to perform a slide animation. */
	private MoveToAction moveAction;

	/** The power of two that makes the value (e.g. 2^1, 2^2, 2^3, 2^4, ...). */
	private int value;
	/** The index into the Grid array. */
	private int index;

	/** The x and y coordinates belonging to the current index. */
	private float baseX, baseY;

	public DrawableTile(int index, int value) {
		this.index = index;
		this.value = value;
		this.skin = AssetHandler.getInstance().getSkin();
		this.region = new TextureRegion();
		setSprite(skin);
		setMetrics();
		updateBaseCoordinates();
		setBaseCoordinates();
	}

	/**
	 * Constructor for testing purposes: takes a TextureRegion as parameters to
	 * allow mocking.
	 * 
	 * @param region
	 *            The TextureRegion this Tile will use to draw itself.
	 */
	public DrawableTile(int index, int value, TextureRegion region) {
		this.index = index;
		this.value = value;
		this.skin = AssetHandler.getInstance().getSkin();
		this.region = region;
		setSprite(skin);
		setMetrics();
		updateBaseCoordinates();
		setBaseCoordinates();
	}

	private void setMetrics() {
		setWidth(TILE_WIDTH);
		setHeight(TILE_HEIGHT);
	}

	@Override
	public void update(Observable o, Object arg) {
		Tile tile = (Tile) o;
		updateValue(tile);
		updateIndex(tile);
		updateAnimations(tile);
	}

	/** Updates the value if the value of the observed Tile has changed. */
	private void updateValue(Tile tile) {
		if (value != tile.getValue()) {
			value = tile.getValue();
			setSprite(skin);
		}
	}

	/** Updates the index if the index of the observed Tile has changed. */
	private void updateIndex(Tile tile) {
		if (index != tile.getIndex()) {
			index = tile.getIndex();
			updateBaseCoordinates();
			setBaseCoordinates();
		}
	}

	/** Updates the animations after the observed Tile has changed. */
	private void updateAnimations(Tile tile) {
		if (tile.shouldMerge()) {
			merge();
		}
		if (tile.shouldMove()) {
			move(tile.getDestination());
		}
		if (tile.shouldSpawn()) {
			spawn();
		}
		finishActions();
	}

	/**
	 * Finished the current animations if stopping conditions are met by the
	 * observed Tile.
	 */
	private void finishActions() {
		if (value == 0) {
			if (getActions().contains(spawnAction, true)) {
				spawnAction.finish();
			}
			if (getActions().contains(mergeAction, true)) {
				mergeAction.finish();
			}
			if (getActions().contains(moveAction, true)) {
				moveAction.finish();
				setBaseCoordinates();
			}
			getActions().clear();
		}
	}

	/**
	 * Initializes a new spawn animation.
	 */
	public void spawn() {
		this.setScale(0.6f);
		spawnAction = new ScaleToAction();
		spawnAction.setScale(1f);
		spawnAction.setDuration(.3f);
		this.addAction(spawnAction);
		logger.debug("Tile", "Spawning a Tile at index " + index + "...");
	}

	/**
	 * Initializes a new merge animation.
	 */
	public void merge() {
		this.setScale(1.4f);
		mergeAction = new ScaleToAction();
		mergeAction.setScale(1f);
		mergeAction.setDuration(.3f);
		this.addAction(mergeAction);
		logger.debug("Tile", "Merging tiles at index " + index + "...");
	}

	/**
	 * Starts a sliding animation to the x and y coordinates, defined for the
	 * index provided.
	 * 
	 * @param destIndex
	 *            The index the Tile needs to move to.
	 */
	public void move(int destIndex) {
		index = destIndex;
		updateBaseCoordinates();
		moveAction = new MoveToAction();
		moveAction.setPosition(baseX, baseY);
		moveAction.setDuration(.08f);
		this.addAction(moveAction);
		logger.debug("Tile", "Moving Tile from (" + getX() + ", " + getY()
				+ ") to (" + baseX + ", " + baseY + ")...");
	}

	@Override
	public void act(float delta) {
		if (value != 0 && (getX() != baseX || getY() != baseY)) {
			moveAction.act(delta);
		}
		if (getScaleX() > 1) {
			mergeAction.act(delta);
		} else if (getScaleX() < 1 && value != 0) {
			spawnAction.act(delta);
		} else if (getScaleX() < 1) {
			setScale(1);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX() + getXOffset(), getY() + getYOffset(),
				getOriginX(), getOriginY(), getWidth(), getHeight(),
				getScaleX(), getScaleY(), 0);
	}

	/**
	 * Calculates the x coordinate belonging to the current index and sets the
	 * baseX variable to the value calculated.
	 */
	public void setBaseX() {
		switch (this.index % 4) {
		case 1:
			baseX = TILE_X + TILE_WIDTH + TwentyFourtyGame.GAP;
			break;
		case 2:
			baseX = TILE_X + 2 * (TILE_WIDTH + TwentyFourtyGame.GAP);
			break;
		case 3:
			baseX = TILE_X + 3 * (TILE_WIDTH + TwentyFourtyGame.GAP);
			break;
		default:
			baseX = TILE_X;
			break;
		}
	}

	/**
	 * Calculates the y coordinate belonging to the current index and sets the
	 * baseY variable to the value calculated.
	 */
	public void setBaseY() {
		if (index >= 12) {
			baseY = TILE_Y - 3 * TILE_HEIGHT - 3 * TwentyFourtyGame.GAP;
		} else if (index >= 8) {
			baseY = TILE_Y - 2 * TILE_HEIGHT - 2 * TwentyFourtyGame.GAP;
		} else if (index >= 4) {
			baseY = TILE_Y - TILE_HEIGHT - TwentyFourtyGame.GAP;
		} else {
			baseY = TILE_Y;
		}
	}

	/** Updates the baseX and baseY variables. */
	private void updateBaseCoordinates() {
		setBaseX();
		setBaseY();
	}

	/**
	 * sets the x and y coordinates of this tile to the values of baseX and
	 * baseY.
	 */
	private void setBaseCoordinates() {
		setX(baseX);
		setY(baseY);
	}

	/**
	 * @return The offset for the x-coordinate, depending on the current scale.
	 */
	private float getXOffset() {
		return getWidth() / 2 - getWidth() * getScaleX() / 2;
	}

	/**
	 * @return The offset for the y-coordinate, depending on the current scale.
	 */
	private float getYOffset() {
		return getHeight() / 2 - getHeight() * getScaleY() / 2;
	}

	/**
	 * Moves the TextureRegion to the new Texture, belonging to the current
	 * value of the Tile.
	 * 
	 * @param skin
	 *            The skin to get the sprite from.
	 */
	private void setSprite(Skin skin) {
		region.setRegion(skin.getRegion("tile" + this.value));
	}

	/** Returns the value of the DrawableTile. */
	public int getValue() {
		return value;
	}

	/** Returns the index of the DrawableTile. */
	public int getIndex() {
		return index;
	}
}
