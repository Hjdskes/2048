package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.Command;
import nl.tudelft.ti2206.handlers.UndoCommand;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to undo a move.
 */
public class UndoButton extends TextButton {
	/** Constructs a new UndoButton. */
	public UndoButton(final Grid grid) {
		super("Undo", AssetHandler.getInstance().getSkin());
		this.setX( (TwentyFourtyGame.GAME_WIDTH / 4) - this.getPrefWidth() / 2);
		this.setY(TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Command command = new UndoCommand(grid);
				command.execute();
			}
		});
	}
}