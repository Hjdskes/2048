package nl.tudelft.ti2206.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface DrawBehavior {
	void draw();
}

class SimpleDraw implements DrawBehavior {

	private Stage stage;
	
	public SimpleDraw(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void draw() {
		stage.draw();
	}
}

class DrawBeige implements DrawBehavior {
	
	private Stage stage;
	
	public DrawBeige(Stage stage) {
		this.stage = stage;
	}
	@Override
	public void draw() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
}