package nl.tudelft.ti2206.utils.states;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.utils.states.RunningState;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RunningStateTest {

	/** State that we are going to test. */
	private RunningState state;

	/**
	 * Mocked objects needed to create the necessary objects and verify if the
	 * correct methods are called.
	 */
	@Mock
	private Grid grid;
	@Mock
	private Grid remote;
	@Mock
	private ScreenHandler screenHandler;
	@Mock
	private Screen screen;

	/**
	 * Sets up the necessary mocking of objects and creates the RunningState.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		state = new RunningState(screenHandler);
		when(screenHandler.getScreen()).thenReturn(screen);
	}

	/**
	 * Checks if the game is won and the won overlay is created.
	 */
	@Test
	public void testSigleHighest2048() {
		when(grid.getCurrentHighestTile()).thenReturn(11);
		state.update(grid);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(false, true, grid);
	}

	/**
	 * Checks if the game is lost and the lost overlay is created.
	 */
	@Test
	public void testSingleFull() {
		when(grid.getPossibleMoves()).thenReturn(0);
		state.update(grid);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(false, false, grid);
	}

	/**
	 * Checks if the multiplayer game is won and the won overlay is created.
	 */
	@Test
	public void testMultiLocal2048() {
		when(grid.getCurrentHighestTile()).thenReturn(11);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(true, true, null);
	}

	/**
	 * Checks if the multiplayer game is lost and the lost overlay is created.
	 */
	@Test
	public void testMultiRemote2048() {
		when(remote.getCurrentHighestTile()).thenReturn(11);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(true, false, null);
	}

	/**
	 * Checks if the state is set to waiting when the local grid is full.
	 */
	@Test
	public void testMultiLocalFull() {
		when(grid.getPossibleMoves()).thenReturn(0);
		when(remote.getPossibleMoves()).thenReturn(1);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWaiting());
	}

	/**
	 * Checks if the game is lost when the score is equal and both player are
	 * out of moves.
	 */
	@Test
	public void testMultiLocalRemoteFull() {
		when(grid.getPossibleMoves()).thenReturn(0);
		when(remote.getPossibleMoves()).thenReturn(0);
		when(grid.getScore()).thenReturn(50);
		when(remote.getScore()).thenReturn(50);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isLost());
	}

	/**
	 * Checks if the multiplayer game is lost and the lost overlay is created.
	 */
	@Test
	public void testMultiRemoteScoreHigher() {
		when(grid.getPossibleMoves()).thenReturn(0);
		when(remote.getPossibleMoves()).thenReturn(0);
		when(remote.getScore()).thenReturn(20000);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(true, false, null);
	}

	/**
	 * Checks if the multiplayer game is won and the won overlay is created.
	 */
	@Test
	public void testMultiLocalScoreHigher() {
		when(grid.getPossibleMoves()).thenReturn(0);
		when(remote.getPossibleMoves()).thenReturn(0);
		when(grid.getScore()).thenReturn(20000);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(true, true, null);
	}
}
