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

/**
 * The SettingsScreen contains several options that can be used
 * to tweak certain behavior to your likings.
 * 
 * A short summary: which log level to use, whether to enable logging
 * to a file, which solver to use, what delay the solvers should have
 * and what difficulty to spawn new tiles with.
 */
public class SettingsScreen extends Screen {
	/** The Singleton reference to the logger. */
	private static Logger logger = Logger.getInstance();

	/** The Singleton reference to the PreferenceHandler. */
	private static PreferenceHandler prefsHandler = PreferenceHandler
			.getInstance();

	/** The singleton reference to the AssetHandler. */
	private static AssetHandler assetHandler = AssetHandler.getInstance();

	/** The slider indicating which LogLevel to set. */
	private Slider levelSlider;
	/** The label indicating which LogLevel is currently set. */
	private Label levelLabel;
	/** The checkbox indicating if logging to file is enabled. */
	private CheckBox checkBox;

	/** The drop down menu containing all possible solvers. */
	private SelectBox<String> solverSelect;
	/** The label indicating what solver is currently selected. */
	private Label solverLabel;

	/** The slider indicating which delay to set. */
	private Slider delaySlider;
	/** The label indicating which delay is currently set. */
	private Label delayLabel;

	/** The slider indicating which difficulty to set. */
	private Slider difficultySlider;
	/** The label indicating which difficulty is currently set. */
	private Label difficultyLabel;

	/** The button to return to the menu. */
	private MenuButton menuButton;

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

	/** Initializes and positions the LevelSlider. */
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

	/** Positions the LevelLabel. */
	private void setupLevelLabel() {
		levelLabel.setX(100);
		levelLabel.setY(550);
	}

	/** Initializes and positions the CheckBox. */
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

	/** Initializes and positions the SolverSelect SelectBox. */
	private void setupSolverSelect() {
		Array<String> items = new Array<>();
		items.add(" HUMAN");
		items.add(" EXPECTIMAX");
		solverSelect.setItems(items);

		solverSelect.setSelected(" " + prefsHandler.getSolverStrategy().name());
		solverSelect.setWidth(220);
		solverSelect.setX(TwentyFourtyGame.GAME_WIDTH - 100
				- solverSelect.getWidth());
		solverSelect.setY(350);
	}

	/** Positions the SolverLabel. */
	private void setupSolverLabel() {
		solverLabel.setX(100);
		solverLabel.setY(360);
	}

	/** Initializes and positions the DelaySlider. */
	private void setupDelaySlider() {
		delaySlider.setValue(prefsHandler.getSolverDelay());
		delaySlider.setX(100);
		delaySlider.setY(240);
		delaySlider.setWidth(400);

		delaySlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				delayLabel.setText("Delay: " + delaySlider.getValue());
			}
		});
	}

	/** Positions the DelayLabel. */
	private void setupDelayLabel() {
		delayLabel.setX(100);
		delayLabel.setY(290);
	}

	/** Initializes and positions the DifficultySlider. */
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

	/** Positions the DifficultyLabel. */
	private void setupDifficultyLabel() {
		difficultyLabel.setX(100);
		difficultyLabel.setY(180);
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

	/**
	 * Sets the LogLevel to the value in the slider. 
	 * @return The text indicating the value in the slider.
	 */
	protected String updateLevel() {
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
		default:
			logger.setLevel(LogLevel.ALL);
			return "ALL";
		}
	}

	/** @return The text indicating the value in the slider. */
	protected String updateDifficulty() {
		switch ((int) difficultySlider.getValue()) {
		default:
		case 0:
			return "RANDOM";
		case 1:
			return "EASY";
		case 2:
			return "MEDIUM";
		case 3:
			return "HARD";
		}
	}

	/** @return The value of the slider, depending on the current LogLevel. */
	protected int getSliderValue() {
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
		default:
			return 400;
		}
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
