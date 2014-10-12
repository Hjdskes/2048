package nl.tudelft.ti2206.screens.overlays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

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

	@Mock
	private Skin skin;
	@Mock
	private Stage stage;
	@Mock
	private Image image;
	@Mock
	private Input input;
	
	private CountDownScreen screen;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		screen = new CountDownScreen(System.currentTimeMillis() + 10, stage, image);
	}

	@Test
	public void testCreate() {
		screen.create();
		verify(stage).addActor(image);
	}
	
	@Test
	public void testUpdateGo() {
		screen = new CountDownScreen(System.currentTimeMillis() - 4000, stage, image);
		screen.update();
		verify(stage).clear();
		verify(stage).addActor(image);
	}
	
	@Test
	public void testUpdateSet() {
		screen = new CountDownScreen(System.currentTimeMillis() - 2000, stage, image);
		screen.update();
		verify(stage).clear();
		verify(stage).addActor(image);
	}
}
