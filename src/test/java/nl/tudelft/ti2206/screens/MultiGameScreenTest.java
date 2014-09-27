package nl.tudelft.ti2206.screens;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
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
	@Mock
	private ConnectionLostScreen clScreen;
	@Mock
	private MultiWinScreen mwScreen;
	@Mock
	private MultiLoseScreen mlScreen;

	private Grid localGrid;

	private Grid remoteGrid;

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

	/**
	 * Tests if all required methods are called on screen.update().
	 */
	@Test
	public void testUpdateLocalWin() {
		localGrid = mock(Grid.class);
		remoteGrid = mock(Grid.class);
		screen.setLocalGrid(localGrid);
		screen.setRemoteGrid(remoteGrid);

		when(localGrid.getCurrentHighestTile()).thenReturn(2048);
		screen.update(clScreen, mwScreen, mlScreen);
		verify(localGrid).getCurrentHighestTile();
		verify(mwScreen).create();
		assertEquals(TwentyFourtyGame.getState(), GameState.WON);
	}

	/**
	 * Tests if all required methods are called on screen.update().
	 */
	@Test
	public void testUpdateLocalLost() {
		localGrid = mock(Grid.class);
		remoteGrid = mock(Grid.class);
		screen.setLocalGrid(localGrid);
		screen.setRemoteGrid(remoteGrid);

		when(remoteGrid.getPossibleMoves()).thenReturn(1);
		when(remoteGrid.getCurrentHighestTile()).thenReturn(2048);
		when(localGrid.getPossibleMoves()).thenReturn(1);

		screen.update(clScreen, mwScreen, mlScreen);
		verify(localGrid).getCurrentHighestTile();
		verify(remoteGrid).getPossibleMoves();
		verify(localGrid).getPossibleMoves();
		verify(remoteGrid).getCurrentHighestTile();
		verify(mlScreen).create();
		assertEquals(TwentyFourtyGame.getState(), GameState.LOST);
	}

	/**
	 * Tests if all required methods are called on screen.update().
	 */
	@Test
	public void testUpdate() {
		TwentyFourtyGame.setState(GameState.RUNNING);
		localGrid = mock(Grid.class);
		remoteGrid = mock(Grid.class);
		screen.setLocalGrid(localGrid);
		screen.setRemoteGrid(remoteGrid);

		when(remoteGrid.getPossibleMoves()).thenReturn(1);
		when(remoteGrid.getCurrentHighestTile()).thenReturn(4);
		when(localGrid.getPossibleMoves()).thenReturn(1);
		when(localGrid.getCurrentHighestTile()).thenReturn(4);

		screen.update(clScreen, mwScreen, mlScreen);
		verify(localGrid).getCurrentHighestTile();
		verify(remoteGrid).getPossibleMoves();
		verify(localGrid).getPossibleMoves();
		verify(remoteGrid).getCurrentHighestTile();
		verify(mlScreen, never()).create();
		verify(mwScreen, never()).create();
		assertEquals(TwentyFourtyGame.getState(), GameState.RUNNING);
	}
}
