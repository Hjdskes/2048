package nl.tudelft.ti2206.graphics.screens.overlays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.graphics.buttons.ContinueButton;
import nl.tudelft.ti2206.graphics.buttons.RestartButton;
import nl.tudelft.ti2206.graphics.screens.overlays.WinScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class WinScreenTest {

	/**
	 * Mocked objects needed to create the necessary objects and verify if the
	 * correct methods are called.
	 */
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private RestartButton restartButton;
	@Mock
	private ContinueButton continueButton;

	/**
	 * Initialize all mock objects.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Tests if all the required methods are called when show() is called.
	 */
	@Test
	public void testCreate() {
		WinScreen screen = new WinScreen(stage, image, restartButton,
				continueButton);
		assertNotNull(screen);
		verify(stage).addActor((Image) image);
		verify(stage).addActor(restartButton);
		verify(stage).addActor(continueButton);
	}
}
