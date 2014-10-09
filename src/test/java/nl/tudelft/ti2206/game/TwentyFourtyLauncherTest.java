package nl.tudelft.ti2206.game;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TwentyFourtyLauncherTest {
	
	private TwentyFourtyGame game;
	
	@Mock
	private AssetHandler assetHandler;
	@Mock
	private ScreenHandler screenHandler;
	@Mock
	private Logger logger;
	@Mock
	private Skin skin;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		game = new TwentyFourtyGame();
		game.setMockObjects(screenHandler, assetHandler, logger);
		assetHandler.setSkin(skin);
	}
	
	@Test
	public void testRender() {
		game.render();
		verify(screenHandler).update();
		verify(screenHandler).draw();
	}
	
	@Test
	public void testDispose() {
		game.dispose();
		verify(screenHandler).dispose();
		verify(assetHandler).dispose();
		verify(logger).dispose();
	}
	
	@Test
	public void testResize() {
		game.resize(0, 0);
		verify(screenHandler).resize(0, 0);
	}
	
	@Test
	public void testGetState() {
		TwentyFourtyGame.setState(GameState.WON);
		assertEquals(GameState.WON, TwentyFourtyGame.getState());
	}

	@Test
	public void testIsRunning() {
		TwentyFourtyGame.setState(GameState.RUNNING);
		assertTrue(TwentyFourtyGame.isRunning());
	}
	
	@Test
	public void testIsWon() {
		TwentyFourtyGame.setState(GameState.WON);
		assertTrue(TwentyFourtyGame.isWon());
	}
	
	@Test
	public void testIsLost() {
		TwentyFourtyGame.setState(GameState.LOST);
		assertTrue(TwentyFourtyGame.isLost());
	}
	
	@Test
	public void testIsContinuing() {
		TwentyFourtyGame.setState(GameState.CONTINUING);
		assertTrue(TwentyFourtyGame.isContinuing());
	}
}
