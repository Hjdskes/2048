package nl.tudelft.ti2206.screens;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WinScreenTest {
	@Mock
	private Skin skin;
	@Mock
	private BitmapFont font;
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private RestartButton restartButton;
	@Mock
	private ContinueButton continueButton;
	@Mock
	private Input input;

	private WinScreen screen;

	/**
	 * Initialize all mock objects and the object under test
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		screen = new WinScreen(stage, image, restartButton, continueButton);
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
	}

	/**
	 * Tests if all the required methods are called when show() is called.
	 */
	@Test
	public void testCreate() {
		screen.create();

		verify(stage).addActor(image);
		verify(stage).addActor(restartButton);
		verify(stage).addActor(continueButton);
	}

	@Test
	public void testDraw() {
		screen.draw();
		verify(stage).draw();
	}

	public void testIsOverlay() {
		assertTrue(screen.isOverlay());
	}

}
