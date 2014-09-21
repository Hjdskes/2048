package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.buttons.PlayButton;
import nl.tudelft.ti2206.gameobjects.StringConstants;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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

	/** The button to start the game when a connection has been made. */
	private PlayButton play;

	/** Constructs a new HostScreen. */
	public HostScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label(StringConstants.OPPONENT_DESTINY,
				AssetHandler.getSkin());

		remote = new Label(StringConstants.CONNECTION_WAITING,
				AssetHandler.getSkin());

		// show addresses to user to share with opponent:
		addresses = new Label(Networking.strAddresses(), AssetHandler.getSkin());
		cancel = new MenuButton();
		play = new PlayButton();
	}

	/** Constructor for injecting mock objects. For testing purposes only. */
	public HostScreen(Stage stage, Table table, Label label, PlayButton play,
			MenuButton cancel) {
		this.stage = stage;
		this.table = table;
		this.remote = label;
		this.addresses = label;
		this.label = label;
		this.cancel = cancel;
		this.play = play;
	}

	@Override
	public void create() {
		super.create();
		// start hosting if not already doing so:
		if (!Networking.isInitialized() || !Networking.isConnected())
			Networking.startServer();
		
		table.add(label);
		table.getCell(label).padTop(20).padBottom(10).row();
		table.add(addresses);
		table.getCell(addresses).padTop(10).padBottom(20).row();
		table.add(remote);
		table.getCell(remote).padTop(20).padBottom(50).row();

		table.setFillParent(true);
		stage.addActor(table);
		stage.addActor(cancel);
		stage.addActor(play);
		
		addPlayButtonListener();
		
		
	}
	
	/** Sets the listener for the playButton */
	private void addPlayButtonListener() {
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
	//			Networking.sendString("[START]\r\n");
				ScreenHandler.add(new MultiGameScreen());
			}
		});
	}

	@Override
	public void update() {
		super.update();

		if (Networking.isServerSocketInitialized()) {
			if (Networking.isConnected()) {
				// remote user is connected
				// get remote address:
				String addr = Networking.getRemoteAddress();

				label.setText(StringConstants.CONNECTION_ESTABLISHED);

				// show remote address to user:
				addresses.setText(addr);

				remote.setText(StringConstants.LETS_PLAY);

				// make play button visible:
				play.setVisible(true);
			}
			else
			{
				String error = Networking.getLastError();
				
				if (error.compareTo("") != 0)
					label.setText(error);
			}
		} else {
			remote.setText(StringConstants.CONNECTION_WAITING);
			play.setVisible(false);
		}
	}
}
