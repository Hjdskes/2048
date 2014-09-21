package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.gameobjects.StringConstants;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The HostScreen is the screen the host sees. It will list his IP addresses, so
 * he can easily pass those on to the client.
 */
public class HostScreen extends Screen {
	/** The table used for positioning all Actors. */
	private Table table;

	/** The main label. */
	private Label label;

	/** The label with displaying the connection status. */
	private Label remote;

	/** The label listing all addresses. */
	private Label addresses;

	/** The button to cancel and go back to the main menu. */
	private MenuButton cancel;

	/** Constructs a new HostScreen. */
	public HostScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label(StringConstants.OPPONENT_DESTINY,
				AssetHandler.getSkin());

		remote = new Label(StringConstants.CONNECTION_WAITING,
				AssetHandler.getSkin());

		/* Show addresses to user to share with opponent. */
		addresses = new Label(Networking.strAddresses(), AssetHandler.getSkin());
		cancel = new MenuButton();
	}

	/** Constructor for injecting mock objects. For testing purposes only. */
	public HostScreen(Stage stage, Table table, Label label, MenuButton cancel) {
		this.stage = stage;
		this.table = table;
		this.remote = label;
		this.addresses = label;
		this.label = label;
		this.cancel = cancel;
	}

	@Override
	public void create() {
		super.create();

		/* Start hosting if not already doing so. */
		if (!Networking.isInitialized() || !Networking.isConnected()) {
			Networking.startServer();
		}
		
		table.add(label);
		table.getCell(label).padTop(20).padBottom(10).row();
		table.add(addresses);
		table.getCell(addresses).padTop(10).padBottom(20).row();
		table.add(remote);
		table.getCell(remote).padTop(20).padBottom(50).row();

		table.setFillParent(true);
		stage.addActor(table);
		stage.addActor(cancel);
	}

	@Override
	public void update() {
		super.update();

		if (Networking.isServerSocketInitialized()) {
			if (Networking.isConnected()) {
				label.setText(StringConstants.CONNECTION_ESTABLISHED);
				remote.setText(StringConstants.LETS_PLAY);

				/* Remote user is connected, show remote address. */
				String addr = Networking.getRemoteAddress();
				addresses.setText(addr);

				Networking.sendString("[START]\r\n");
				ScreenHandler.add(new MultiGameScreen());
			} else {
				String error = Networking.getLastError();

				if (error.compareTo("") != 0) {
					label.setText(error);
				}
			}
		} else {
			remote.setText(StringConstants.CONNECTION_WAITING);
		}
	}
}
