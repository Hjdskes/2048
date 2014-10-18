package nl.tudelft.ti2206.graphics.screens.drawbehaviour;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * SimpleDraw is used as one of the draw-method implementations. 
 * Implements DrawBehavior interface.
 */
public class DrawSimple implements DrawBehavior {

	/** The scene graph. */
	private Stage stage;
	
	/**	Constructs a new SimpleDraw object 
	 * @param stage
	 *				current scene graph
	 */
	public DrawSimple(Stage stage) {
		this.stage = stage;
	}
	
	/**	Object's implementation of draw	 */
	@Override
	public void draw() {
		stage.draw();
	}
}
