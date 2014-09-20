package nl.tudelft.ti2206.screens;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.buttons.CancelButton;
import nl.tudelft.ti2206.buttons.PlayButton;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ClientScreenTest {

	@Mock
	private Skin skin;
	@Mock
	private BitmapFont font;
	@Mock
	private Stage stage;
	@Mock
	private Label label;
	@Mock
	private TextField field;
	@Mock
	private CancelButton cancelButton;
	@Mock
	private PlayButton playButton;
	@Mock
	private GL20 gl;
	@Mock
	private Input input;

	private ClientScreen screen;

	/**
	 * Initialize all mock objects and the object under test
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		screen = new ClientScreen(stage, label, field, cancelButton, playButton);
		Gdx.gl = gl;
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		doNothing().when(Gdx.gl).glClearColor(anyInt(), anyInt(), anyInt(),
				anyInt());
		doNothing().when(Gdx.gl).glClear(anyInt());
	}

	/**
	 * Tests if all the required methods are called when drawing.
	 */
	@Test
	public void testDraw() {
		screen.draw();
		verify(stage).draw();
	}
	
	@Test
	public void testUpdate() {
		screen.update();
		verify(stage).act();
	}

	/**
	 * Tests if all the required methods are called when show() is called.
	 */
	@Test
	public void testCreate() {
		screen.show();
		verify(playButton).setVisible(false);

		verify(label).setX(anyInt());
		verify(label).setY(anyInt());
		verify(stage).addActor(label);

		verify(field).setX(anyInt());
		verify(field).setY(anyInt());
		verify(stage).addActor(field);

		verify(stage).addActor(cancelButton);
		verify(stage).addActor(playButton);
	}
	
	/**
	 * Tests if the stage is disposed when the screen is disposed.
	 */
	@Test
	public void testDispose() {
		screen.dispose();
		verify(stage).dispose();
	}
}
