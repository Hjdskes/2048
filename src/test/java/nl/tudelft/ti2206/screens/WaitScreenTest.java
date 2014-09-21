package nl.tudelft.ti2206.screens;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.buttons.PlayButton;
import nl.tudelft.ti2206.net.Networking;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class WaitScreenTest {

	@Mock
	private Skin skin;
	@Mock
	private BitmapFont font;
	@Mock
	private Stage stage;
	@Mock
	private Table table;
	@Mock
	private Cell<Label> labelCell;
	@Mock
	private Cell<PlayButton> buttonCell;
	@Mock
	private Label label;
	@Mock
	private TextField field;
	@Mock
	private PlayButton button;
	@Mock
	private GL20 gl;
	@Mock
	private Input input;

	private WaitScreen screen;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		screen = new WaitScreen(stage, table, button, label);
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

		when(table.getCell(label)).thenReturn(labelCell);
		when(table.getCell(button)).thenReturn(buttonCell);
	}

	@Test
	public void testCreate() {
		screen.create();
		
		verify(input).setInputProcessor(stage);
		
		verify(table).add(label);
		verify(table).getCell(label);
		verify(labelCell).padTop(anyInt());
		verify(labelCell).padBottom(anyInt());
		verify(labelCell).row();
		
		verify(table).setFillParent(true);
		
		verify(stage).addActor(table);
		verify(stage).addActor(button);
		
		verify(button).addListener(any(EventListener.class));
	}

	@Test
	public void testUpdate() {
		screen.update();
		verify(stage).act();
		verify(label).setText("Connection lost.");
	}
}
