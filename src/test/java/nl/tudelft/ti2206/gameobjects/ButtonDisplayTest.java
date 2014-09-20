package nl.tudelft.ti2206.gameobjects;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class ButtonDisplayTest {

	/** Button to mock for testing. */
	private static Button button;

	/** Object ButtonDisplay which we are going to test. */
	private static ButtonDisplay buttondisplay;

	/**
	 * Setting up the button display with the continue and restart button
	 * mocked.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		button = mock(Button.class);
		buttondisplay = new ButtonDisplay(button, button);
	}

	/**
	 * Tests if the act() method does invoke the act() method of the continue
	 * and restart button.
	 */
	@Test
	public void testAct() {
		buttondisplay.act(1);
		verify(buttondisplay.getContinueButton(), times(2)).act(anyInt());
		verify(buttondisplay.getRestartButton(), times(2)).act(anyInt());
	}
}
