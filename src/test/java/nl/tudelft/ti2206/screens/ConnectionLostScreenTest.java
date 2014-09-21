package nl.tudelft.ti2206.screens;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.buttons.MenuButton;

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

public class ConnectionLostScreenTest {

	@Mock
	private Skin skin;
	@Mock
	private BitmapFont font;
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private MenuButton button;
	@Mock
	private Input input;
	
	private MultiLoseScreen screen;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Gdx.input = input;
		screen = new MultiLoseScreen(stage, image, button);
	}

	@Test
	public void testCreate() {
		screen.create();
		
		verify(input).setInputProcessor(stage);
		verify(stage).addActor(image);
		verify(stage).addActor(button);
	}

	@Test
	public void testDraw() {
		screen.draw();
		verify(stage).draw();
	}

	@Test
	public void testIsOverlay() {
		assertTrue(screen.isOverlay());
	}

}
