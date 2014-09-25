package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.net.Networking;
import nl.tudelft.ti2206.screens.MenuScreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to cancel the current operation and to go back to the main menu.
 */
public class MenuButton extends TextButton {
	public MenuButton() {
		super("Menu", AssetHandler.getInstance().getSkin());
		this.setX(TwentyFourtyGame.GAME_WIDTH / 4 - this.getPrefWidth() / 2);
		this.setY(5 * TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Networking.getInstance().disconnect();
				ScreenHandler.getInstance().add(new MenuScreen());
			}
		});
	}
}
