package nl.tudelft.ti2206.graphics.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.drawables.DrawableGrid;
import nl.tudelft.ti2206.graphics.drawables.DrawableTile;
import nl.tudelft.ti2206.utils.ai.solvers.GridSolver;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to call a solver and make it perform one move. Extends the
 * TextButton class from the GDX library.
 */
public class HintButton extends TextButton {
	/** The singleton reference to the GridSolver instance. */
	private static GridSolver solver = GridSolver.getInstance();

	/** Constructs a new HintButton. */
	public HintButton(final Grid grid) {
		super("Hint", AssetHandler.getInstance().getSkin(), "small");
		this.setHeight(50);
		this.setWidth(DrawableTile.TILE_WIDTH);
		this.setX(DrawableTile.TILE_X - DrawableTile.TILE_WIDTH / 2
				- TwentyFourtyGame.GAP / 2 + DrawableTile.TILE_WIDTH
				+ TwentyFourtyGame.GAP);
		this.setY(DrawableGrid.GRID_Y / 2 - this.getHeight() / 2);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!solver.isRunning()) {
					solver.setGrid(grid);
					solver.run();
				}
			}
		});
	}
}
