package nl.tudelft.ti2206.graphics.screens.menuscreens;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.menuscreens.HostScreen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class HostScreenTest {

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
	private Table table;
	@Mock
	private Cell<Label> cell;
	@Mock
	private Label label;
	@Mock
	private TextField field;
	@Mock
	private MenuButton menuButton;
	@Mock
	private GL20 gl;
	@Mock
	private Input input;

	/** MenuScreen that we are going to test. */
	private HostScreen screen;

	/** Sets up all mock objects and the object under test. */
	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		Skin skin = mock(Skin.class);
		AssetHandler.getInstance().setSkin(skin);

		screen = new HostScreen(stage, table, label, label, menuButton);
		Gdx.gl = gl;
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		doNothing().when(Gdx.gl).glClearColor(anyInt(), anyInt(), anyInt(),
				anyInt());
		doNothing().when(Gdx.gl).glClear(anyInt());

		when(cell.padTop(anyInt())).thenReturn(cell);
		when(cell.padBottom(anyInt())).thenReturn(cell);
		when(cell.row()).thenReturn(cell);

		when(table.getCell(label)).thenReturn(cell);
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

		verify(table, times(4)).add(label);
		verify(cell, times(4)).padTop(anyInt());
		verify(cell, times(4)).padBottom(anyInt());
		verify(cell, times(4)).row();

		verify(stage).addActor(table);
		verify(stage).addActor(menuButton);

		verify(input).setInputProcessor(stage);
	}

	/**
	 * Tests if all the necessary methods are called when the create method is
	 * called.
	 */
	@Test
	public void testDraw() {
		screen.draw();
		verify(gl).glClearColor(anyInt(), anyInt(), anyInt(), anyInt());
		verify(gl).glClear(anyInt());

		verify(stage).draw();
	}

	/**
	 * Tests if all the necessary methods are called when the update method is
	 * called.
	 */
	@Test
	public void testUpdate() {
		screen.update();
		verify(stage).act();
	}
}
