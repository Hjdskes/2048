package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to restart the current game.
 */
public class RestartButton extends TextButton {
	public RestartButton() {
		super("Restart", AssetHandler.getInstance().getSkin());
		this.setX(TwentyFourtyGame.GAME_WIDTH / 2 - this.getPrefWidth() / 2);
		this.setY(TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//FIXME: this does not work from win- or losescreen! Their stages do not
				//       own a grid, so we get a nullpointer.
				//((Grid) event.getStage().getRoot().findActor("Grid")).restart();
			}
		});
	}
}
