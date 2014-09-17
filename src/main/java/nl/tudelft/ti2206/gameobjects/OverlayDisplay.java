package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.Game;
import nl.tudelft.ti2206.game.Game.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * This class draws overlays if the game is lost or won.
 *
 */
public class OverlayDisplay extends Group {

	/** The textures of the overlays */
	private Texture wonOverlay;
	private Texture lostOverlay;

	/**
	 * Loads the textures of the overlays
	 * 
	 * @param grid
	 */
	public OverlayDisplay() {
		wonOverlay = AssetHandler.getSkin().get("wonoverlay", Texture.class);
		lostOverlay = AssetHandler.getSkin().get("lostoverlay", Texture.class);
	}

	/**
	 * Draws the overlays if the current game state requires it.
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (Game.getState() == GameState.WON)
			batch.draw(wonOverlay, 0, 0);
		else if (Game.getState() == GameState.LOST)
			batch.draw(lostOverlay, 0, 0);
	}

}
