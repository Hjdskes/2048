package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.drawables.DrawableGrid;
import nl.tudelft.ti2206.drawables.DrawableTile;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.Command;
import nl.tudelft.ti2206.handlers.UndoCommand;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button that calls the execute method of the UndoCommand class when pressed.
 * Extends the TextButton class from the GDX library.
 */
public class UndoButton extends TextButton {
	/** Constructs a new UndoButton. */
	public UndoButton(final Grid grid) {
		super("Undo", AssetHandler.getInstance().getSkin(), "small");
		this.setHeight(50);
		this.setWidth(DrawableTile.TILE_WIDTH);
		this.setX(DrawableTile.TILE_X - DrawableTile.TILE_WIDTH / 2
				- TwentyFourtyGame.GAP / 2 + 3
				* (DrawableTile.TILE_WIDTH + TwentyFourtyGame.GAP));
		this.setY(DrawableGrid.GRID_Y / 2 - this.getHeight() / 2);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Command command = new UndoCommand(grid);
				command.execute();
			}
		});
	}
}