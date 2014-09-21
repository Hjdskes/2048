package nl.tudelft.ti2206.game;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.screens.Screen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TwentyFourtyGameTest {

	@Mock
	private Skin skin;
	@Mock
	private Screen screen;
	@Mock
	private Texture texture;
	@Mock
	private AssetManager manager;
	@Mock
	private Files files;
	@Mock
	private Graphics graphics;
	
	private TwentyFourtyGame game;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		Gdx.files = files;
		Gdx.graphics = graphics;

		when(skin.get(anyString(), eq(Texture.class))).thenReturn(texture);
		doNothing().when(manager).load(anyString(), eq(Texture.class));
		doNothing().when(skin).load(any(FileHandle.class));
		doNothing().when(screen).resize(anyInt(), anyInt());
		
		AssetHandler.setSkin(skin);
		AssetHandler.setAssetManager(manager);
		
		game = new TwentyFourtyGame(screen);
	}

	@Test
	public void testDispose() {
		// make sure thereis something to dispose.
		game.create();
		
		game.dispose();
		verify(screen).dispose();
		verify(manager).dispose();
	}

	@Test
	public void testRender() {
		// make sure there is something to render.
		game.create();
		
		game.render();
		verify(screen).update();
		verify(screen).draw();
	}

	@Test
	public void testResize() {
		// make sure there is something to resize.
		game.create();
		// Reset the screen to remove any method invocation counters.
		reset(screen);
		
		game.resize(0, 0);
		verify(screen).resize(0, 0);
	}

	@Test
	public void testCreate() {
		game.create();
		verify(manager, atLeast(1)).load(anyString(), eq(Texture.class));
		verify(skin).load(any(FileHandle.class));
		verify(screen).create();
	}

	@Test
	public void testGetState() {
		TwentyFourtyGame.setState(GameState.WON);
		assertEquals(GameState.WON, TwentyFourtyGame.getState());
	}

	@Test
	public void testSetState() {
		TwentyFourtyGame.setState(GameState.WON);
		assertEquals(GameState.WON, TwentyFourtyGame.getState());
	}

	@Test
	public void testIsRunning() {
		TwentyFourtyGame.setState(GameState.WON);
		assertFalse(TwentyFourtyGame.isRunning());
		TwentyFourtyGame.setState(GameState.RUNNING);
		assertTrue(TwentyFourtyGame.isRunning());
	}

	@Test
	public void testIsLost() {
		TwentyFourtyGame.setState(GameState.WON);
		assertFalse(TwentyFourtyGame.isLost());
		TwentyFourtyGame.setState(GameState.LOST);
		assertTrue(TwentyFourtyGame.isLost());
	}

	@Test
	public void testIsWon() {
		TwentyFourtyGame.setState(GameState.LOST);
		assertFalse(TwentyFourtyGame.isWon());
		TwentyFourtyGame.setState(GameState.WON);
		assertTrue(TwentyFourtyGame.isWon());
	}

	@Test
	public void testIsContinuing() {
		TwentyFourtyGame.setState(GameState.WON);
		assertFalse(TwentyFourtyGame.isContinuing());
		TwentyFourtyGame.setState(GameState.CONTINUING);
		assertTrue(TwentyFourtyGame.isContinuing());
	}
}
