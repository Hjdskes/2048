package nl.tudelft.ti2206.screens;

import java.util.Observable;
import java.util.Observer;

import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The HostScreen is the screen the host sees. It will list his IP addresses, so
 * he can easily pass those on to the client.
 */
public class HostScreen extends Screen implements Observer {
	/** The string used in the label of the remote. */
	public static final String LETS_PLAY = "Let's play!";

	/** Text displayed when waiting for a connection. */
	public static final String CONNECTION_WAITING = "Waiting for connection...";

	/** Text displayed when a connection is established. */
	public static final String CONNECTION_ESTABLISHED = "Connection established!";

	/** The text for the main label. */
	public static final String OPPONENT_DESTINY = "Your opponent's destiny\r\nlies beyond one of these:";

	/** The table used for positioning all Actors. */
	private Table table;

	/** The main label. */
	private Label label;

	/** The label displaying the connection status. */
	private Label remote;

	/** The label listing all addresses. */
	private Label addresses;

	/** The button to cancel and go back to the main menu. */
	private MenuButton cancel;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();
	
	/** The singleton Networking instance. */ 
	private static Networking networking = Networking.getInstance();
	
	
	/** Constructs a new HostScreen. */
	public HostScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label(OPPONENT_DESTINY, assetHandler.getSkin());

		remote = new Label(CONNECTION_WAITING, assetHandler.getSkin());

		/* Show addresses to user to share with opponent. */
		addresses = new Label(networking.localAddresses(), assetHandler.getSkin());
		cancel = new MenuButton();
		
		networking.addObserver(this);
		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for injecting mock objects. For testing purposes only. */
	public HostScreen(Stage stage, Table table, Label label, MenuButton cancel) {
		this.stage = stage;
		this.table = table;
		this.remote = label;
		this.addresses = label;
		this.label = label;
		this.cancel = cancel;
		this.setDrawBehavior(new DrawBeige(stage));
	}

	@Override
	public void create() {
		super.create();

		/* Start hosting if not already doing so. */
		if (!networking.isInitialized() || !networking.isConnected()) {
			networking.startServer();
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
		update(new MultiGameScreen());

	}
	
	public void update(MultiGameScreen multiGameScreen) {

		if (networking.isServerSocketInitialized()) {
			if (networking.isConnected()) {
				label.setText(CONNECTION_ESTABLISHED);
				remote.setText(LETS_PLAY);

				/* Remote user is connected, show remote address. */
				String addr = networking.getRemoteAddress();
				addresses.setText(addr);

				ScreenHandler.getInstance().add(multiGameScreen);
			} else if (networking.errorOccured()) {
				String error = networking.getLastError();

				if (error.compareTo("") != 0) {
					label.setText(error);
				}
			}
		} else if (networking.errorOccured()) {
			
			String error = "Networking error!";
			
			if (!networking.getLastError().isEmpty())
				error = networking.getLastError();
			
			label.setText(error);
			addresses.setText(":(");
			remote.setText("Is the port in use?");
		} else {
			remote.setText(CONNECTION_WAITING);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
			
	}
	
	@Override
	public void dispose() {
		// dispose the stage:
		super.dispose();
		
		// remove screen object from networking observer list
		networking.deleteObserver(this);
	}
	
	/**
	 * Setter for networking. Used for testing.
	 * @param networking which we will mock.
	 */
	public void setNetworking(Networking networking) {
		HostScreen.networking = networking;
	}
}
