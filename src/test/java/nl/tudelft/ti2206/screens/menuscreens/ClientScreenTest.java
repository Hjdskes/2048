package nl.tudelft.ti2206.screens.menuscreens;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.screens.menuscreens.ClientScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
	private MenuButton menuButton;
	@Mock
	private TextButton playButton;
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
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		screen = new ClientScreen(stage, label, field, menuButton, playButton);
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
		when(field.getText()).thenReturn("");
		screen.update();
		verify(stage).act();
	}

	/**
	 * Tests if all the required methods are called when show() is called.
	 */
	@Test
	public void testCreate() {
		screen.create();
		verify(playButton).setVisible(false);
		verify(playButton).setX(anyInt());
		verify(playButton).setY(anyInt());
		verify(playButton).addListener(any(EventListener.class));

		verify(label).setX(anyInt());
		verify(label).setY(anyInt());
		verify(stage).addActor(label);

		verify(field).setX(anyInt());
		verify(field).setY(anyInt());
		verify(field).setWidth(anyInt());
		verify(field).setMaxLength(anyInt());
		verify(stage).addActor(field);

		verify(stage).addActor(menuButton);
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
