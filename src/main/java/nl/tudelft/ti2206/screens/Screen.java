package nl.tudelft.ti2206.screens;

import com.badlogic.gdx.utils.Disposable;

/**
 * An abstract screen class.
 */
public interface Screen extends Disposable {
    /**
     * Called when the screen is shown. Used for initialization.
     */
    public void show();

    /**
     * Draws the screen.
     */
    public void draw();

    /**
     * Determines if the screen is an overlay or not. Overlays will not cause screens below it to automatically exit.
     *
     * @return True if the screen is an overlay, false otherwise.
     */
    public boolean isOverlay();

    /**
     * Pauses the screen.
     */
    public void pause();

    /**
     * Called when the screen is resized.
     *
     * @param width
     *         The new game window width (in pixels).
     * @param height
     *         The new game window height (in pixels).
     */
    public void resize(int width, int height);

    /**
     * Resumes the screen after a pause.
     */
    public void resume();

    /**
     * Updates the screen.
     */
    public void update();
}