package nl.tudelft.ti2206.graphics.screens.overlays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.gamescreens.MultiGameScreen;
import nl.tudelft.ti2206.graphics.screens.overlays.MultiWinScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MultiWinScreenTest {

	/**
	 * Mocked objects needed to create the necessary objects and verify if the
	 * correct methods are called.
	 */
	@Mock
	private Skin skin;
	@Mock
	private MultiGameScreen screen;
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private MenuButton button;

	/**
	 * Initialize all mock objects.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(screen.getStage()).thenReturn(stage);
	}

	/**
	 * Tests if all the required methods are called when show() is called.
	 */
	@Test
	public void testCreate() {
		MultiWinScreen screen = new MultiWinScreen(stage, image, button);
		assertNotNull(screen);
		verify(stage).addActor(image);
		verify(stage).addActor(button);
	}
}
