package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends ScreenAdapter {
	private Stage stage;
	private Table table;
	private Label label;
	private TextButton singlePlayer;
	private TextButton hostGame;
	private TextButton connect;

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
	public void render(float delta) {
		/* Draw beige background in the screen. */
		Gdx.gl.glClearColor(.976f, .969f, .933f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Tell all actors to update... */
		stage.act();
		/* ... and to redraw themselves. */
		stage.draw();
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
				((TwentyFourtyGame) Gdx.app.getApplicationListener())
						.addScreen(new GameScreen());
			}
		});
		hostGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((TwentyFourtyGame) Gdx.app.getApplicationListener())
						.addScreen(new HostScreen("LaLaLa"));
			}
		});
		connect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((TwentyFourtyGame) Gdx.app.getApplicationListener())
						.addScreen(new ClientScreen());
			}
		});
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
