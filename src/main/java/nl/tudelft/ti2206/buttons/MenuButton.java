package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.net.Networking;
import nl.tudelft.ti2206.screens.menuscreens.MenuScreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to cancel the current operation and to go back to the main menu.
 */
public class MenuButton extends TextButton {

	/** Constructs a new MenuButton. */
	public MenuButton() {
		super("Menu", AssetHandler.getInstance().getSkin());
		this.setX(2 * TwentyFourtyGame.GAP);
		this.setY(2 * TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Networking.getInstance().disconnect();
				ScreenHandler.getInstance().set(new MenuScreen());
			}
		});
	}
}
