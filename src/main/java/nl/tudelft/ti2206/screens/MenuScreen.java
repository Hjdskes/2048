package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The MenuScreen is used on start-up, to have the user choose between
 * singleplayer, hosting a game or connecting to another player.
 */
public class MenuScreen extends Screen {
	/** The table used for positioning all Actors. */
	private Table table;

	/** The main label. */
	private Label label;

	/** The button to launch a singleplayer game. */
	private TextButton singlePlayer;

	/** The button to go to the host menu. */
	private TextButton hostGame;

	/** The button to go to the client menu. */
	private TextButton connect;

	/** Constructs a new MenuScreen. */
	public MenuScreen() {
		stage = new Stage();
		table = new Table();
		label = new Label("Choose your destiny!", AssetHandler.getSkin());
		singlePlayer = new TextButton("Singleplayer", AssetHandler.getSkin());
		hostGame = new TextButton("Host a game", AssetHandler.getSkin());
		connect = new TextButton("Join a game", AssetHandler.getSkin());
	}

	/** Constuctor for testing purposes only. */
	public MenuScreen(Stage stage, Table table, Label label, TextButton button) {
		this.stage = stage;
		this.table = table;
		this.label = label;
		this.singlePlayer = button;
		this.hostGame = button;
		this.connect = button;
	}

	@Override
	public void create() {
		super.create();

		table.add(label);
		table.getCell(label).padBottom(60).row();
		table.add(singlePlayer);
		table.getCell(singlePlayer).padBottom(60).row();
		table.add(connect);
		table.getCell(connect).padBottom(20).row();
		table.add(hostGame);
		table.getCell(hostGame).padBottom(20).row();

		table.setFillParent(true);
		
		stage.addActor(table);

		singlePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new GameScreen());
			}
		});
		hostGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new HostScreen());
			}
		});
		connect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenHandler.add(new ClientScreen());
			}
		});
	}
}
