package nl.tudelft.ti2206.screens;

import java.util.Stack;

import com.badlogic.gdx.Gdx;

/**
 * The ScreenHandler is responsible for managing all the screens.
 * 
 * It keeps a stack of all our screens and draws them from top to bottom, which
 * enables us to draw transparent screens, such as, for example, the WinScreen.
 * 
 */
public class ScreenHandler {
	private static Stack<Screen> screenStack = new Stack<Screen>();

	/** Sets the stack. Used for testing. */
	public static void setScreenStack(Stack<Screen> screens) {
		screenStack = screens;
	}

	/**
	 * Adds the specified screen to the stack.
	 *
	 * @param screen
	 *            The screen.
	 */
	public static void add(Screen screen) {
		screen.create();
		screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screenStack.push(screen);
	}

	/**
	 * Disposes cleanly of all the screens.
	 * */
	public static void dispose() {
		for (Screen screen : screenStack) {
			screen.dispose();
		}
		screenStack.clear();
	}

	/**
	 * Draws all screens in the stack.
	 */
	public static void draw() {
		for (Screen screen : screenStack) {
			screen.draw();
		}
	}

	/**
	 * Pauses all screens in the stack.
	 */
	public static void pause() {
		for (Screen screen : screenStack) {
			screen.pause();
		}
	}

	/**
	 * Resizes all screens in the stack
	 *
	 * @param width
	 *            The new game window width (in pixels).
	 * @param height
	 *            The new game window height (in pixels).
	 */
	public static void resize(int width, int height) {
		for (Screen screen : screenStack) {
			screen.resize(width, height);
		}
	}

	/**
	 * Resumes all screens in the stack.
	 */
	public static void resume() {
		for (Screen screen : screenStack) {
			screen.resume();
		}
	}

	/**
	 * Updates all screens in the stack.
	 */
	public static void update() {
		boolean coveredByOtherScreen = false;
		screenStack.peek().update();
		for (int i = screenStack.size() - 1; i >= 0; i--) {
			Screen screen = screenStack.get(i);
			if (screen == null) {
				screenStack.remove(i);
				continue;
			}
			if (coveredByOtherScreen) {
				remove(screen);
			}
			if (!screen.isOverlay()) {
				coveredByOtherScreen = true;
			}
		}
	}

	/**
	 * Removes the specified screen from the stack.
	 *
	 * @param screen
	 *            The screen.
	 */
	public static void remove(Screen screen) {
		screen.dispose();
		screenStack.remove(screen);
	}

	/**
	 * Removes the top screen and places input back into the new top screen.
	 */
	public static void removeTop() {
		remove(screenStack.peek());
		screenStack.peek().resume();
	}
}
