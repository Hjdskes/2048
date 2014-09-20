package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The MenuScreen is used on start-up, to have the user choose between
 * singleplayer, hosting a game or connecting to another player.
 */
public class MenuScreen implements Screen {
	/** The stage which holds all Actors. */
	private Stage stage;

	/** The table used for positioning all Actors. */
	private Table table;

	/** The main label. */
	private Label label;

	/** The button to launch a singleplayer game. */
	private TextButton singlePlayer;

	/** The button to go to the host menu. */
	private TextButton hostGame;

	/** The button to go to the client menu. */
	private TextButton connect;

	/** Constructs a new MenuScreen. */
	public MenuScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label("Choose your destiny!", AssetHandler.getSkin());
		singlePlayer = new TextButton("Singleplayer", AssetHandler.getSkin());
		hostGame = new TextButton("Host a game", AssetHandler.getSkin());
		connect = new TextButton("Connect to your friend",
				AssetHandler.getSkin());
	}

	@Override
	public void show() {
		table.add(label).padBottom(40).row();
		table.add(singlePlayer).padBottom(20).row();
		table.add(hostGame).padBottom(20).row();
		table.add(connect).padBottom(20).row();

		table.setFillParent(true);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);

		singlePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new GameScreen());
			}
		});
		hostGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new HostScreen());
			}
		});
		connect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new ClientScreen());
			}
		});
	}

	@Override
	public void draw() {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	@Override
	public boolean isOverlay() {
		return false;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void update() {
		stage.act();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
