package nl.tudelft.ti2206.drawables;

import java.util.Observable;
import java.util.Observer;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DrawableTile extends Actor implements Observer {
	/** Coordinates and offsets used to position the Tile. */
	private static final int TILE_WIDTH = 81;
	private static final int TILE_HEIGHT = 81;
	private static final int TILE_X = 115;
	private static final int TILE_Y = 403;

	/** The area of a Texture the Tile will use to draw itself. */
	private TextureRegion region;
	/** The Skin to retrieve all textures from. */
	private Skin skin;
	/** The Action used to perform a spawn animation. */
	private ScaleToAction spawnAction;
	/** The Action used to perform a merge animation. */
	private ScaleToAction mergeAction;
	/** The power of two that makes the value (e.g. 2^1, 2^2, 2^3, 2^4, ...). */
	private int value;
	/** The index into the Grid array. */
	private int index;

	public DrawableTile(int index, int value) {
		this.index = index;
		this.value = value;
		this.skin = AssetHandler.getInstance().getSkin();
		this.region = new TextureRegion();
		setSprite(skin);
	}

	/**
	 * Constructor for testing purposes: takes a Skin and a TextureRegion as
	 * parameters to allow mocking.
	 * 
	 * @param skin
	 *            The Skin object to retrieve all Drawables and styles from.
	 * @param region
	 *            The TextureRegion this Tile will use to draw itself.
	 */
	public DrawableTile(int index, int value, Skin skin, TextureRegion region) {
		this.index = index;
		this.value = value;
		this.skin = skin;
		this.region = region;
		setSprite(skin);
	}

	@Override
	public void update(Observable o, Object arg) {
		Tile tile = (Tile) o;
		if (value != tile.getValue()) {
			value = tile.getValue();
			setSprite(skin);
		}
		if (tile.shouldMerge()) {
			merge();
		}
		// if (tile.shouldMove()) {
		// move();
		// }
		if (tile.shouldSpawn()) {
			spawn();
		}
		finishActions();
	}

	private void finishActions() {
		if (value == 0) {
			if (getActions().contains(spawnAction, true)) {
				spawnAction.finish();
			}
			if (getActions().contains(mergeAction, true)) {
				mergeAction.finish();
			}
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

	@Override
	public void act(float delta) {
		if (getScaleX() > 1) {
			mergeAction.act(delta);
		} else if (getScaleX() < 1 && value != 0) {
			spawnAction.act(delta);
		} else if (getScaleX() < 1) {
			setScale(1);
		} else if (value == 0 && mergeAction != null) {
			mergeAction.finish();
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
		default:
			return TILE_X;
		}
	}

	@Override
	public float getY() {
		if (index >= 12) {
			return TILE_Y - 3 * TILE_HEIGHT - 3 * TwentyFourtyGame.GAP;
		} else if (index >= 8) {
			return TILE_Y - 2 * TILE_HEIGHT - 2 * TwentyFourtyGame.GAP;
		} else if (index >= 4) {
			return TILE_Y - TILE_HEIGHT - TwentyFourtyGame.GAP;
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
}
