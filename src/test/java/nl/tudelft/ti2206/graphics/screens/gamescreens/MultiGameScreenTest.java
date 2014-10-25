package nl.tudelft.ti2206.graphics.screens.gamescreens;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Observer;

import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.drawables.Scores;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.input.RemoteInputHandler;
import nl.tudelft.ti2206.utils.net.Networking;
import nl.tudelft.ti2206.utils.security.MoveValidator;
import nl.tudelft.ti2206.utils.states.DisqualifiedState;
import nl.tudelft.ti2206.utils.states.RunningState;
import nl.tudelft.ti2206.utils.states.WaitingState;

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

public class MultiGameScreenTest {

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
	private ScreenHandler handler;
	@Mock
	private Screen screen;
	@Mock
	private RemoteInputHandler remoteInput;
	@Mock
	private MoveValidator validator;

	/** Gamescreen that we are going to test. */
	private MultiGameScreen gameScreen;

	/** Sets up all mock objects and the object under test */
	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		AssetHandler.getInstance().setSkin(skin);
		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);

		gameScreen = new MultiGameScreen(handler, stage, grid, label, group,
				scores, remoteInput, networking);
		
		
		
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
		doNothing().when(group).addActor(any(Actor.class));

		doNothing().when(networking).deleteObserver(any(Observer.class));

		when(label.getPrefWidth()).thenReturn(0f);
		when(handler.getScreen()).thenReturn(screen);
		when(remoteInput.getMoveValidator()).thenReturn(validator);
		when(validator.getIrregularity()).thenReturn(false);
		RunningState.getInstance().setScreenHandler(handler);
		WaitingState.getInstance().setScreenHandler(handler);
	}

	/**
	 * Tests if all required methods are called on screen.show().
	 */
	@Test
	public void testCreate() {
		gameScreen.create();
		verify(input).setInputProcessor(stage);
		verify(label, times(2)).setX(anyInt());
		verify(label, times(2)).setY(anyInt());
		verify(group, times(2)).addActor(scores);
		// verify(group, times(2)).addActor(grid);
		verify(group, times(2)).addActor(label);
		verify(stage).addListener(any(EventListener.class));
		verify(stage, times(2)).addActor(group);
	}

	/**
	 * Tests if all required methods are called on screen.dispose().
	 */
	@Test
	public void testDispose() {
		gameScreen.dispose();
		verify(networking).deleteObserver(any(Observer.class));
	}

	/**
	 * Tests if all required methods are called on screen.setYouLabel().
	 */
	@Test
	public void testSetYouLabel() {
		gameScreen.setYouLabel("String", Color.RED);
		verify(label).setText("String");
		verify(label).setColor(Color.RED);
		verify(label).setX(anyFloat());
	}

	/**
	 * Tests if all required methods are called on screen.setOppenentLabel().
	 */
	@Test
	public void testSetOpponentLabel() {
		gameScreen.setOpponentLabel("OString", Color.BLACK);
		verify(label).setText("OString");
		verify(label).setColor(Color.BLACK);
		verify(label).setX(anyFloat());
	}

	/**
	 * Tests if all required methods are called on screen.update() and the
	 * connection is lost.
	 */
	@Test
	public void testUpdateConnectionLost() {
		TwentyFourtyGame.setState(WaitingState.getInstance());
		when(networking.isConnectionLost()).thenReturn(true);
		when(grid.getPossibleMoves()).thenReturn(1);
		gameScreen.update();
		verify(stage).act();
		verify(screen).addConnectionLostOverlay();
	}

	 @Test
	 public void testUpdateWaiting() {
	 TwentyFourtyGame.setState(WaitingState.getInstance());
	 gameScreen.update();
	 verify(stage).act();
	 verify(label).setText("WAITING");
	 }

	 @Test
	 public void testUpdateRemoteLost() {
	 TwentyFourtyGame.setState(RunningState.getInstance());
	 when(grid.getPossibleMoves()).thenReturn(0);
	 gameScreen.update();
	 verify(stage).act();
	 verify(label).setText("WAITING");
	 }
	 
	 @Test
	 public void testRemoteCheater() {
	 TwentyFourtyGame.setState(RunningState.getInstance());
	 when(validator.getIrregularity()).thenReturn(true);
	 gameScreen.update();
	 verify(stage).act();
	 verify(screen).addBoardOverlay(false,false);
	 verify(screen).addLWOverlay(false,true, grid);
	 verify(label).setText("CHEATER");
	 }
	 
	 @Test
	 public void testLocalCheater() {
	 TwentyFourtyGame.setState(DisqualifiedState.getInstance());
	 gameScreen.update();
	 verify(stage).act();
	 verify(screen).addBoardOverlay(false,true);
	 verify(label).setText("CHEATER");
	 }
}
