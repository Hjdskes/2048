package nl.tudelft.ti2206.solver;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.log.Logger.LogLevel;

import org.junit.Before;
import org.junit.Test;

public class SolverTest {

	private Grid grid;
	private Solver solver;
	private Logger logger = Logger.getInstance();

	@Before
	public void setUp() throws Exception {
		logger.setLevel(LogLevel.NONE);
		grid = new Grid(true);
		grid.setTile(0, 2);
		grid.setTile(1, 4);
		solver = new Solver(grid, 3);
	}

	@Test
	public void testRun() {
		for (Tile tile : grid.getTiles()) {
			tile.setValue((int) Math.pow(2, tile.getIndex() + 1));
		}
		solver.run();
		// Make sure the solver has stopped
		assertFalse(solver.cancel());

		grid.setTile(0, 4);
		String old = grid.toString();
		solver.run();
		// Make sure the grid has changed
		assertNotEquals(old, grid.toString());
		solver.cancel();
	}

	@Test
	public void testSolve() {
		assertTrue(solver.scheduledExecutionTime() == 0);
		solver.solve();
		assertTrue(solver.scheduledExecutionTime() != 0);
		solver.cancel();
	}

	@Test
	public void testFindMoveHorizontal() {
		grid.setTile(0, 10);
		grid.setTile(1, 10);
		Direction moveMade = solver.findMove(grid, 3);
		boolean rightMoveMade = moveMade == Direction.LEFT
				|| moveMade == Direction.RIGHT;
		assertTrue(rightMoveMade);
		solver.cancel();
	}

	// FIXME: make sure it makes a move down or up when 2 1024 tiles are located
	// above each other

	@Test
	public void testFindMoveVertical() {
		grid.setTile(0, 10);
		grid.setTile(4, 10);
		Direction moveMade = solver.findMove(grid, 1);
		boolean rightMoveMade = moveMade == Direction.UP
				|| moveMade == Direction.DOWN;
		assertTrue(rightMoveMade);
		solver.cancel();
	}

}
