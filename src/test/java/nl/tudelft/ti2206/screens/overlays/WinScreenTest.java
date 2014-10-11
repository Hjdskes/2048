package nl.tudelft.ti2206.screens.overlays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.screens.gamescreens.GameScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class WinScreenTest {
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private RestartButton restartButton;
	@Mock
	private ContinueButton continueButton;
	@Mock
	private GameScreen gameScreen;

	/**
	 * Initialize all mock objects and the object under test
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(gameScreen.getStage()).thenReturn(stage);
	}

	@Test
	public void testCreate() {
		WinScreen screen = new WinScreen(gameScreen, image, restartButton,
				continueButton);
		assertNotNull(screen);
		verify(stage).addActor((Image) image);
		verify(stage).addActor(restartButton);
		verify(stage).addActor(continueButton);
	}
}
