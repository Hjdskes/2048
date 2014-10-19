package nl.tudelft.ti2206.graphics.screens.gamescreens;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.drawables.Scores;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.gamescreens.UserComputerScreen.Difficulty;
import nl.tudelft.ti2206.utils.ai.GridSolver;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.net.Networking;
import nl.tudelft.ti2206.utils.states.LostState;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UserComputerScreenTest {

	/**
	 * Mocked objects needed to create the necessary objects and verify if the
	 * correct methods are called.
	 */
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
	private Scores scores;
	@Mock
	private Texture texture;
	@Mock
	private Input input;
	@Mock
	private Networking networking;
	@Mock
	private GridSolver solver;
	@Mock
	private ScreenHandler handler;
	@Mock
	private Screen mockScreen;

	/** Gamescreen that we are going to test. */
	private UserComputerScreen screen;

	/** Sets up all mock objects and the object under test */
	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		AssetHandler.getInstance().setSkin(skin);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);

		screen = new UserComputerScreen(stage, grid, label, group, scores,
				Difficulty.EXTREME, solver, handler);

		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		doNothing().when(group).addActor(any(Actor.class));

		when(label.getPrefWidth()).thenReturn(0f);
		
		when(handler.getScreen()).thenReturn(mockScreen);
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
		// verify(group, times(2)).addActor(grid);
		verify(group, times(2)).addActor(label);
		verify(stage).addListener(any(EventListener.class));
		verify(stage, times(2)).addActor(group);
	}
	
	@Test
	public void testSetYouLabel() {
		screen.setYouLabel("You", Color.BLACK);
		verify(label).setText("You");
		verify(label).setColor(Color.BLACK);
		verify(label).setX(anyInt());
	}
	
	@Test
	public void testSetOpponentLabel() {
		screen.setYouLabel("Opponent", Color.BLUE);
		verify(label).setText("Opponent");
		verify(label).setColor(Color.BLUE);
		verify(label).setX(anyInt());
	}
	
	@Test
	public void testUpdate() {
		TwentyFourtyGame.setState(LostState.getInstance());
		screen.update();
		verify(stage).act();
	}
	
	@Test
	public void testDispose() {
		screen.dispose();
		verify(stage).dispose();
		verify(solver).stop();
	}
}
