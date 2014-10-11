package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.drawables.DrawableGrid;
import nl.tudelft.ti2206.drawables.DrawableTile;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.state.GameState;
import nl.tudelft.ti2206.state.LostState;
import nl.tudelft.ti2206.state.WonState;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to restart the current game.
 */
public class RestartButton extends TextButton {

	/** Constructs a new RestartButton. */
	public RestartButton(final Grid grid, boolean ingame) {
		super("Restart", AssetHandler.getInstance().getSkin());
		if (ingame) {
			TextButtonStyle style = AssetHandler.getInstance().getSkin()
					.get("small", TextButtonStyle.class);
			this.setStyle(style);
			this.setHeight(50);
			this.setWidth(DrawableTile.TILE_WIDTH);
			this.setX(DrawableTile.TILE_X - DrawableTile.TILE_WIDTH / 2
					- TwentyFourtyGame.GAP / 2 + 2
					* (DrawableTile.TILE_WIDTH + TwentyFourtyGame.GAP));
		} else {
			this.setX(TwentyFourtyGame.GAME_WIDTH / 2 - this.getWidth() / 2);
		}
		this.setY(DrawableGrid.GRID_Y / 2 - this.getHeight() / 2);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameState state = TwentyFourtyGame.getState();
				grid.restart();
				if (state instanceof LostState || state instanceof WonState) {
					ScreenHandler.getInstance().restart();
				}
			}
		});
	}
}
