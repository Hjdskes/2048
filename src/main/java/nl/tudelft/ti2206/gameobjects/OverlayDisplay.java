package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.Game;
import nl.tudelft.ti2206.game.Game.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * This class creates, positions and draws the overlays if necessary.
 * 
 * It extends Group so an instance of this class can be
 * added to the stage.
 */
public class OverlayDisplay extends Group {
	/** The textures of the overlays. */
	private Texture wonOverlay;
	private Texture lostOverlay;
	
	private Skin skin;

	/**
	 * Loads the textures of the overlays.
	 */
	public OverlayDisplay() {
		skin = AssetHandler.getSkin();
		wonOverlay = skin.get("wonoverlay", Texture.class);
		lostOverlay = skin.get("lostoverlay", Texture.class);
	}
	
//	public OverlayDisplay(Skin skin) {
//		this.skin = skin;
//		wonOverlay = skin.get("wonoverlay", Texture.class);
//		lostOverlay = skin.get("lostoverlay", Texture.class);
//	}

	/**
	 * Draws the overlays if the current game state requires it.
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (Game.getState() == GameState.WON) {
			batch.draw(wonOverlay, 0, 0);
		} else if (Game.getState() == GameState.LOST) {
			batch.draw(lostOverlay, 0, 0);
		}
	}
}
