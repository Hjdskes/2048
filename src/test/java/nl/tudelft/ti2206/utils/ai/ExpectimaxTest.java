package nl.tudelft.ti2206.utils.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.log.Logger.LogLevel;
import nl.tudelft.ti2206.utils.states.LostState;
import nl.tudelft.ti2206.utils.states.RunningState;

import org.junit.Before;
import org.junit.Test;

public class ExpectimaxTest {

	private Grid grid;
	private Expectimax solver;
	private Logger logger = Logger.getInstance();

	@Before
	public void setUp() {
		logger.setLevel(LogLevel.NONE);
		grid = new Grid(true);
		grid.setTile(0, 1);
		grid.setTile(1, 2);
		solver = new Expectimax();
		TwentyFourtyGame.setState(RunningState.getInstance());
	}

	@Test
	public void testRunDone() {
		// make sure the grid is empty
		TwentyFourtyGame.setState(LostState.getInstance());
		grid = new Grid(true);
		String old = grid.toString();
		// make sure no move has been made
		assertEquals(old, grid.toString());
	}

	@Test
	public void testFindMoveHorizontal() {
		grid.setTile(0, 10);
		grid.setTile(1, 10);
		Direction moveMade = solver.findMove(grid, 0);
		boolean rightMoveMade = moveMade == Direction.LEFT
				|| moveMade == Direction.RIGHT;
		assertTrue(rightMoveMade);
	}

	@Test
	public void testFindMoveVertical() {
		grid.setTile(0, 10);
		grid.setTile(4, 10);
		Direction moveMade = solver.findMove(grid, 0);
		boolean rightMoveMade = moveMade == Direction.UP
				|| moveMade == Direction.DOWN;
		assertTrue(rightMoveMade);
	}
	
	@Test 
	public void testDepth0() {
		grid = new Grid(true);
		solver = new Expectimax();
		assertEquals(null, solver.findMove(grid, 0));
	}
}