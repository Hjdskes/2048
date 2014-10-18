package nl.tudelft.ti2206.graphics.screens.overlays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.graphics.screens.overlays.CountDownScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CountDownScreenTest {

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
	private Input input;

	/** The screen that we are going to test. */
	private CountDownScreen screen;

	/** Sets up all mock objects and the object under test. */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		screen = new CountDownScreen(System.currentTimeMillis() + 10, stage,
				image);
	}

	/**
	 * Tests if all the necessary methods are called when the create method is
	 * called.
	 */
	@Test
	public void testCreate() {
		screen.create();
		verify(stage).addActor(image);
	}

	/**
	 * Tests if all the necessary methods are called when the update method is
	 * called.
	 */
	@Test
	public void testUpdateGo() {
		screen = new CountDownScreen(System.currentTimeMillis() - 4000, stage,
				image);
		screen.update();
		verify(stage).clear();
		verify(stage).addActor(image);
	}

	/**
	 * Tests if all the necessary methods are called when the create method is
	 * called.
	 */
	@Test
	public void testUpdateSet() {
		screen = new CountDownScreen(System.currentTimeMillis() - 2000, stage,
				image);
		screen.update();
		verify(stage).clear();
		verify(stage).addActor(image);
	}
}
