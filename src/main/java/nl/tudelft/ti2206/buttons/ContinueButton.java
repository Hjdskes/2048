package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.screens.ScreenHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to continue playing after having won.
 */
public class ContinueButton extends TextButton {
	public ContinueButton() {
		super("Continue!", AssetHandler.getSkin());
		this.setX(TwentyFourtyGame.GAME_WIDTH / 2 - getPrefWidth() / 2);
		this.setY(this.getPrefHeight() + 2 * TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TwentyFourtyGame.setState(GameState.CONTINUING);
				ScreenHandler.removeTop();
			}
		});
	}
}
