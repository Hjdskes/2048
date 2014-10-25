package nl.tudelft.ti2206.utils.ai.solvers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.find;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.ai.solvers.GridSolver;
import nl.tudelft.ti2206.utils.ai.solvers.HumanSolver;
import nl.tudelft.ti2206.utils.ai.solvers.GridSolver.Strategy;
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
		new HeadlessLauncher().launch();
		emptyGrid = new Grid(true);
		grid = new Grid(true);
		for (int i = 0; i < 16; i++) {
			grid.getTiles()[i].setValue(11);
		}
		
		solver = GridSolver.getInstance();
		reset(logger);
	}

	private void initSolver(boolean isEmpty, Strategy strat) {
		if (isEmpty) {
			solver.setTestObjects(logger, strat, emptyGrid);
		} else {
			solver.setTestObjects(logger, strat, grid);
		}


	}

	@Test
	public void testRun() {
		initSolver(true, Strategy.HUMAN);
		solver.run();
		verify(logger).info(solver.getClass().getSimpleName(),
				"Solver cannot make any more moves.");

		initSolver(false, Strategy.HUMAN);
		solver.run();
		verify(logger, atLeastOnce()).debug(eq(solver.getClass().getSimpleName()),
				find("Direction selected: "));

		initSolver(false, Strategy.EXPECTIMAX);
		solver.run();
		verify(logger, atLeastOnce()).debug(eq(solver.getClass().getSimpleName()),
				find("Direction selected: "));
	}

	@Test
	public void testStart() {
		initSolver(false, Strategy.HUMAN);
		solver.start(grid);
		verify(logger).info(solver.getClass().getSimpleName(),
				"Starting solver...");
		assertTrue(solver.isRunning());
	}

	@Test
	public void testStopWhileRunning() {
		initSolver(false, Strategy.HUMAN);
		solver.start(grid);
		assertTrue(solver.isRunning());
		reset(logger);
		solver.stop();
		assertFalse(solver.isRunning());
		verify(logger).info(solver.getClass().getSimpleName(),
				"Solver stopped.");
	}
}
