package nl.tudelft.ti2206.utils.states;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContinuingStateTest {

	/** State that we are going to test. */
	private ContinuingState state;

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
	 * Sets up the necessary mocking of objects and creates the ContinuingState.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		state = ContinuingState.getInstance();
		state.setScreenHandler(screenHandler);
		when(screenHandler.getScreen()).thenReturn(screen);
		when(screen.hasOverlay()).thenReturn(false);
	}

	/**
	 * Check if the right overlay is added during the state.
	 */
	@Test
	public void testAddDisqualified() {
		when(grid.getCurrentHighestTile()).thenReturn(11);
		state.update(grid,grid);
		verify(screen).addBoardOverlay(false, false);
	}
}
