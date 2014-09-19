package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ButtonHandler;

import com.badlogic.gdx.graphics.g2d.Batch;

public class ButtonDisplay implements Drawable {

	private static final int DEFAULT_X = 300;

	/** The height of the game window. */
	private static final int GAME_HEIGHT = 600;

	/** The width of the gap between objects */
	private static final int GAP = 15;

	private GameWorld world;
	private boolean isSinglePlayer;
	private RestartButton restartButton;
	private ContinueButton continueButton;

	/**
	 * Creates a new ButtonDisplay object with the specified parameters.
	 * 
	 * @param world
	 *            The GameWorld of the current game.
	 * @param isSinglePlayer
	 *            If true, the buttons are only drawn at the position of the
	 *            single player. Else, buttons for another player are drawn as
	 *            well.
	 */
	public ButtonDisplay(GameWorld world, boolean isSinglePlayer) {
		this.world = world;
		this.isSinglePlayer = isSinglePlayer;
		initButtons();
	}

	/**
	 * Initializes and sets the restart button and the continue button.
	 */
	private void initButtons() {
		restartButton = new RestartButton(DEFAULT_X
				- AssetHandler.restart.getWidth() / 2, GAME_HEIGHT - GAP
				- AssetHandler.restart.getHeight(),
				AssetHandler.restart.getWidth(),
				AssetHandler.restart.getHeight());
		ButtonHandler.setRestartButton(restartButton);

		continueButton = new ContinueButton(DEFAULT_X
				- AssetHandler.continueb.getWidth() / 2, GAME_HEIGHT - GAP * 3
				- AssetHandler.continueb.getHeight() * 2,
				AssetHandler.continueb.getWidth(),
				AssetHandler.continueb.getHeight());
		ButtonHandler.setContinueButton(continueButton);
	}

	/**
	 * Calculates the correct x coordinate for the multiplayer game.
	 */
	private void setMPXCoords() {
		continueButton.setX(continueButton.getX() + 600);
		restartButton.setX(restartButton.getX() + 600);
	}

	/**
	 * Draws the continue button and the restart button at their designated
	 * positions.
	 */
	@Override
	public void draw(Batch batch) {
		drawContinueButton(batch);
		drawRestartButton(batch);
		if (!isSinglePlayer) {
			setMPXCoords();
			drawContinueButton(batch);
			drawRestartButton(batch);
		}
	}

	/**
	 * Renders the button that will initiate a restart of the game.
	 */
	private void drawRestartButton(Batch batch) {
		restartButton.draw(batch);
	}

	/**
	 * Renders the button that will allow to continue playing after winning.
	 */
	private void drawContinueButton(Batch batch) {
		if (world.isWon())
			continueButton.draw(batch);
	}
}
