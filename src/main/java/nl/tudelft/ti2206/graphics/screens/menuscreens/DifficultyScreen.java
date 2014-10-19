package nl.tudelft.ti2206.graphics.screens.menuscreens;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawSimple;
import nl.tudelft.ti2206.graphics.screens.gamescreens.UserComputerScreen.Difficulty;
import nl.tudelft.ti2206.graphics.screens.overlays.CountDownScreen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The DifficultyScreen is used to select a difficulty for the computer to play
 * against.
 */
public class DifficultyScreen extends Screen {
	/** The main label. */
	private Label difficultyLabel;

	/** The buttons to choose the difficulty. */
	private TextButton easyButton, mediumButton, hardButton, extremeButton;
	/** The button to go back to the main menu. */
	private MenuButton menuButton;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();
	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new DifficultyScreen. */
	public DifficultyScreen() {
		stage = new Stage();
		difficultyLabel = new Label("Select your difficulty!",
				assetHandler.getSkin());

		easyButton = new TextButton("Easy", assetHandler.getSkin());
		mediumButton = new TextButton("Medium", assetHandler.getSkin());
		hardButton = new TextButton("Hard", assetHandler.getSkin());
		extremeButton = new TextButton("Extreme", assetHandler.getSkin());
		menuButton = new MenuButton();

		this.setDrawBehavior(new DrawBeige(stage));
	}

	/** Constructor for testing purposes only. */
	public DifficultyScreen(Stage stage, Label label, MenuButton menuButton,
			TextButton button) {
		this.stage = stage;
		this.difficultyLabel = label;

		this.easyButton = button;
		this.mediumButton = button;
		this.hardButton = button;
		this.extremeButton = button;
		this.menuButton = menuButton;

		this.setDrawBehavior(new DrawSimple(stage));
	}
	
	@Override
	public void create() {
		super.create();

		positionDifficultyLabel();
		positionEasyButton();
		positionMediumButton();
		positionHardButton();
		positionExtremeButton();

		setListeners();
		addActors();
	}
	
	/** Positions the difficulty label. */
	private void positionDifficultyLabel() {
		difficultyLabel.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- difficultyLabel.getWidth() / 2);
		difficultyLabel.setY(TwentyFourtyGame.GAME_HEIGHT / 2
				+ difficultyLabel.getHeight() + 10 * TwentyFourtyGame.GAP);
	}
	
	/** Positions the easy difficulty button. */
	private void positionEasyButton() {
		easyButton.setWidth(200);
		easyButton.setX(TwentyFourtyGame.GAME_WIDTH / 2 - easyButton.getWidth()
				/ 2);
		easyButton.setY(difficultyLabel.getY() - difficultyLabel.getHeight()
				- 6 * TwentyFourtyGame.GAP);
	}
	
	/** Positions the medium difficulty button. */
	private void positionMediumButton() {
		mediumButton.setWidth(200);
		mediumButton.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- mediumButton.getWidth() / 2);
		mediumButton.setY(easyButton.getY() - easyButton.getHeight() - 2
				* TwentyFourtyGame.GAP);
	}
	
	/** Positions the hard difficulty button. */
	private void positionHardButton() {
		hardButton.setWidth(200);
		hardButton.setX(TwentyFourtyGame.GAME_WIDTH / 2 - hardButton.getWidth()
				/ 2);
		hardButton.setY(mediumButton.getY() - easyButton.getHeight() - 2
				* TwentyFourtyGame.GAP);
	}
	
	/** Positions the extreme difficulty button. */
	private void positionExtremeButton() {
		extremeButton.setWidth(200);
		extremeButton.setX(TwentyFourtyGame.GAME_WIDTH / 2
				- extremeButton.getWidth() / 2);
		extremeButton.setY(hardButton.getY() - mediumButton.getHeight() - 2
				* TwentyFourtyGame.GAP);
	}
	
	/** Sets the listeners of all buttons. */
	private void setListeners() {
		easyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new CountDownScreen(Difficulty.EASY));
			}
		});

		mediumButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new CountDownScreen(Difficulty.MEDIUM));
			}
		});

		hardButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new CountDownScreen(Difficulty.HARD));
			}
		});

		extremeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.set(new CountDownScreen(Difficulty.EXTREME));
			}
		});
	}
	
	/** Adds all required actors to stage. */
	private void addActors() {
		stage.addActor(difficultyLabel);
		stage.addActor(easyButton);
		stage.addActor(mediumButton);
		stage.addActor(hardButton);
		stage.addActor(extremeButton);
		stage.addActor(menuButton);
	}
}
