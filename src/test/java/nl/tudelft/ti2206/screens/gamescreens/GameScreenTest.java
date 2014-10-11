package nl.tudelft.ti2206.screens.gamescreens;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.buttons.RedoButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.buttons.SolveButton;
import nl.tudelft.ti2206.buttons.UndoButton;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.drawables.Scores;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.screens.gamescreens.GameScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreenTest {
	@Mock private Skin skin;
	@Mock private Stage stage;
	@Mock private Grid grid;
	@Mock private Texture texture;
	@Mock private GL20 gl;
	@Mock private Input input;
	@Mock private Preferences prefs;
	@Mock private Viewport viewPort;
	@Mock private Scores scores;
	@Mock private RestartButton restartButton;
	@Mock private SolveButton solveButton;
	@Mock private UndoButton undoButton;
	@Mock private RedoButton redoButton;
	
	private GameScreen screen;

	/** Sets up all mock objects and the object under test */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		new HeadlessLauncher().launch();
		AssetHandler.getInstance().setSkin(skin);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
		screen = new GameScreen(stage, grid, restartButton, solveButton, undoButton, redoButton, scores);

		Gdx.gl = gl;
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		doNothing().when(Gdx.gl).glClearColor(anyInt(), anyInt(), anyInt(),
				anyInt());
		doNothing().when(Gdx.gl).glClear(anyInt());
		
		when(stage.getViewport()).thenReturn(viewPort);
	}

	/**
	 * Tests if the stage is disposed on screen.dispose().
	 */
	@Test
	public void testDispose() {
		screen.dispose();
		verify(stage).dispose();
	}

	/**
	 * Tests if all required methods are called on screen.show().
	 */
	@Test
	public void testCreate() {
		screen.create();
		verify(input).setInputProcessor(stage);
		verify(stage).addListener(any(EventListener.class));
		verify(stage).addActor(scores);
		verify(stage).addActor(restartButton);
		verify(stage).addActor(solveButton);
		verify(stage).addActor(undoButton);
		verify(stage).addActor(redoButton);
	}
	
	/**
	 * Tests if all required methods are called on screen.draw().
	 */
	@Test
	public void testDraw() {
		screen.draw();
		verify(gl).glClearColor(anyInt(), anyInt(), anyInt(), anyInt());
		verify(gl).glClear(anyInt());
		verify(stage).draw();
	}
}
