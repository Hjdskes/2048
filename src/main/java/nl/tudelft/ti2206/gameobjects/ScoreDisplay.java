package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * This class creates, positions and updates the labels and textures for
 * displaying scores. It extends Group so an instance of this class can be added
 * to the stage.
 */
public class ScoreDisplay extends Group {

	/** Coordinates and offsets used to position the score tiles and labels. */
	private static final int BASE_X = 100;
	private static final int LABEL_Y = 510;
	private static final int SCORE_TILE_WIDTH = 140;
	private static final int GAP = 15;
	private static final int GRID_TOP = 500;

	/** The grid holding the tiles. */
	private Grid grid;

	/** The group holding all actors. */
	private Group group;

	/** Labels to display scores. */
	private Label scoreLabel;
	private Label highScoreLabel;
	private Label highestTileLabel;

	/** Textures to display score tiles. */
	private TextureRegion scoreRegion;
	private TextureRegion highScoreRegion;
	private TextureRegion highestTileRegion;

	/**
	 * Creates a new ScoreDisplay object. Automatically creates all textures and
	 * labels and positions them.
	 * 
	 * @param grid
	 *            A reference to the Grid.
	 */
	public ScoreDisplay(Grid grid) {
		this.grid = grid;
		group = new Group();

		initRegions();
		initLabels();

		group.addActor(scoreLabel);
		group.addActor(highScoreLabel);
		group.addActor(highestTileLabel);
	}

	/**
	 * Initializes all TextureRegions for the scores.
	 */
	private void initRegions() {
		scoreRegion = AssetHandler.getSkin().getRegion("score");
		highScoreRegion = AssetHandler.getSkin().getRegion("highscore");
		highestTileRegion = AssetHandler.getSkin().getRegion("highest");
	}

	/**
	 * Initializes all Labels for the scores. It creates the label and sets its
	 * style and location. Furthermore, the act method is declared to be able to
	 * update the scores.
	 */
	private void initLabels() {
		scoreLabel = new Label("0", AssetHandler.getSkin().get("white-text",
				LabelStyle.class)) {
			@Override
			public void act(float delta) {
				scoreLabel.setText(Integer.toString(grid.getScore()));
			}
		};

		highScoreLabel = new Label("0", AssetHandler.getSkin().get("white-text",
				LabelStyle.class)) {
			@Override
			public void act(float delta) {
				highScoreLabel.setText(Integer.toString(grid.getHighscore()));
			}
		};

		highestTileLabel = new Label("0", AssetHandler.getSkin().get("white-text",
				LabelStyle.class)) {
			@Override
			public void act(float delta) {
				highestTileLabel.setText(Integer.toString(grid
						.getCurrentHighestTile()));
			}
		};

		setLabelStyles();
		setLabelLocations();
	}

	/**
	 * Sets the label styles and scale.
	 */
	private void setLabelStyles() {
		AssetHandler.getSkin().get("white-text", Label.LabelStyle.class).font
				.setScale(.65f);
		scoreLabel.setStyle(AssetHandler.getSkin().get("white-text",
				Label.LabelStyle.class));
		highScoreLabel.setStyle(AssetHandler.getSkin().get("white-text",
				Label.LabelStyle.class));
		highestTileLabel.setStyle(AssetHandler.getSkin().get("white-text",
				Label.LabelStyle.class));
	}

	/**
	 * Sets the label locations. This is called on acting to update the size
	 * when a score changes.
	 */
	private void setLabelLocations() {
		scoreLabel.setX(BASE_X + scoreRegion.getRegionWidth() / 2
				- scoreLabel.getWidth() / 2);
		scoreLabel.setY(LABEL_Y);

		highScoreLabel.setX(BASE_X + SCORE_TILE_WIDTH + GAP
				+ highScoreRegion.getRegionWidth() / 2
				- highScoreLabel.getWidth() / 2);
		highScoreLabel.setY(LABEL_Y);

		highestTileLabel.setX(BASE_X + 2 * SCORE_TILE_WIDTH + 2 * GAP
				+ highestTileRegion.getRegionWidth() / 2
				- scoreLabel.getWidth() / 2);
		highestTileLabel.setY(LABEL_Y);
	}

	/**
	 * Updates the scores and locations.
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		scoreLabel.act(delta);
		highScoreLabel.act(delta);
		highestTileLabel.act(delta);
		setLabelLocations();
	}

	/**
	 * @return The x-coordinate of the score tile.
	 */
	private int getScoreX() {
		return BASE_X;
	}

	/**
	 * @return The x-coordinate of the high score tile.
	 */
	private int getHighScoreX() {
		return BASE_X + GAP + SCORE_TILE_WIDTH;
	}

	/**
	 * 
	 * @return The x-coordinate for the tile displaying the highest value.
	 */
	private int getHighestTileX() {
		return BASE_X + 2 * GAP + 2 * SCORE_TILE_WIDTH;
	}

	/**
	 * 
	 * @return The y-coordinate for all score tiles.
	 */
	private int getScoreY() {
		return GRID_TOP + GAP;
	}

	/**
	 * Draws the Group and Textures. Drawing order is important.
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(scoreRegion, getScoreX(), getScoreY());
		batch.draw(highScoreRegion, getHighScoreX(), getScoreY());
		batch.draw(highestTileRegion, getHighestTileX(), getScoreY());
		group.draw(batch, parentAlpha);
	}
}
