package nl.tudelft.ti2206.screens;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.ScoreDisplay;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MultiGameScreenTest {

	@Mock
	private Skin skin;
	@Mock
	private Stage stage;
	@Mock
	private Grid grid;
	@Mock
	private Group group;
	@Mock
	private Label label;
	@Mock
	private ScoreDisplay scores;
	@Mock
	private Texture texture;
	@Mock
	private Input input;

	private MultiGameScreen screen;

	/** Sets up all mock objects and the object under test */
	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		AssetHandler.getInstance().setSkin(skin);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);

		screen = new MultiGameScreen(stage, grid, label, group, scores);

		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		doNothing().when(group).addActor(any(Actor.class));

		when(label.getPrefWidth()).thenReturn(0f);
	}

	/**
	 * Tests if all required methods are called on screen.show().
	 */
	@Test
	public void testCreate() {
		screen.create();
		verify(input).setInputProcessor(stage);
		verify(label, times(2)).setX(anyInt());
		verify(label, times(2)).setY(anyInt());
		verify(group, times(2)).addActor(scores);
		verify(group, times(2)).addActor(grid);
		verify(group, times(2)).addActor(label);
		verify(stage).addListener(any(EventListener.class));
		verify(stage, times(2)).addActor(group);
	}

//	/**
//	 * Tests if all required methods are called on screen.update().
//	 */
//	@Test
//	public void testUpdateLocalWin() {
//		screen.update();
//		verify(stage).act();
//	}
}
