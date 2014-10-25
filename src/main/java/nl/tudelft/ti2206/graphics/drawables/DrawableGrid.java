package nl.tudelft.ti2206.graphics.drawables;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * The DrawableGrid gives a visual representation of its underlying Grid. 
 */
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

	/**
	 * Constructs the DrawableGrid and fills the DrawableTile array with tiles,
	 * adding observers on the fly.
	 */
	public DrawableGrid(Tile[] tiles) {
		setMetrics();
		this.drawableTiles = new DrawableTile[Grid.NTILES];
		this.region = new TextureRegion(AssetHandler.getInstance().getSkin()
				.get("grid", Texture.class));

		for (int i = 0; i < drawableTiles.length; i++) {
			drawableTiles[i] = new DrawableTile(i, tiles[i].getValue());
			tiles[i].addObserver(drawableTiles[i]);
		}
	}

	/**
	 * Constructor for testing purposes only.
	 */
	public DrawableGrid(DrawableTile[] tiles, TextureRegion region) {
		setMetrics();
		this.drawableTiles = tiles;
		this.region = region;
	}

	/** Sets the size and location of the grid. */
	private void setMetrics() {
		setX(GRID_X);
		setY(GRID_Y);
		setWidth(GRID_WIDTH);
		setHeight(GRID_HEIGHT);
	}
	
	/**
	 * Updates the grid, by updating all the Tiles it contains and checking for
	 * a new highest value.
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		for (int i = 0; i < drawableTiles.length; i++) {
			drawableTiles[i].act(delta);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		for (int i = 0; i < drawableTiles.length; i++) {
			drawableTiles[i].draw(batch, parentAlpha);
		}
	}
}
