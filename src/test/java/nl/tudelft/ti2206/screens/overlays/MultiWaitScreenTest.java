package nl.tudelft.ti2206.screens.overlays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.buttons.MenuButton;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MultiWaitScreenTest {

	@Mock
	private Skin skin;
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private MenuButton button;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreate() {
		MultiWaitScreen screen = new MultiWaitScreen(stage, image, button);
		assertNotNull(screen);
		verify(stage).addActor(image);
		verify(stage).addActor(button);
 	}
}
