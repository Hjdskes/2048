package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.drawables.DrawableTile;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.Command;
import nl.tudelft.ti2206.handlers.RedoCommand;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to Redo a move.
 */
public class RedoButton extends TextButton {
	/** Constructs a new RedoButton. */
	public RedoButton(final Grid grid) {
		super("Redo", AssetHandler.getInstance().getSkin(), "small");
		this.setHeight(50);
		this.setWidth(DrawableTile.TILE_WIDTH);
		this.setX(DrawableTile.TILE_X + 3 * DrawableTile.TILE_WIDTH + 3 * TwentyFourtyGame.GAP);
		this.setY(100 / 2 - this.getHeight() / 2);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Command command = new RedoCommand(grid);
				command.execute();
			}
		});
	}
}
