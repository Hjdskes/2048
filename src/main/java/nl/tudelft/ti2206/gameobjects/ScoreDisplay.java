package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.PreferenceHandler;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * This class creates, positions and updates the labels and textures for
 * displaying scores. It extends Group so an instance of this class can be added
 * to the stage.
 */
public class ScoreDisplay extends Group {

	/** Coordinates and offsets used to position the labels. */
	private static final int LABEL_X = 100;
	private static final int LABEL_Y = 520;
	private static final int SCORE_WIDTH = 140;
	private static final int HIGHEST_WIDTH = 90;
	private static final int HEIGHT = 70;

	/** The grid holding the tiles. */
	private Grid grid;

	/** The highest tile value saved. */
	private int allTimeHighestValue;

	/** Labels to display scores. */
	private Label scoreLabel;
	private Label highScoreLabel;
	private Label highestTileLabel;

	/** The singleton AssetHandler instance used to access our assets. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/**
	 * Creates a new ScoreDisplay object. Automatically creates all textures and
	 * labels and positions them.
	 * 
	 * @param grid
	 *            A reference to the Grid.
	 */
	public ScoreDisplay(Grid grid) {
		this.grid = grid;

		initLabels();
		setLabelLocations();
		addLabelsToGroup();
		allTimeHighestValue = PreferenceHandler.getInstance().getHighestTile();
	}

	/** Constructor for testing purposes only */
	public ScoreDisplay(Grid mockGrid, Label mockLabel) {
		this.grid = mockGrid;
		this.scoreLabel = mockLabel;
		this.highScoreLabel = mockLabel;
		this.highestTileLabel = mockLabel;

		addLabelsToGroup();
		setLabelLocations();
		allTimeHighestValue = PreferenceHandler.getInstance().getHighestTile();
	}

	/**
	 * Initializes all Labels for the scores. It creates the label and sets its
	 * style and location. Furthermore, the act method is declared to be able to
	 * update the scores.
	 */
	private void initLabels() {
		scoreLabel = new Label("0", assetHandler.getSkin(), "score") {
			@Override
			public void act(float delta) {
				scoreLabel.setText(Integer.toString(grid.getScore()));
			}
		};
		scoreLabel.setHeight(HEIGHT);
		scoreLabel.setWidth(SCORE_WIDTH);

		highScoreLabel = new Label("0", assetHandler.getSkin(), "highscore") {
			@Override
			public void act(float delta) {
				highScoreLabel.setText(Integer.toString(grid.getHighscore()));
			}
		};
		highScoreLabel.setHeight(HEIGHT);
		highScoreLabel.setWidth(SCORE_WIDTH);

		highestTileLabel = new Label(Integer.toString(allTimeHighestValue),
				assetHandler.getSkin(), "highest") {
			@Override
			public void act(float delta) {
				int currentHighest = grid.getCurrentHighestTile();
				String value = Integer
						.toString(allTimeHighestValue > currentHighest ? allTimeHighestValue
								: currentHighest);
				highestTileLabel.setText(value);
			}
		};
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
}
