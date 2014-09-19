package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;

public class ScoreDisplay implements Drawable {

	/** The width of a gap, which is between all the tiles. */
	private static final int GAP = 15;

	/** The base x coordinate */
	private static final int BASE_X = 100;

	/** The gap between the top of the game window and the score text. */
	private static final int SCORE_Y = 50;

	/** The height of the score displays */
	private static final int SCORE_TILE_HEIGHT = 70;

	/** The gap between the players' grids */
	private static final int MULTIPLAYER_GAP = 500;

	private GameWorld world;
	private int scoreX, scoreWidth;
	private int highScoreX, highScoreWidth;
	private int highestTileX, highestTileWidth;
	private boolean isSinglePlayer;

	/**
	 * Creates a ScoreDisplay object with the given parameters.
	 * 
	 * @param world
	 *            The GameWorld containing the scores to display.
	 * @param isSinglePlayer
	 *            If true, the displays will be drawn twice on different places.
	 */
	public ScoreDisplay(GameWorld world, boolean isSinglePlayer) {
		this.world = world;
		this.isSinglePlayer = isSinglePlayer;

		setScoreWidths();
		setXCoords(isSinglePlayer);
	}

	/**
	 * Initializes the score width and x locations, depending on whether it is a
	 * single player game or not.
	 */
	private void setScoreWidths() {
		scoreWidth = (int) AssetHandler.score.getWidth();
		highScoreWidth = (int) AssetHandler.highscore.getWidth();
		highestTileWidth = (int) AssetHandler.highest.getWidth();
	}

	private void setXCoords(boolean isSinglePlayerX) {
		if (isSinglePlayerX) {
			scoreX = BASE_X;
		} else {
			scoreX = BASE_X + MULTIPLAYER_GAP;
		}

		highScoreX = scoreX + scoreWidth + GAP;
		highestTileX = highScoreX + highScoreWidth + GAP;
	}

	/**
	 * Calculates the center x-coordinate of a String.
	 * 
	 * @param text
	 *            The text to be centered.
	 * @return The center x-coordinate of the provided String.
	 */
	private float getTextCenterX(String text) {
		return AssetHandler.font.getBounds(text).width / 2;
	}

	/**
	 * Draw the score displays and their actual values at their designated
	 * positions.
	 */
	@Override
	public void draw(Batch batch) {
		drawForPlayer(batch);
		drawMultiPlayer(batch);
	}

	/**
	 * Draw the score display for a player.
	 * 
	 * @param batch
	 *            The batch to draw with.
	 */
	private void drawForPlayer(Batch batch) {
		// draw sprites
		batch.draw(AssetHandler.score, scoreX, GAP, scoreWidth,
				SCORE_TILE_HEIGHT);
		batch.draw(AssetHandler.highscore, highScoreX, GAP, highScoreWidth,
				SCORE_TILE_HEIGHT);
		batch.draw(AssetHandler.highest, highestTileX, GAP, highestTileWidth,
				SCORE_TILE_HEIGHT);

		// draw scores
		String score = Integer.toString(world.getScore());
		AssetHandler.font.draw(batch, score, scoreX + scoreWidth / 2
				- getTextCenterX(score), SCORE_Y);
		String highScore = Integer.toString(world.getHighscore());
		AssetHandler.font.draw(batch, highScore, highScoreX + highScoreWidth
				/ 2 - getTextCenterX(highScore), SCORE_Y);
		String highestTile = Integer.toString(world.getHighestTile());
		AssetHandler.font.draw(batch, highestTile, highestTileX
				+ highestTileWidth / 2 - getTextCenterX(highestTile), SCORE_Y);
	}

	/**
	 * Draws the multiplayer score displays iff isSinglePlayer == false.
	 * 
	 * @param batch
	 */
	private void drawMultiPlayer(Batch batch) {
		if (!isSinglePlayer) {
			// update coordinates for multiplayer.
			setXCoords(true);
			drawForPlayer(batch);
		}
	}
}
