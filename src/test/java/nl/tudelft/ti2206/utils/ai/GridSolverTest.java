package nl.tudelft.ti2206.utils.ai;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.ai.GridSolver.Strategy;
import nl.tudelft.ti2206.utils.log.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GridSolverTest {

	private Grid emptyGrid;
	private Grid grid;
	@Mock
	private Logger logger;
	@Mock
	private HumanSolver humanSolver;

	private GridSolver solver;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		emptyGrid = new Grid(true);
		grid = new Grid(true);
		for (int i = 0; i < 16; i++) {
			grid.getTiles()[i].setValue(11);
		}
	}

	private void initSolver(boolean isEmpty, Strategy strat) {
		if (isEmpty) {
			solver = new GridSolver(emptyGrid, strat, 1, 1);
		} else {
			solver = new GridSolver(grid, strat, 1, 1);
		}

		reset(logger);
		solver.setLogger(logger);
	}

	@Test
	public void testRun() {
		initSolver(true, Strategy.HUMAN);
		solver.run();
		verify(logger).info(solver.getClass().getSimpleName(),
				"Solver cannot make any more moves.");

		initSolver(false, Strategy.HUMAN);
		solver.run();
		verify(logger).debug(solver.getClass().getSimpleName(),
				"Direction selected: " + "LEFT");

		initSolver(false, Strategy.EXPECTIMAX);
		solver.run();
		verify(logger).debug(solver.getClass().getSimpleName(),
				"Direction selected: " + "LEFT");
	}

	@Test
	public void testStart() {
		initSolver(false, Strategy.HUMAN);
		solver.start();
		verify(logger).info(solver.getClass().getSimpleName(),
				"Starting solver...");
		assertTrue(solver.isRunning());
	}

	@Test
	public void testStopWhileRunning() {
		initSolver(false, Strategy.HUMAN);
		solver.start();
		assertTrue(solver.isRunning());
		reset(logger);
		solver.stop();
		assertFalse(solver.isRunning());
		verify(logger).info(solver.getClass().getSimpleName(),
				"Solver stopped.");
	}
}
