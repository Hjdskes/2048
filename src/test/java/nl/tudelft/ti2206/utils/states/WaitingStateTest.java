package nl.tudelft.ti2206.utils.states;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.utils.states.WaitingState;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WaitingStateTest {

	/** State that we are going to test */
	private WaitingState state;

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
	 * Sets up the necessary mocking of objects and creates the new
	 * WaitingState.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		state = WaitingState.getInstance();
		state.setScreenHandler(screenHandler);
		when(screenHandler.getScreen()).thenReturn(screen);
	}

	/**
	 * Checks if there are no interactions with the grid when nothing has happened
	 * yet.
	 */
	@Test
	public void testSingle() {
		state.update(grid);
		verifyNoMoreInteractions(grid);
	}

	/**
	 * Checks if the game state is set to lost and the lost overlay is created.
	 */
	@Test
	public void testMultiLoss() {
		when(remote.getScore()).thenReturn(1);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(true, false, null);
	}

	/**
	 * Checks if the game state is set to won and the won overlay is created.
	 */
	@Test
	public void testMultiWin() {
		when(grid.getScore()).thenReturn(1);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(true, true, null);
	}

	/**
	 * Checks if the game is won when the score is equal and both player are out
	 * of moves.
	 */
	@Test
	public void testMultiSameScoreWin() {
		when(grid.getPossibleMoves()).thenReturn(0);
		when(remote.getPossibleMoves()).thenReturn(0);
		when(grid.getScore()).thenReturn(50);
		when(remote.getScore()).thenReturn(50);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(true, true, null);
	}
}
