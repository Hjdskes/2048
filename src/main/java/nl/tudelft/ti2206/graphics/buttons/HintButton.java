package nl.tudelft.ti2206.graphics.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.graphics.drawables.DrawableGrid;
import nl.tudelft.ti2206.graphics.drawables.DrawableTile;
import nl.tudelft.ti2206.utils.ai.solvers.Expectimax;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.log.Logger;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to call a solver and make it perform one move. Extends the
 * TextButton class from the GDX library.
 */
public class HintButton extends TextButton {
	private Expectimax solver;

	/** Constructs a new HintButton. */
	public HintButton(final Grid grid) {
		super("Hint", AssetHandler.getInstance().getSkin(), "small");
		this.setHeight(50);
		this.setWidth(DrawableTile.TILE_WIDTH);
		this.setX(DrawableTile.TILE_X - DrawableTile.TILE_WIDTH / 2
				- TwentyFourtyGame.GAP / 2 + DrawableTile.TILE_WIDTH
				+ TwentyFourtyGame.GAP);
		this.setY(DrawableGrid.GRID_Y / 2 - this.getHeight() / 2);
		solver = new Expectimax();

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Direction direction = solver.findMove(grid, 0); // Depth is ignored by Expectimax.
				if (direction != null) {
					grid.move(direction);
				} else {
					Logger.getInstance().error(this.getClass().getSimpleName(),
							"direction == null (ignored");
				}
			}
		});
	}
}
