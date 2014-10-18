package nl.tudelft.ti2206.graphics.screens.overlays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.overlays.ConnectionLostScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ConnectionLostScreenTest {

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
	private MenuButton button;

	/** The screen that we are going to test. */
	private ConnectionLostScreen screen;

	/** Sets up all mock objects and the object under test. */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		screen = new ConnectionLostScreen(stage, image, button);
	}

	/**
	 * Tests if all the required methods are called when show() is called.
	 */
	@Test
	public void testCreate() {
		assertNotNull(screen);
		verify(stage).addActor(image);
		verify(stage).addActor(button);
	}

}
