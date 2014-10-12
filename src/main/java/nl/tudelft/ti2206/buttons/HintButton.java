package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.drawables.DrawableGrid;
import nl.tudelft.ti2206.drawables.DrawableTile;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.solver.Solver;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to call a solver and make it perform one move.
 */
public class HintButton extends TextButton {
	private Solver solver;

	/** Constructs a new HintButton. */
	public HintButton(final Grid grid) {
		super("Hint", AssetHandler.getInstance().getSkin(), "small");
		this.setHeight(50);
		this.setWidth(DrawableTile.TILE_WIDTH);
		this.setX(DrawableTile.TILE_X - DrawableTile.TILE_WIDTH / 2 - TwentyFourtyGame.GAP / 2 + DrawableTile.TILE_WIDTH + TwentyFourtyGame.GAP);
		this.setY(DrawableGrid.GRID_Y / 2 - this.getHeight() / 2);
		solver = new Solver(grid, 7);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Direction direction = solver.findMove(grid, 7);
				if (direction != null)
					grid.move(direction);
				else
					Logger.getInstance().error(this.getClass().getSimpleName(), "direction == null (ignored");
			}
		});
	}
}
