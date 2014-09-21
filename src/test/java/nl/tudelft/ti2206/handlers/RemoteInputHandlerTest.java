package nl.tudelft.ti2206.handlers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RemoteInputHandlerTest {

	@Mock
	private Grid grid;
	
	private RemoteInputHandler handler;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		handler = new RemoteInputHandler(grid);
	}

	@Test
	public void testFillGrid() {
		handler.fillGrid("2,0");
		verify(grid).setTile(anyInt(), eq(2));
		verify(grid).setTile(anyInt(), eq(0));
	}

	@Test
	public void testMoveUp() {
		handler.moveUp();
		verify(grid).move(Direction.UP);
	}

	@Test
	public void testMoveDown() {
		handler.moveDown();
		verify(grid).move(Direction.DOWN);
	}

	@Test
	public void testMoveRight() {
		handler.moveRight();
		verify(grid).move(Direction.RIGHT);
	}

	@Test
	public void testMoveLeft() {
		handler.moveLeft();
		verify(grid).move(Direction.LEFT);
	}

}
