package nl.tudelft.ti2206.state;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.screens.Screen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RunningStateTest {

	private RunningState state;
	
	@Mock
	private Grid grid;
	@Mock
	private Grid remote;
	@Mock
	private ScreenHandler screenHandler;
	@Mock
	private Screen screen;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		state = new RunningState(screenHandler);
		when(screenHandler.getScreen()).thenReturn(screen);
	}

	@Test
	public void testSigleHighest2048() {
		when(grid.getCurrentHighestTile()).thenReturn(11);
		state.update(grid);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(false, true, grid);
	}
	
	@Test
	public void testSingleFull() {
		when(grid.getPossibleMoves()).thenReturn(0);
		state.update(grid);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(false, false, grid);
	}

	@Test
	public void testMultiLocal2048() {
		when(grid.getCurrentHighestTile()).thenReturn(11);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(true, true, null);
	}
	
	@Test
	public void testMultiRemote2048() {
		when(remote.getCurrentHighestTile()).thenReturn(11);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(true, false, null);
	}

	@Test
	public void testMultiLocalFull() {
		when(grid.getPossibleMoves()).thenReturn(0);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWaiting());
	}
	
	@Test
	public void testMultiRemoteScoreHigher() {
		when(grid.getPossibleMoves()).thenReturn(0);
		when(remote.getPossibleMoves()).thenReturn(0);
		when(remote.getScore()).thenReturn(20000);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(true, false, null);
	}
	
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
