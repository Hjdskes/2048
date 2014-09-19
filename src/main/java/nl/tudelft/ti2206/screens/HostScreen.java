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

public class HostScreen extends ScreenAdapter {
	private Stage stage;
	private Table table;
	private Label label;
	private TextButton cancel;
	private TextButton play;

	public HostScreen(String message) {
		stage = new Stage();
		table = new Table();
		label = new Label("Your IP Addresses:", AssetHandler.getSkin());
		cancel = new TextButton("Cancel", AssetHandler.getSkin());
		play = new TextButton("Continue", AssetHandler.getSkin());
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
		table.add(label).padTop(20).padBottom(20).row();
		//table.add(cancel).padBottom(20).padLeft(20).row();
		//table.add(play).padRight(20).padBottom(20).row();

		table.setFillParent(true);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);

		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((TwentyFourtyGame) Gdx.app.getApplicationListener())
						.popScreen();
			}
		});
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//((TwentyFourtyGame) Gdx.app.getApplicationListener())
				//		.addScreen(new WaitScreen());
			}
		});
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
