package nl.tudelft.ti2206.graphics.screens.menuscreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
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
	private Slider difficultySlider;
	private Label difficultyLabel;
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
		setupLevelSlider();
		levelLabel = new Label("Log Level: " + updateLevel(),
				assetHandler.getSkin());
		setupLevelLabel();

		checkBox = new CheckBox("    Enable logging to file",
				assetHandler.getSkin());
		setupCheckBox();

		menuButton = new MenuButton();

		solverSelect = new SelectBox<>(assetHandler.getSkin());
		solverLabel = new Label("Solver Type: ", assetHandler.getSkin());
		setupSolverLabel();
		setupSolverSelect();

		delaySlider = new Slider(25, 1000, 5, false, assetHandler.getSkin());
		setupDelaySlider();
		delayLabel = new Label("Delay: " + delaySlider.getValue(),
				assetHandler.getSkin());
		setupDelayLabel();

		difficultySlider = new Slider(0, 3, 1, false, assetHandler.getSkin());
		setupDifficultySlider();
		difficultyLabel = new Label("Difficulty: " + updateDifficulty(),
				assetHandler.getSkin());
		setupDifficultyLabel();


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
		this.difficultyLabel = label;
		this.difficultySlider = slider;
		setupLevelLabel();
		setupLevelSlider();
		setupCheckBox();
		setupSolverLabel();
		setupSolverSelect();
		setupDelayLabel();
		setupDelaySlider();
		setupDifficultyLabel();
		setupDifficultySlider();
		setStageListeners();
		addActors();
	}

	private void setupLevelSlider() {
		levelSlider.setValue(getSliderValue());
		levelSlider.setX(100);
		levelSlider.setY(500);
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
		levelLabel.setY(550);
	}

	private void setupCheckBox() {
		checkBox.setChecked(prefsHandler.isLogFileEnabled());
		checkBox.setX(100);
		checkBox.setY(420);
		
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
		solverSelect.setWidth(220);
		solverSelect.setX(TwentyFourtyGame.GAME_WIDTH - 100 - solverSelect.getWidth());
		solverSelect.setY(350);
	}

	private void setupSolverLabel() {
		solverLabel.setX(100);
		solverLabel.setY(360);
	}

	private void setupDifficultySlider() {
		difficultySlider.setValue(prefsHandler.getDifficulty().ordinal());
		difficultySlider.setX(100);
		difficultySlider.setY(130);
		difficultySlider.setWidth(400);
		
		difficultySlider.addListener(new ChangeListener() {		
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				difficultyLabel.setText("Difficulty: " + updateDifficulty());
			}
		});
	}

	private void setupDifficultyLabel() {
		difficultyLabel.setX(100);
		difficultyLabel.setY(180);
	}

	private void setupDelaySlider() {
		delaySlider.setValue(prefsHandler.getSolverDelay());
		delaySlider.setX(100);
		delaySlider.setY(180);
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
		delayLabel.setY(290);
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
		stage.addActor(difficultyLabel);
		stage.addActor(difficultySlider);
	}

	/** Updates the loglevel and returns the loglevel string. */
	private String updateLevel() {
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

	/** @return The text indicating the value in the slider. */
	private String updateDifficulty() {
		switch ((int) difficultySlider.getValue()) {
		case 0:
			return "RANDOM";
		case 1:
			return "EASY";
		case 2:
			return "MEDIUM";
		case 3:
			return "HARD";
		}
		return null;
	}

	/** @return The value of the slider, depending on the current loglevel. */
	private int getSliderValue() {
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
		prefsHandler.setDifficulty(updateDifficulty());
	}
}
