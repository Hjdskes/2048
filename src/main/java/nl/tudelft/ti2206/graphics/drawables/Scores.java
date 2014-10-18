package nl.tudelft.ti2206.graphics.drawables;

import java.util.Observable;
import java.util.Observer;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.handlers.PreferenceHandler;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * This class creates, positions and updates the labels and textures for
 * displaying scores. It extends Group so an instance of this class can be added
 * to the stage.
 */
public class Scores extends Group implements Observer {
	/** Coordinates and offsets used to position the labels. */
	private static final int LABEL_X = 100;
	private static final int LABEL_Y = 520;
	private static final int HEIGHT = 70;
	private static final int SCORE_WIDTH = 140;
	private static final int HIGHEST_WIDTH = 90;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();
	/** The singleton PreferenceHandler instance used to acces our saved game. */
	private PreferenceHandler prefsHandler = PreferenceHandler.getInstance();

	/** Labels to display scores. */
	private Label scoreLabel;
	private Label highScoreLabel;
	private Label highestTileLabel;

	/** Keeps track of the current high score. */
	private int highScore;

	/** The highest tile value saved. */
	private int highestTile;

	/**
	 * Creates a new ScoreDisplay object. Automatically creates all textures and
	 * labels and positions them.
	 */
	public Scores() {
		highestTile = (int) Math.pow(2, prefsHandler.getHighestTile());
		highScore = prefsHandler.getHighscore();
		initLabels();

		addLabelsToGroup();
		setLabelLocations();
	}

	/** Constructor for testing purposes only */
	public Scores(Label mockLabel) {
		this.scoreLabel = mockLabel;
		this.highScoreLabel = mockLabel;
		this.highestTileLabel = mockLabel;

		addLabelsToGroup();
		setLabelLocations();
		highestTile = (int) Math.pow(2, prefsHandler.getHighestTile());
	}

	/**
	 * Initializes all Labels for the scores. It creates the label and sets its
	 * style and location.
	 */
	private void initLabels() {
		scoreLabel = new Label(Integer.toString(prefsHandler.getScore()),
				assetHandler.getSkin(), "score");
		scoreLabel.setHeight(HEIGHT);
		scoreLabel.setWidth(SCORE_WIDTH);

		highScoreLabel = new Label(Integer.toString(highScore),
				assetHandler.getSkin(), "highscore");
		highScoreLabel.setHeight(HEIGHT);
		highScoreLabel.setWidth(SCORE_WIDTH);

		highestTileLabel = new Label(Integer.toString(highestTile),
				assetHandler.getSkin(), "highest");
		highestTileLabel.setHeight(HEIGHT);
		highestTileLabel.setWidth(HIGHEST_WIDTH);
	}

	/**
	 * Sets the label locations. This is called on acting to update the size
	 * when a score changes.
	 */
	private void setLabelLocations() {
		scoreLabel.setX(LABEL_X);
		scoreLabel.setY(LABEL_Y);
		scoreLabel.setAlignment(Align.bottom, Align.center);

		highScoreLabel.setX(LABEL_X + SCORE_WIDTH + TwentyFourtyGame.GAP);
		highScoreLabel.setY(LABEL_Y);
		highScoreLabel.setAlignment(Align.bottom, Align.center);

		highestTileLabel.setX(LABEL_X + 2 * SCORE_WIDTH + 2
				* TwentyFourtyGame.GAP);
		highestTileLabel.setY(LABEL_Y);
		highestTileLabel.setAlignment(Align.bottom, Align.center);
	}

	/** Adds the score labels to the group */
	private void addLabelsToGroup() {
		this.addActor(scoreLabel);
		this.addActor(highScoreLabel);
		this.addActor(highestTileLabel);
	}

	/**
	 * @return The x-coordinate of the score tile.
	 */
	@Override
	public float getX() {
		return LABEL_X;
	}

	/**
	 * @return The y-coordinate for all score tiles.
	 */
	@Override
	public float getY() {
		return LABEL_Y;
	}

	/**
	 * @return The current high score. Is not necessarily higher than the saved
	 *         high score. This is checked when saving the game.
	 */
	public int getHighScore() {
		return highScore;
	}

	/**
	 * @return The currently highest tile ever reached. This is not necessarily
	 *         from the current game.
	 */
	public int getHighestTile() {
		return highestTile;
	}

	/**
	 * Sets the current high score to the value provided, on the condition that
	 * it is actually higher than the previous highscore.
	 * 
	 * @param highScore
	 *            The new high score.
	 */
	public void setHighScore(int highScore) {
		if (this.highScore < highScore) {
			this.highScore = highScore;
		}
	}

	/**
	 * Sets the highest tile value to the value provided, on the condition that
	 * it is actually the highest value.
	 * 
	 * @param highestTile
	 *            The new highest tile value.
	 */
	public void setHighestTile(int highestTile) {
		if (this.highestTile < highestTile) {
			this.highestTile = highestTile;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Grid grid = (Grid) o;
		int currentHighest = (int) Math.pow(2, grid.getCurrentHighestTile());
		setHighestTile(currentHighest);
		setHighScore(grid.getScore());

		highestTileLabel.setText(Integer.toString(highestTile));
		highScoreLabel.setText(Integer.toString(highScore));
		scoreLabel.setText(Integer.toString(grid.getScore()));
	}
}
