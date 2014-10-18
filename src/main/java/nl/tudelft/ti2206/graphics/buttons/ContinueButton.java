package nl.tudelft.ti2206.graphics.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.states.ContinuingState;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to continue playing after having made a 2048 value tile. Extends the
 * TextButton class from the GDX library.
 */
public class ContinueButton extends TextButton {

	/** Constructs a new ContinueButton. */
	public ContinueButton() {
		super("Continue!", AssetHandler.getInstance().getSkin());
		this.setX(TwentyFourtyGame.GAME_WIDTH / 2 - getPrefWidth() / 2);
		this.setY(this.getPrefHeight() + 2 * TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TwentyFourtyGame.setState(ContinuingState.getInstance());
				ScreenHandler.getInstance().restart();
			}
		});
	}
}
