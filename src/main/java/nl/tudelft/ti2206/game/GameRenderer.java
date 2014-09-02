package nl.tudelft.ti2206.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameRenderer {
	private GameWorld world;
	private OrthographicCamera cam;
	private ShapeRenderer r;
	private int gameWidth, gameHeight;

	public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
		this.world = world;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;

		cam = new OrthographicCamera();
		cam.setToOrtho(true, gameWidth, gameHeight);

		r = new ShapeRenderer();
		r.setProjectionMatrix(cam.combined);
	}

	public void render(float delta, float runTime) {
		// draw black screen to avoid flickering
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderShapes(delta);
	}

	private void renderShapes(float delta) {
		r.begin(ShapeType.Filled);
		r.setColor(Color.CYAN);
		// draw rectangle in the middle of the screen
		r.rect(gameWidth / 2 - 25, gameHeight / 2 - 25, 50, 50);
		r.end();
	}
}
