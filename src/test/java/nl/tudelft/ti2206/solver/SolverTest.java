package nl.tudelft.ti2206.solver;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

import org.junit.Before;
import org.junit.Test;

public class SolverTest {

	private Grid grid;
	private Solver solver;

	@Before
	public void setUp() throws Exception {
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
	}

	@Test
	public void testSolve() {
		assertTrue(solver.scheduledExecutionTime() == 0);
		solver.solve();
		assertTrue(solver.scheduledExecutionTime() != 0);
	}

	@Test
	public void testFindMoveHorizontal() {
		grid.setTile(0, 1024);
		grid.setTile(1, 1024);
		Direction moveMade = solver.findMove(grid, 3);
		boolean rightMoveMade = moveMade == Direction.LEFT
				|| moveMade == Direction.RIGHT;
		assertTrue(rightMoveMade);
	}

	// FIXME: make sure it makes a move down or up when 2 1024 tiles are located
	// above each other

	// @Test
	// public void testFindMoveVertical() {
	// grid.setTile(0, 1024);
	// grid.setTile(4, 1024);
	// Direction moveMade = solver.findMove(grid, 3);
	// boolean rightMoveMade = moveMade == Direction.UP
	// || moveMade == Direction.DOWN;
	// assertTrue(rightMoveMade);
	// }

}
