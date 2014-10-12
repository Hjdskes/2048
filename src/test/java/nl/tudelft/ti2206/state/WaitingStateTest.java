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

public class WaitingStateTest {

	@Mock
	private Grid grid;
	@Mock
	private Grid remote;
	@Mock
	private ScreenHandler screenHandler;
	@Mock
	private Screen screen;
	
	private WaitingState state;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		state = new WaitingState(screenHandler);
		when(screenHandler.getScreen()).thenReturn(screen);
	}

	@Test
	public void testSingle() {
		state.update(grid);
		verifyNoMoreInteractions(grid);
	}

	@Test
	public void testMultiLoss() {
		when(remote.getScore()).thenReturn(1);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isLost());
		verify(screen).addLWOverlay(true, false, null);
	}

	@Test
	public void testMultiWin() {
		when(grid.getScore()).thenReturn(1);
		state.update(grid, remote);
		assertTrue(TwentyFourtyGame.isWon());
		verify(screen).addLWOverlay(true, true, null);
	}
}
