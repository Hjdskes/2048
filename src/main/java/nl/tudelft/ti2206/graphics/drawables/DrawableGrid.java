package nl.tudelft.ti2206.graphics.drawables;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DrawableGrid extends Group {
	/** Coordinates and offsets used to position the Grid. */
	private static final int GRID_WIDTH = 400;
	private static final int GRID_HEIGHT = 400;
	private static final int GRID_X = 100;
	public static final int GRID_Y = 100;

	/** The area of a Texture the Grid will use to draw itself. */
	private TextureRegion region;

	/** The array containing all sixteen tiles. */
	private DrawableTile[] drawableTiles;

	public DrawableGrid(Tile[] tiles) {
		this.drawableTiles = new DrawableTile[Grid.NTILES];
		this.region = new TextureRegion(AssetHandler.getInstance().getSkin()
				.get("grid", Texture.class));

		for (int i = 0; i < drawableTiles.length; i++) {
			drawableTiles[i] = new DrawableTile(i, tiles[i].getValue());
			tiles[i].addObserver(drawableTiles[i]);
		}
	}

	/**
	 * Constructor for testing purposes to allow mocking.
	 */
	public DrawableGrid(DrawableTile[] tiles, Skin skin, TextureRegion region) {
		this.drawableTiles = tiles;
		this.region = region;
	}
	
	/**
	 * Updates the grid, by updating all the Tiles it contains and checking for
	 * a new highest value.
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		for(int i = 0; i < drawableTiles.length; i++) {
			drawableTiles[i].act(delta);
		}
	}

	@Override
	public float getX() {
		return GRID_X;
	}

	@Override
	public float getY() {
		return GRID_Y;
	}

	@Override
	public float getWidth() {
		return GRID_WIDTH;
	}

	@Override
	public float getHeight() {
		return GRID_HEIGHT;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		for (int i = 0; i < drawableTiles.length; i++) {
			drawableTiles[i].draw(batch, parentAlpha);
		}
	}
}
