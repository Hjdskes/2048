package nl.tudelft.ti2206.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.states.ContinuingState;
import nl.tudelft.ti2206.utils.states.DisqualifiedState;
import nl.tudelft.ti2206.utils.states.LostState;
import nl.tudelft.ti2206.utils.states.RunningState;
import nl.tudelft.ti2206.utils.states.WonState;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TwentyFourtyLauncherTest {

	/** Game we are going to test. */
	private TwentyFourtyGame game;

	/** Mocked AssetHandler. Needed to create TwentyFourtyGame object. */
	@Mock
	private AssetHandler assetHandler;
	/** Mocked screenHandler to verify that the correct method are called. */
	@Mock
	private ScreenHandler screenHandler;
	/** Mocked logger. Needed to create TwentyFourtyGame object. */
	@Mock
	private Logger logger;
	/** Mocked Skin. Needed to create AssetHandler object. */
	@Mock
	private Skin skin;

	/**
	 * Sets up the necessary objects before testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		game = new TwentyFourtyGame();
		game.setMockObjects(screenHandler, assetHandler, logger);
		assetHandler.setSkin(skin);
	}

	/**
	 * Tests if the render method invokes the correct methods in the
	 * screenHandler.
	 */
	@Test
	public void testRender() {
		game.render();
		verify(screenHandler).update();
		verify(screenHandler).draw();
	}

	/**
	 * Tests if the dispose method invokes the correct methods in the
	 * screenHandler and logger.
	 */
	@Test
	public void testDispose() {
		game.dispose();
		verify(screenHandler).dispose();
		verify(assetHandler).dispose();
		verify(logger).dispose();
	}

	/**
	 * Tests if the getter of the state returns the current state.
	 */
	@Test
	public void testGetState() {
		TwentyFourtyGame.setState(WonState.getInstance());
		assertEquals(WonState.getInstance(),
				TwentyFourtyGame.getState());
	}

	/**
	 * Tests if the current state is correctly set.
	 */
	@Test
	public void testIsRunning() {
		TwentyFourtyGame.setState(RunningState.getInstance());
		assertTrue(TwentyFourtyGame.isRunning());
	}

	/**
	 * Tests if the current state is correctly set.
	 */
	@Test
	public void testIsWon() {
		TwentyFourtyGame.setState(WonState.getInstance());
		assertTrue(TwentyFourtyGame.isWon());
	}

	/**
	 * Tests if the current state is correctly set.
	 */
	@Test
	public void testIsLost() {
		TwentyFourtyGame.setState(LostState.getInstance());
		assertTrue(TwentyFourtyGame.isLost());
	}

	/**
	 * Tests if the current state is correctly set.
	 */
	@Test
	public void testIsContinuing() {
		TwentyFourtyGame.setState(ContinuingState.getInstance());
		assertTrue(TwentyFourtyGame.isContinuing());
	}
	
	/**
	 * Tests if the current state is correctly set.
	 */
	@Test
	public void testIsDisqualified() {
		TwentyFourtyGame.setState(DisqualifiedState.getInstance());
		assertTrue(TwentyFourtyGame.isDisqualified());
	}
}
