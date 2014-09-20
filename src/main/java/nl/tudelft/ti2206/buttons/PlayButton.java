package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to launch the WaitScreen, which waits untill both
 * connecting parties are ready and then launches the game.
 */
public class PlayButton extends TextButton {
	public PlayButton() {
		super("Connnect", AssetHandler.getSkin());
		this.setX((TwentyFourtyGame.GAME_WIDTH / 4) * 3 - this.getPrefWidth() / 2);
		this.setY(5 * TwentyFourtyGame.GAP);


	}
}
