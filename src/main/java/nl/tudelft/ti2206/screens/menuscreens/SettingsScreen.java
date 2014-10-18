package nl.tudelft.ti2206.screens.menuscreens;

import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.log.Logger.LogLevel;
import nl.tudelft.ti2206.screens.Screen;
import nl.tudelft.ti2206.screens.drawbehaviour.DrawBeige;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingsScreen extends Screen {

	/** The Singleton reference to the logger. */
	private static Logger logger = Logger.getInstance();

	private Slider slider;
	private Label levelLabel;
	private CheckBox checkBox;
	private MenuButton menuButton;

	/**
	 * Creates a new SettingsScreen, setting up and adding all required actors
	 * to the stage.
	 */
	public SettingsScreen() {
		stage = new Stage();
		slider = new Slider(0, 400, 100, false, AssetHandler.getInstance()
				.getSkin());

		levelLabel = new Label("Log Level: " + updateLevel(), AssetHandler
				.getInstance().getSkin());
		checkBox = new CheckBox("    Enable logging to file", AssetHandler
				.getInstance().getSkin());
		menuButton = new MenuButton();

		setupSlider();
		setupLevelLabel();
		setupCheckBox();
		setListeners();
		addActors();
		
		this.setDrawBehavior(new DrawBeige(stage));
	}
	
	/** Constructor for testing purposes only. */
	public SettingsScreen(MenuButton button, Slider slider, Label label, CheckBox checkBox) {
		this.slider =  slider;
		this.levelLabel = label;
		this.checkBox = checkBox;
		this.menuButton = button;
		setupMenuButton();
		setupLevelLabel();
		setupCheckBox();
		setListeners();
		addActors();
	}

	private void setupMenuButton() {
		menuButton.setX(10);
		menuButton.setY(10);
	}
	
	private void setupSlider() {
		slider.setX(100);
		slider.setY(350);
		slider.setWidth(400);
	}

	private void setupLevelLabel() {
		levelLabel.setX(TwentyFourtyGame.GAME_WIDTH / 2 - levelLabel.getWidth()
				/ 2);
		levelLabel.setY(400);
	}

	private void setupCheckBox() {
		checkBox.setX(100);
		checkBox.setY(200);
	}

	/** Sets the listeners of the actors belonging to this screen. */
	private void setListeners() {
		slider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				levelLabel.setText("Log Level: " + updateLevel());
			}
		});

		checkBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (logger.getFile() == null) {
					logger.setLogFile("2048");
				} else {
					logger.setLogFile(null);
				}
			}
		});
		
		// Return to main menu on escape
		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Keys.ESCAPE) {
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							ScreenHandler.getInstance().set(new MenuScreen());
						}
					});
					return true;
				}
				return false;
			}
		});
	}

	/** Adds the actors to the stage. */
	private void addActors() {
		stage.addActor(slider);
		stage.addActor(levelLabel);
		stage.addActor(checkBox);
		stage.addActor(menuButton);
	}

	/** Updates the loglevel and returns the loglevel string. */
	private String updateLevel() {
		switch ((int) slider.getValue()) {
		case 0:
			logger.setLevel(LogLevel.NONE);
			return "NONE";
		case 100:
			logger.setLevel(LogLevel.ERROR);
			return "ERROR";
		case 200:
			logger.setLevel(LogLevel.DEBUG);
			return "DEBUG";
		case 300:
			logger.setLevel(LogLevel.INFO);
			return "INFO";
		case 400:
			logger.setLevel(LogLevel.ALL);
			return "ALL";
		}
		return null;
	}
}
