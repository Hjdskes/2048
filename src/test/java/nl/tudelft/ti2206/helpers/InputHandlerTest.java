package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Input.Keys;

public class InputHandlerTest {

	private static InputHandler handler;
	private static GameWorld world;
	private static Grid grid;
	private static RestartButton restartButton;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HeadlessLauncher launcher = new HeadlessLauncher();
		launcher.launch();
		world = new GameWorld();

		grid = Mockito.mock(Grid.class);
		world.setGrid(grid);

		restartButton = Mockito.mock(RestartButton.class);
		handler = new InputHandler(world);
		handler.setRestartButton(restartButton);
	}

	@Test
	public void testConstructor() {
		assertNotNull(handler);
	}

	@Test
	public void testKeyDown1() {
		handler.keyDown(Keys.DPAD_DOWN);
		verify(grid).move(Direction.DOWN);
	}

	@Test
	public void testKeyDown2() {
		handler.keyDown(Keys.DPAD_UP);
		verify(grid).move(Direction.UP);
	}

	@Test
	public void testKeyDown3() {
		handler.keyDown(Keys.DPAD_LEFT);
		verify(grid).move(Direction.LEFT);
	}

	@Test
	public void testKeyDown4() {
		handler.keyDown(Keys.DPAD_RIGHT);
		verify(grid).move(Direction.RIGHT);
	}

	@Test
	public void testKeyDown5() {
		handler.keyDown(Keys.ESCAPE);
		verify(restartButton).onClick(world);
		// reset to remove method invocation counter
		reset(restartButton);
	}

	@Test
	public void testTouchDown() {
		stub(restartButton.isTouchDown(0, 0))
				.toReturn(true);
		
		handler.touchDown(0, 0, 0, 0);
		
		verify(restartButton).onClick(world);
		// reset to remove method invocation counter
		reset(restartButton);
	}
}
