package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WaitScreen extends Screen {
	/** The main label. */
	private Label label;
	private Table table;

	public WaitScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label("Waiting for connection...", AssetHandler.getSkin());
	}

	@Override
	public void create() {
		super.create();

		table.add(label);
		table.getCell(label).padTop(20).padBottom(10).row();

		table.setFillParent(true);
		stage.addActor(table);
	}

	@Override
	public void update() {
		super.update();

		if (Networking.isConnected()) {
			ScreenHandler.add(new MultiGameScreen());
		}
	}
}
