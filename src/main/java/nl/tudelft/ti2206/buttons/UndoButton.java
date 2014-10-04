package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.screens.Screen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to undo a move.
 */
public class UndoButton extends TextButton {
	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new UndoButton. */
	public UndoButton() {
		super("Undo", AssetHandler.getInstance().getSkin());
		this.setX((TwentyFourtyGame.GAME_WIDTH / 3)*2 - this.getPrefWidth() / 2);
		this.setY(TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Screen gameScreen = screenHandler.get(0);
				Stage stage = gameScreen.getStage();
				Group group = stage.getRoot();
				Grid grid = group.findActor("Grid");
				screenHandler.removeTop();
				grid.undo();
				//grid.getTileHandler().moveUp();
			}
		});
	}
}