package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

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

	/** The gap in between tiles, Grid edges, etc. */
	private static final int GAP = 15;

	/** The value (e.g. 2, 4, 8, 16, ...). */
	private int value;

	/** The index into the Grid array. */
	private int index;

	/** The label of the Tile, displaying its value. */
	private Label label;

	/** Indicates whether this Tile has been merged in the current move. */
	private boolean isMerged;

	private ScaleToAction spawnAction;

	/**
	 * Defines a rectangular area of a texture, kind of like a viewport, on the
	 * whole image.
	 */
	private TextureRegion region;

	/**
	 * Creates a new Tile with the given value.
	 * 
	 * @param index
	 *            The index into the Grid array.
	 * @param value
	 *            The value of the Tile.
	 */
	public Tile(int index, int value) {
		this.value = value;
		this.index = index;
		this.isMerged = false;

		this.region = new TextureRegion();
		this.label = new Label(Integer.toString(value), AssetHandler.getSkin());

		setSprite();
		setLabel();
		setActors();

		spawn();
	}

	/**
	 * @return The value of the Tile.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the Tile.
	 * 
	 * @param value
	 *            The new value.
	 */
	public void setValue(int value) {
		this.value = value;
		setSprite();
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
	 * Resets the value of the Tile.
	 */
	public void resetValue() {
		this.value = 0;
	}

	/**
	 * Resets the Tile to its default state.
	 */
	public void reset() {
		resetValue();
		isMerged = false;
	}

	/**
	 * Doubles the value of the Tile, or sets it to 2 if the current value is 0.
	 */
	public void doubleValue() {
		if (this.isEmpty()) {
			value = 2;
		} else {
			value *= 2;
		}

		setSprite();
	}

	/**
	 * Sets the size of the scale to 0.5, to trigger the spawn action.
	 */
	public void spawn() {
		this.setScale(.5f);
	}

	/**
	 * Moves the TextureRegion to the new Texture, belonging to the current
	 * value of the Tile.
	 */
	private void setSprite() {
		/* Temporary fix to allow headless testing. */
		if (Gdx.app.getGraphics() != null) {
			region.setRegion(AssetHandler.getSkin().getRegion(
					"tile" + this.value));
		}
	}

	/**
	 * Sets the label displaying the value of the tile to the designated
	 * position.
	 */
	private void setLabel() {
		float x = getX();
		float y = getY();

		label.setStyle(AssetHandler.getSkin().get("brown-text",
				LabelStyle.class));
		label.setText(Integer.toString(value));
		label.setX(x);
		label.setY(y);
		label.setAlignment(Align.center);
		label.setCenterPosition(x + 40, y + 40);

		if (value == 0) {
			label.setVisible(false);
		} else {
			label.setVisible(true);
		}
	}

	/**
	 * Sets the actions for the tile.s
	 */
	private void setActors() {
		spawnAction = new ScaleToAction();
		spawnAction.setDuration(.3f);
		spawnAction.setScale(1);

		this.addAction(spawnAction);
	}

	@Override
	public float getX() {
		switch (this.index % 4) {
		case 1:
			return TILE_X + TILE_WIDTH + GAP;
		case 2:
			return TILE_X + 2 * (TILE_WIDTH + GAP);
		case 3:
			return TILE_X + 3 * (TILE_WIDTH + GAP);
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
			return TILE_Y + TILE_HEIGHT + GAP;
		} else if (index < 12) {
			return TILE_Y + 2 * (TILE_HEIGHT + GAP);
		} else if (index < 16) {
			return TILE_Y + 3 * (TILE_HEIGHT + GAP);
		} else {
			return TILE_Y;
		}
	}

	/**
	 * @return The Y offset for the y coordinate, depending on the current
	 *         scale.
	 */
	private float getXOffset() {
		return getWidth() / 2 - getWidth() * getScaleX() / 2;
	}

	/**
	 * @return The Y offset for the y coordinate, depending on the current
	 *         scale.
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

	@Override
	public void act(float delta) {
		if (getScaleX() != 1) {
			spawnAction.act(delta);
		}
		setSprite();
		setLabel();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX() + getXOffset(), getY() + getYOffset(),
				getOriginX(), getOriginY(), getWidth(), getHeight(),
				getScaleX(), getScaleY(), 0);
		if (label.isVisible()) {
			label.draw(batch, parentAlpha);
		}
	}
}
