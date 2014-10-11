package nl.tudelft.ti2206.buttons;

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
		super("Redo", AssetHandler.getInstance().getSkin());
		this.setX((TwentyFourtyGame.GAME_WIDTH / 4)*3 - this.getPrefWidth() / 2);
		this.setY(TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Command command = new RedoCommand(grid);
				command.execute();
			}
		});
	}
}
