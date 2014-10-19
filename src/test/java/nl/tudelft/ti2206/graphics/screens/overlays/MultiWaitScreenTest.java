package nl.tudelft.ti2206.graphics.screens.overlays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.overlays.MultiWaitScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MultiWaitScreenTest {

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
		MultiWaitScreen screen = new MultiWaitScreen(stage, image, button, true);
		assertNotNull(screen);
		verify(stage).addActor(image);
		verify(stage).addActor(button);
		verifyNoMoreInteractions(image);
		
		screen = new MultiWaitScreen(stage, image, button, false);
		verify(image).setX(TwentyFourtyGame.GAME_WIDTH);
	}
}
