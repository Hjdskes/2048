package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.drawables.DrawableTile;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.solver.GridSolver;
import nl.tudelft.ti2206.solver.GridSolver.Strategy;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to call a solver to solve the game.
 */
public class SolveButton extends TextButton {
	private GridSolver gridSolver;

	/** Constructs a new SolveButton. */
	public SolveButton(final Grid grid) {
		super("Solve", AssetHandler.getInstance().getSkin(), "small");
		this.setHeight(50);
		this.setWidth(DrawableTile.TILE_WIDTH);
		this.setX(DrawableTile.TILE_X - DrawableTile.TILE_WIDTH / 2
				- TwentyFourtyGame.GAP / 2);
		this.setY(100 / 2 - this.getHeight() / 2);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				// if we don't make a new GridSolver object, we won't be able to
				// reschedule the task and we'll get
				// java.lang.IllegalStateException: Task already scheduled or
				// cancelled
				// doing the following solves this problem: create a new GridSolver
				
				// so first check if a GridSolver object exists, check if it's running
				// and stop it if it is

				boolean wasRunning = false;

				if (gridSolver != null) {
					if (gridSolver.isRunning()) {
						gridSolver.stop();
						wasRunning = true;
					}
					gridSolver = null;
				}
				
				// in case it wasn't previously running, create a new GridSolver instance
				// and simply start it

				if (!wasRunning) {
					// setup GridSolver with HUMAN strategy to make one move
					// every 650 milliseconds
					// 650 ms should be enough to allow the user to see what it's
					// doing but not completely bore the user to death
					gridSolver = new GridSolver(grid, Strategy.HUMAN, 650, 6);
					gridSolver.start();
				}
			}
		});
	}
}
