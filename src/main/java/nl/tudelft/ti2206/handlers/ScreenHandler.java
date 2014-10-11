package nl.tudelft.ti2206.handlers;

import java.util.Stack;

import nl.tudelft.ti2206.screens.Screen;

import com.badlogic.gdx.Gdx;

/**
 * The ScreenHandler is responsible for managing all the screens.
 * 
 * It keeps a stack of all our screens and draws them from top to bottom, which
 * enables us to draw transparent screens, such as, for example, the WinScreen.
 */
public class ScreenHandler {
	/** The unique singleton instance of this class. */
	private static ScreenHandler instance = new ScreenHandler();

	/** The stack containing all the screens. */
	private static Stack<Screen> screenStack = new Stack<Screen>();

	/** Overrides the default constructor. */
	private ScreenHandler() {
	}

	/**
	 * 
	 * @return The singleton instance of the ScreenHandler.
	 */
	public static ScreenHandler getInstance() {
		return instance;
	}

	/** Sets the stack. Used for testing. */
	public void setScreenStack(Stack<Screen> screens) {
		screenStack = screens;
	}

	/**
	 * Adds the specified screen to the stack.
	 *
	 * @param screen
	 *            The screen.
	 */
	public void add(Screen screen) {
		screen.create();
		screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screenStack.push(screen);
	}

	/**
	 * Disposes cleanly of all the screens.
	 * */
	public void dispose() {
		for (Screen screen : screenStack) {
			screen.dispose();
		}
		screenStack.clear();
	}

	/**
	 * Draws all screens in the stack.
	 */
	public void draw() {
		for (Screen screen : screenStack) {
			screen.draw();
		}
	}

	/**
	 * Pauses all screens in the stack.
	 */
	public void pause() {
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
	public void resize(int width, int height) {
		for (Screen screen : screenStack) {
			screen.resize(width, height);
		}
	}

	/**
	 * Resumes all screens in the stack.
	 */
	public void resume() {
		for (Screen screen : screenStack) {
			screen.resume();
		}
	}

	/**
	 * Updates all screens in the stack.
	 */
	public void update() {
		screenStack.peek().update();
		for (int i = screenStack.size() - 1; i >= 0; i--) {
			Screen screen = screenStack.get(i);
			if (screen == null) {
				screenStack.remove(i);
			}
		}
	}

	/**
	 * Removes the specified screen from the stack.
	 *
	 * @param screen
	 *            The screen.
	 */
	public void remove(Screen screen) {
		screen.dispose();
		screenStack.remove(screen);
	}

	/**
	 * Removes the top screen and places input back into the new top screen.
	 */
	public void removeTop() {
		if (screenStack.size() == 1) {
			screenStack.get(0).restart();
			return;
		}

		remove(screenStack.peek());
		screenStack.peek().resume();
	}

	/**
	 * @param index
	 *            The index of the Screen to return.
	 * @return The Screen at index index.
	 */
	public Screen get(int index) {
		if (index < 0 || index > screenStack.size()) {
			return null;
		}
		return screenStack.get(index);
	}

	/**
	 * Returns the Screen of class, given by the String className.
	 * 
	 * @param target
	 *            The name of the ScreenClass to find.
	 * @return The found screen, null if no screen of class target is found.
	 */
	public Screen findScreen(String target) {
		for (Screen screen : screenStack) {
			if (screen.getClass().getSimpleName().equals(target)) {
				return screen;
			}
		}
		return null;
	}
}
