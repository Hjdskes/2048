package nl.tudelft.ti2206.graphics.screens.overlays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.graphics.buttons.RestartButton;
import nl.tudelft.ti2206.graphics.screens.overlays.LoseScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LoseScreenTest {

	/**
	 * Mocked objects needed to create the necessary objects and verify if the
	 * correct methods are called.
	 */
	@Mock
	private Skin skin;
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private RestartButton restartButton;

	/** The screen that we are going to test */
	private LoseScreen screen;

	/**
	 * Initialize all mock objects and the object under test.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		screen = new LoseScreen(stage, image, restartButton);
	}

	/**
	 * Tests if all the required methods are called when show() is called.
	 */
	@Test
	public void testCreate() {
		assertNotNull(screen);
		verify(stage).addActor(image);
		verify(stage).addActor(restartButton);
	}
}
