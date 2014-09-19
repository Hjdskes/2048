package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;

public class OverlayDisplay implements Drawable {

	private boolean isSinglePlayer;
	private GameWorld world;

	/**
	 * Creates a new OverlayDisplay object with the specified parameters.
	 * 
	 * @param world
	 *            The GameWorld containing the GameStates.
	 * @param isSinglePlayer
	 *            If true, overlays are drawn for the single player. Else, the
	 *            overlays are drawn for the opponent.
	 */
	public OverlayDisplay(GameWorld world, boolean isSinglePlayer) {
		this.world = world;
		this.isSinglePlayer = isSinglePlayer;
	}

	/**
	 * Draws the overlays if the current GameState requires so.
	 */
	@Override
	public void draw(Batch batch) {
		if (world.isLost()) {
			drawLostOverlay(batch);
		} else if (world.isWon()) {
			drawWonOverlay(batch);
		}
	}

	/**
	 * Draws the lost overlay iff the game is lost for a certain player.
	 * 
	 * @param batch
	 *            The Batch to draw with.
	 */
	private void drawLostOverlay(Batch batch) {
		if (isSinglePlayer) {
			batch.draw(AssetHandler.lost, 0, 0);
		} else {
			batch.draw(AssetHandler.lost, 500, 0);
		}
	}

	/**
	 * Draws the win overlay iff the game is won for a certain player.
	 * 
	 * @param batch
	 *            The batch to draw with.
	 */
	private void drawWonOverlay(Batch batch) {
		batch.draw(AssetHandler.lost, 0, 0);
		if (!isSinglePlayer) {
			batch.draw(AssetHandler.lost, 500, 0);
		}
	}
}
