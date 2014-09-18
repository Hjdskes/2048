package nl.tudelft.ti2206.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Drawable {
	/** Draws this drawable. The Drawable itself should be aware of its own coordinates and size. */
	public void draw(Batch batch);
}
