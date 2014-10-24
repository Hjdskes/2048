package nl.tudelft.ti2206.graphics.screens.menuscreens;

import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.handlers.PreferenceHandler;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.log.Logger.LogLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class SettingsScreen extends Screen {

	/** The Singleton reference to the logger. */
	private static Logger logger = Logger.getInstance();

	/** The Singleton reference to the PreferenceHandler. */
	private static PreferenceHandler prefsHandler = PreferenceHandler
			.getInstance();

	/** The singleton reference to the AssetHandler. */
	private static AssetHandler assetHandler = AssetHandler.getInstance();

	private Slider levelSlider;
	private Label levelLabel;
	private CheckBox checkBox;
	private MenuButton menuButton;
	private SelectBox<String> solverSelect;
	private Label solverLabel;
	private Slider delaySlider;
	private Label delayLabel;

	/**
	 * Creates a new SettingsScreen, setting up and adding all required actors
	 * to the stage.
	 */
	public SettingsScreen() {
		stage = new Stage();
		setStageListeners();

		levelSlider = new Slider(0, 400, 100, false, assetHandler.getSkin());
		levelLabel = new Label("Log Level: " + updateLevel(),
				assetHandler.getSkin());
		setupLevelLabel();
		setupLevelSlider();

		checkBox = new CheckBox("    Enable logging to file",
				assetHandler.getSkin());
		setupCheckBox();

		menuButton = new MenuButton();

		solverSelect = new SelectBox<>(assetHandler.getSkin());
		solverLabel = new Label("Solver Type: ", assetHandler.getSkin());
		setupSolverSelect();
		setupSolverLabel();

		delaySlider = new Slider(25, 1000, 5, false, assetHandler.getSkin());
		setupDelaySlider();
		delayLabel = new Label("Delay: " + delaySlider.getValue(),
				assetHandler.getSkin());
		setupDelayLabel();
	
		addActors();
		setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public SettingsScreen(Stage stage, MenuButton button, Slider slider,
			Label label, CheckBox checkBox, SelectBox<String> select) {
		this.stage = stage;
		this.levelSlider = slider;
		this.levelLabel = label;
		this.checkBox = checkBox;
		this.menuButton = button;
		this.delaySlider = slider;
		this.delayLabel = label;
		this.solverSelect = select;
		this.solverLabel = label;
		setupLevelLabel();
		setupLevelSlider();
		setupCheckBox();
		setupSolverLabel();
		setupSolverSelect();
		setupDelayLabel();
		setupDelaySlider();
		setStageListeners();
		addActors();
	}

	private void setupLevelSlider() {
		levelSlider.setValue(getSliderValue());
		levelSlider.setX(100);
		levelSlider.setY(460);
		levelSlider.setWidth(400);
		
		levelSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				levelLabel.setText("Log Level: " + updateLevel());
			}
		});
	}

	private void setupLevelLabel() {
		levelLabel.setX(100);
		levelLabel.setY(520);
	}

	private void setupCheckBox() {
		checkBox.setChecked(prefsHandler.isLogFileEnabled());
		checkBox.setX(100);
		checkBox.setY(370);
		
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
	}

	private void setupSolverSelect() {
		Array<String> items = new Array<>();
		items.add(" HUMAN");
		items.add(" EXPECTIMAX");
		solverSelect.setItems(items);

		solverSelect.setSelected(" " + prefsHandler.getSolverStrategy().name());
		solverSelect.setX(100);
		solverSelect.setY(250);
		solverSelect.setWidth(200);

		solverSelect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				delayLabel.setVisible(false);
			}
		});
		
		solverSelect.getList().addListener(new ClickListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				delayLabel.setVisible(true);
			}
		});

	}

	private void setupSolverLabel() {
		solverLabel.setX(100);
		solverLabel.setY(310);
	}

	private void setupDelaySlider() {
		delaySlider.setValue(prefsHandler.getSolverDelay());
		delaySlider.setX(100);
		delaySlider.setY(130);
		delaySlider.setWidth(400);
		
		delaySlider.addListener(new ChangeListener() {		
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				delayLabel.setText("Delay: " + delaySlider.getValue());
			}
		});
	}

	private void setupDelayLabel() {
		delayLabel.setX(100);
		delayLabel.setY(190);
	}

	/** Sets the listeners of the actors belonging to this screen. */
	private void setStageListeners() {
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
		stage.addActor(levelSlider);
		stage.addActor(levelLabel);
		stage.addActor(checkBox);
		stage.addActor(menuButton);
		stage.addActor(solverSelect);
		stage.addActor(solverLabel);
		stage.addActor(delaySlider);
		stage.addActor(delayLabel);
	}

	/** Updates the loglevel and returns the loglevel string. */
	public String updateLevel() {
		switch ((int) levelSlider.getValue()) {
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

	/** @return The value of the slider, depending on the current loglevel. */
	public int getSliderValue() {
		switch (logger.getLevel()) {
		case NONE:
			return 0;
		case ERROR:
			return 100;
		case DEBUG:
			return 200;
		case INFO:
			return 300;
		case ALL:
			return 400;
		}
		return 400;
	}

	@Override
	public void dispose() {
		super.dispose();
		prefsHandler.setLogLevel(logger.getLevel().name());
		prefsHandler.setLogFileEnabled(checkBox.isChecked());
		prefsHandler.setSolver(solverSelect.getSelected().substring(1));
		prefsHandler.setSolverDelay((int) delaySlider.getValue());
	}
}
