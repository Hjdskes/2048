package nl.tudelft.ti2206.graphics.screens.menuscreens;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.graphics.screens.menuscreens.MenuScreen;

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
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class MenuScreenTest {

	/**
	 * Mocked objects needed to create the necessary objects and verify if the
	 * correct methods are called.
	 */
	@Mock
	private Skin skin;
	@Mock
	private BitmapFont font;
	@Mock
	private Stage stage;
	@Mock
	private Cell<Label> labelCell;
	@Mock
	private Cell<TextButton> buttonCell;
	@Mock
	private Label label;
	@Mock
	private TextField field;
	@Mock
	private ImageButton imageButton;
	@Mock
	private TextButton button;
	@Mock
	private GL20 gl;
	@Mock
	private Input input;

	/** MenuScreen that we are going to test. */
	private MenuScreen screen;

	/** Sets up all mock objects and the object under test. */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		screen = new MenuScreen(stage, label, button, imageButton);
		Gdx.gl = gl;
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		doNothing().when(Gdx.gl).glClearColor(anyInt(), anyInt(), anyInt(),
				anyInt());
		doNothing().when(Gdx.gl).glClear(anyInt());

		when(labelCell.padTop(anyInt())).thenReturn(labelCell);
		when(labelCell.padBottom(anyInt())).thenReturn(labelCell);
		when(labelCell.row()).thenReturn(labelCell);

		when(buttonCell.padTop(anyInt())).thenReturn(buttonCell);
		when(buttonCell.padBottom(anyInt())).thenReturn(buttonCell);
		when(buttonCell.row()).thenReturn(buttonCell);
	}

	/**
	 * Tests if all the necessary methods are called when the dispose method is
	 * called.
	 */
	@Test
	public void testDispose() {
		screen.dispose();
		verify(stage).dispose();
	}

	/**
	 * Tests if all the necessary methods are called when the create method is
	 * called.
	 */
	@Test
	public void testCreate() {
		screen.create();
		verify(input).setInputProcessor(stage);

		verify(button, times(3)).setWidth(anyInt());
		verify(button, times(3)).setX(anyInt());
		verify(button, times(3)).setY(anyInt());
		verify(button, times(3)).addListener(any(EventListener.class));
	}
}
