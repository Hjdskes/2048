package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The WaitScreen is displayed when the client has to wait for the connection
 * to be made.
 */
public class WaitScreen extends Screen {
	/** The main label. */
	private Label label;
	private Table table;

	/** Constructs a new WaitScreen. */
	public WaitScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label("Waiting for connection...", AssetHandler.getInstance().getSkin());
	}

	/** Constructor for testing purposes. */
	public WaitScreen(Stage stage, Table table, Label label) {
		this.stage = stage;
		this.table = table;
		this.label = label;
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

		if (Networking.getInstance().isConnected()) {
			ScreenHandler.add(new MultiGameScreen());
		}
	}
}
