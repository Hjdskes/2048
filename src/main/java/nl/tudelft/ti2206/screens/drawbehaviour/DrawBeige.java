package nl.tudelft.ti2206.screens.drawbehaviour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * DrawBeige is used as one of the draw-method implementations. 
 * Implements DrawBehavior interface.
 */
public class DrawBeige implements DrawBehavior {
	/** The scene graph. */
	private Stage stage;
		
	/**	Constructs a new DrawBeige object 
	 * @param stage
	 *				current scene graph
	 */
	public DrawBeige(Stage stage) {
		this.stage = stage;
	}
		
	/**	Object's implementation of draw */
	@Override
	public void draw() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
}
