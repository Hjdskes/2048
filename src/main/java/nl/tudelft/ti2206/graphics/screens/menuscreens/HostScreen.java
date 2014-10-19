package nl.tudelft.ti2206.graphics.screens.menuscreens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.graphics.screens.gamescreens.MultiGameScreen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.net.Networking;

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
	/** The label displaying the connection status. */
	private Label remote;
	/** The label listing all addresses. */
	private Label addresses;
	/** The label showing the current port number. */
	private Label portLabel;

	/** The button to cancel and go back to the main menu. */
	private MenuButton cancel;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();
	/** The singleton Networking instance. */
	private static Networking networking = Networking.getInstance();

	/** List of error messages. */
	private List<String> errorMessages = new ArrayList<String>();
	/** Message for port in use. */
	private String errorMessage;

	/** Constructs a new HostScreen. */
	public HostScreen() {
		stage = new Stage();
		table = new Table();
		String labelString = " Your opponent's destiny\r\nlies beyond one of these:";
		label = new Label(labelString, assetHandler.getSkin());

		portLabel = new Label("On port TCP/" + networking.getPortNumber()
				+ ", duh!", assetHandler.getSkin());

		remote = new Label("Waiting for connection...", assetHandler.getSkin());

		/* Show addresses to user to share with opponent. */
		addresses = new Label(networking.localAddresses(),
				assetHandler.getSkin());
		cancel = new MenuButton();

		initErrorMessages();
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for injecting mock objects. For testing purposes only. */
	public HostScreen(Stage stage, Table table, Label label, Label portLabel,
			MenuButton cancel) {
		this.stage = stage;
		this.table = table;
		this.remote = label;
		this.addresses = label;
		this.label = label;
		this.cancel = cancel;
		this.portLabel = portLabel;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Picks a random string to show when the user has lost the game. */
	private void initErrorMessages() {
		errorMessages.add("What have you done!?");
		errorMessages.add("Fix this, NOW!");
		errorMessages.add("I'm sad!");
		errorMessages.add("You're doing it WRONG.");

		int index = new Random().nextInt(errorMessages.size());
		errorMessage = errorMessages.get(index);
	}

	@Override
	public void create() {
		super.create();

		/* Start hosting if not already doing so. */
		if (!networking.isInitialized() || !networking.isConnected()) {
			networking.startServer();
		}

		table.add(label);
		table.getCell(label).padTop(5).padBottom(5).row();
		table.add(addresses);
		table.getCell(addresses).padTop(5).padBottom(5).row();
		table.add(portLabel);
		table.getCell(portLabel).padTop(5).padBottom(5).row();
		table.add(remote);
		table.getCell(remote).padTop(5).padBottom(5).row();

		table.setFillParent(true);
		stage.addActor(table);
		stage.addActor(cancel);
	}

	@Override
	public void update() {
		super.update();

		if (networking.isServerSocketInitialized()) {
			if (networking.isConnected()) {
				label.setText("Connection established!");
				remote.setText("Let's play!");

				/* Remote user is connected, show remote address. */
				String addr = networking.getRemoteAddress();
				addresses.setText(addr);

				ScreenHandler.getInstance().set(new MultiGameScreen());
			}
		} else if (networking.errorOccured()) {
			String error = "Networking error!";

			if (!networking.getLastError().isEmpty()) {
				error = networking.getLastError();
			}

			portLabel.setText(errorMessage);
			label.setText(error);
			addresses.setText(":("); // HostScreen sad :(
			remote.setText("Is the port in use?"); // you bet!
		} else {
			remote.setText("Waiting for connection...");
		}
	}
}
