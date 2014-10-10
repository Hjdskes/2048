package nl.tudelft.ti2206.screens.menuscreens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.net.Networking;
import nl.tudelft.ti2206.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.screens.Screen;
import nl.tudelft.ti2206.screens.gamescreens.MultiGameScreen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The HostScreen is the screen the host sees. It will list his IP addresses, so
 * he can easily pass those on to the client.
 */
public class HostScreen extends Screen {
	/** The string used in the label of the remote. */
	public static final String LETS_PLAY = "Let's play!";

	/** Text displayed when waiting for a connection. */
	public static final String CONNECTION_WAITING = "Waiting for connection...";

	/** Text displayed when a connection is established. */
	public static final String CONNECTION_ESTABLISHED = "Connection established!";

	/** The text for the main label. */
	public static final String OPPONENT_DESTINY = " Your opponent's destiny\r\nlies beyond one of these:";

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

	/** Random number selector for random error message. */
	private Random rand = new Random();
	
	/** Message for port in use. */
	private String errorMessage = "What have you done!?";

	/** Constructs a new HostScreen. */
	public HostScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label(OPPONENT_DESTINY, assetHandler.getSkin());

		portLabel = new Label("On port TCP/" + networking.getPortNumber()
				+ ", duh!", assetHandler.getSkin());

		remote = new Label(CONNECTION_WAITING, assetHandler.getSkin());

		/* Show addresses to user to share with opponent. */
		addresses = new Label(networking.localAddresses(),
				assetHandler.getSkin());
		cancel = new MenuButton();

		errorMessages.add("What have you done!?");
		errorMessages.add("Fix this, NOW!");
		errorMessages.add("I'm sad!");
		errorMessages.add("You're doing it WRONG.");
		
		int index = rand.nextInt(errorMessages.size());
		errorMessage = errorMessages.get(index);
		this.setDrawBehavior( new DrawBeige(stage));

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
		this.setDrawBehavior( new DrawBeige(stage));
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
				label.setText(CONNECTION_ESTABLISHED);
				remote.setText(LETS_PLAY);

				/* Remote user is connected, show remote address. */
				String addr = networking.getRemoteAddress();
				addresses.setText(addr);

				ScreenHandler.getInstance().add(new MultiGameScreen());
			} else if (networking.errorOccured()) {

				// This never really happens, but whatever...

				String error = networking.getLastError();

				if (error.compareTo("") != 0) {
					label.setText(error);
				}
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
			remote.setText(CONNECTION_WAITING);
		}
	}
}
