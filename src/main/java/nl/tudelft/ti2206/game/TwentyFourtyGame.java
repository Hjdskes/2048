package nl.tudelft.ti2206.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TwentyFourtyGame implements ApplicationListener {

	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 600;

	private Stage stage;
	private Skin skin;

	public TwentyFourtyGame() {
	}

	@Override
	public void create() {
		AssetManager manager = new AssetManager();

		manager.load("src/main/resources/fonts/fonts.atlas", TextureAtlas.class);
		manager.load("src/main/resources/images/icons/icons.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/tiles/tiles.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/scoretiles/scoretiles.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/buttons/buttons.atlas",
				TextureAtlas.class);
		manager.load("src/main/resources/images/grid.png", Texture.class);
		manager.load("src/main/resources/images/lostoverlay.png", Texture.class);
		manager.load("src/main/resources/images/wonoverlay.png", Texture.class);

		manager.finishLoading();

		TextureAtlas fonts = manager.get(
				"src/main/resources/fonts/fonts.atlas", TextureAtlas.class);
		TextureAtlas icons = manager.get("src/main/resources/images/icons/icons.atlas", TextureAtlas.class);
		TextureAtlas tiles = manager.get("src/main/resources/images/tiles/tiles.atlas", TextureAtlas.class);
		TextureAtlas scoretiles = manager.get("src/main/resources/images/scoretiles/scoretiles.atlas", TextureAtlas.class);
		TextureAtlas buttons = manager.get("src/main/resources/images/buttons/buttons.atlas", TextureAtlas.class);
	
		skin = new Skin(Gdx.files.internal("skin.json"));
		skin.addRegions(fonts);
		skin.addRegions(icons);
		skin.addRegions(tiles);
		skin.addRegions(scoretiles);
		skin.addRegions(buttons);
		skin.add("grid", manager.get("src/main/resources/images/grid.png", Texture.class));
		skin.add("lostoverlay", manager.get("src/main/resources/images/lostoverlay.png", Texture.class));
		skin.add("wonoverlay", manager.get("src/main/resources/images/wonoverlay.png", Texture.class));

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Label label = new Label("Hello!", skin);
		stage.addActor(label);

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		/* center camera: true */
		stage.getViewport().update(GAME_WIDTH, GAME_HEIGHT, true);
	}

	@Override
	public void resume() {
	}

}
