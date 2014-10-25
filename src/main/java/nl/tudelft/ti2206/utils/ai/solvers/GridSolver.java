package nl.tudelft.ti2206.utils.ai.solvers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.utils.handlers.PreferenceHandler;
import nl.tudelft.ti2206.utils.log.Logger;

/**
 * The GridSolver starts a AI session that will attempt to solve the Grid it is
 * called on.
 */
public class GridSolver implements Runnable {

	/** An enumeration indicating which solver will be used. */
	public enum Strategy {
		HUMAN, EXPECTIMAX
	}

	/** Get current class name, used for logging output. */
	private static final String CLASSNAME = GridSolver.class.getSimpleName();

	/** The singleton reference to this class. */
	private static GridSolver instance = new GridSolver();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** The scheduler and its future tasks used to make moves with fixed delay. */
	private ScheduledExecutorService timer;
	private ScheduledFuture<?> future;

	/** The delay between consecutive task executions. */
	private int delay;

	/** The original Grid to calculate for and make a move on. */
	private Grid original;

	/** An indication on whether this solver is running. */
	private boolean running;

	/** The strategy to be used by the GridSolver. */
	private Strategy strategy;

	/**
	 * The recursion depth to use. Depending on the used solver, this may be
	 * ignored.
	 */
	private int depth;

	/** The solver used to solve the Grid. */
	private Solver solver;

	/** Constructs a new GridSolver. */
	private GridSolver() {
		this.delay = PreferenceHandler.getInstance().getSolverDelay();
		strategy = PreferenceHandler.getInstance().getSolverStrategy();
		depth = 6;

		timer = Executors.newScheduledThreadPool(1);
		running = false;

		if (strategy == Strategy.HUMAN) {
			this.solver = HumanSolver.getInstance();
		} else if (strategy == Strategy.EXPECTIMAX) {
			this.solver = Expectimax.getInstance();
		}
	}

	/** @return The Singleton instance of this class. */
	public static GridSolver getInstance() {
		return instance;
	}

	/** Starts the scheduler. */
	public void start(Grid grid) {
		original = grid;
		if (!isRunning()) {
			logger.info(CLASSNAME, "Starting solver...");
			future = timer.scheduleWithFixedDelay(this, 0, delay,
					TimeUnit.MILLISECONDS);
			running = true;
		}
	}

	/** Stop the scheduler. */
	public void stop() {
		if (isRunning()) {
			future.cancel(true);
			running = false;
			logger.info(CLASSNAME, "Solver stopped.");
		}
	}

	/**
	 * @return True iff this solver is running, false otherwise.
	 */
	public boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		if (original.getPossibleMoves() == 0) {
			logger.info(CLASSNAME, "Solver cannot make any more moves.");
			stop();
		} else {
			Direction direction = solver.findMove(original, depth);
			logger.debug(CLASSNAME, "Direction selected: " + direction);
			if (direction != null) {
				original.move(direction);
			}
		}
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	/** Setter for testing purposes only */
	public void setTestObjects(Logger logger, Strategy strategy, Grid grid) {
		GridSolver.logger = logger;
		this.strategy = strategy;
		this.original = grid;
	}
}
