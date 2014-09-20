package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.CancelButton;
import nl.tudelft.ti2206.buttons.PlayButton;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HostScreen implements Screen {
	private Stage stage;
	private Label label;
	private Label remote;
	private Label addresses;
	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void create() {
		stage = new Stage();
		Table table = new Table();

		label = new Label(
				"Your opponent's destiny\r\nlies beyond one of these:\r\n",
				AssetHandler.getSkin());
		
		remote = new Label("Waiting for connection...", AssetHandler.getSkin());

		// start hosting:
		if (!Networking.isInitialized() || !Networking.isConnected())
			Networking.startServer();
		
		CancelButton cancel = new CancelButton();
		PlayButton play = new PlayButton();

		table.add(label).padTop(20).padBottom(20).row();

		Label addresses = new Label(Networking.strAddresses(), AssetHandler.getSkin());		
		
		table.add(label).padTop(20).padBottom(5).row();
		table.add(addresses).padTop(5).padBottom(20).row();
		table.add(remote).padTop(5).padBottom(20).row();

		table.setFillParent(true);
		stage.addActor(table);
		stage.addActor(cancel);
		stage.addActor(play);

		Gdx.input.setInputProcessor(stage);
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

		if (Networking.isInitialized()) {
			if (Networking.isConnected()) {
				String addr = Networking.getRemoteAddress();
				remote.setText("Remote: " + addr);
				
			}
		} else {
			remote.setText("Waiting for connection..");
			
		}

	}
}
