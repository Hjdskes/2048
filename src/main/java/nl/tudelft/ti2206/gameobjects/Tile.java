package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * The Tile class represents the tiles you move around while playing 2048.
 */
public class Tile extends Actor {
	/** The width of the Tile. */
	private static final int TILE_WIDTH = 81;

	/** The height of the Tile. */
	private static final int TILE_HEIGHT = 81;

	/** The base Tile x-coordinate. */
	private static final int TILE_X = 115;

	/** The base Tile y-coordinate. */
	private static final int TILE_Y = 115;

	/** The Skin to retrieve all Drawables from. */
	private Skin skin;

	/** The Action used to perform a spawn animation. */
	private ScaleToAction spawnAction;

	/** The Action used to perform a merge animation. */
	private ScaleToAction mergeAction;

	/**
	 * Defines a rectangular area of a texture, kind of like a viewport, on the
	 * whole image.
	 */
	private TextureRegion region;

	/** The value (e.g. 2, 4, 8, 16, ...). */
	private int value;

	/** The index into the Grid array. */
	private int index;

	/** Indicates whether this Tile has been merged in the current move. */
	private boolean isMerged;

	/**
	 * Creates a new Tile with the given value.
	 * 
	 * @param index
	 *            The index into the Grid array.
	 * @param value
	 *            The value of the Tile.
	 */
	public Tile(int index, int value) {
		this.skin = AssetHandler.getSkin();
		this.region = new TextureRegion();

		setValue(value);
		setIndex(index);
		setMerged(false);

		if (value > 0) {
			System.out
					.println("Tile: I'm spawning with a value bigger than zero!");
			spawn();
		}
	}

	/**
	 * Constructor for testing purposes: takes a Skin and a TextureRegion as
	 * parameters to allow mocking.
	 * 
	 * @param index
	 *            The index into the Grid array.
	 * @param value
	 *            The value of the Tile.
	 * @param skin
	 *            The Skin object to retrieve all Drawables and styles from.
	 * @param region
	 *            The TextureRegion this Tile will use to draw itself.
	 */
	public Tile(int index, int value, Skin skin, TextureRegion region) {
		this.skin = skin;
		this.region = region;
		setValue(value);
		setIndex(index);
		setMerged(false);
	}

	/**
	 * @return The value of the Tile.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the Tile and updates its sprite.
	 * 
	 * @param value
	 *            The new value.
	 */
	public void setValue(int value) {
		this.value = value;
		setSprite(skin);
	}

	/**
	 * @return The index of the Tile into the Grid array.
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Sets the index of the Tile into the Grid array.
	 * 
	 * @param index
	 *            The new index.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return True if the tile is empty (value 0), false otherwise.
	 */
	public boolean isEmpty() {
		return this.value == 0;
	}

	/**
	 * @return True if this Tile has been merged, false otherwise.
	 */
	public boolean isMerged() {
		return this.isMerged;
	}

	/**
	 * Sets the merged state of this Tile.
	 *
	 * @param isMerged
	 *            The new merged state.
	 */
	public void setMerged(boolean isMerged) {
		this.isMerged = isMerged;
	}

	/**
	 * Resets the Tile to its default state.
	 */
	public void reset() {
		setValue(0);
		isMerged = false;
		if (mergeAction != null) {
			mergeAction.finish();
		}
	}

	/**
	 * Doubles the value of the Tile, or sets it to 2 if the current value is 0.
	 */
	public void doubleValue() {
		if (this.isEmpty()) {
			setValue(2);
		} else {
			setValue(value * 2);
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

	@Override
	public void act(float delta) {
		if (getScaleX() > 1) {
			mergeAction.act(delta);
		} else if (getScaleX() < 1 && !isEmpty()) {
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

	@Override
	public float getX() {
		switch (this.index % 4) {
		case 1:
			return TILE_X + TILE_WIDTH + TwentyFourtyGame.GAP;
		case 2:
			return TILE_X + 2 * (TILE_WIDTH + TwentyFourtyGame.GAP);
		case 3:
			return TILE_X + 3 * (TILE_WIDTH + TwentyFourtyGame.GAP);
		case 0: /* Fallthrough. */
		default:
			return TILE_X;
		}
	}

	@Override
	public float getY() {
		if (index < 4) {
			return TILE_Y;
		} else if (index < 8) {
			return TILE_Y + TILE_HEIGHT + TwentyFourtyGame.GAP;
		} else if (index < 12) {
			return TILE_Y + 2 * (TILE_HEIGHT + TwentyFourtyGame.GAP);
		} else if (index < 16) {
			return TILE_Y + 3 * (TILE_HEIGHT + TwentyFourtyGame.GAP);
		} else {
			return TILE_Y;
		}
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

	@Override
	public float getWidth() {
		return TILE_WIDTH;
	}

	@Override
	public float getHeight() {
		return TILE_HEIGHT;
	}
}
