package nl.tudelft.ti2206.graphics.screens.drawbehaviour;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * DrawSimple simply draws no background in its stage. 
 */
public class DrawSimple implements DrawBehavior {
	/** The scene graph. */
	private Stage stage;

	/**
	 * Constructs a new DrawSimple object.
	 * @param stage
	 *				The current scene graph.
	 */
	public DrawSimple(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void draw() {
		stage.draw();
	}
}
