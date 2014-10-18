package nl.tudelft.ti2206.graphics.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.menuscreens.MenuScreen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.net.Networking;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to cancel the current operation and to go back to the main menu.
 * Extends the TextButton class from the GDX library.
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
