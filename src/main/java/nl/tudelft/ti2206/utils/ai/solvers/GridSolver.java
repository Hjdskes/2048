package nl.tudelft.ti2206.utils.ai.solvers;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.utils.handlers.PreferenceHandler;
import nl.tudelft.ti2206.utils.log.Logger;

/**
 * The GridSolver starts a AI session that will attempt to solve the Grid it is
 * called on.
 */
public class GridSolver extends TimerTask {
	/** An enumeration indicating which solver will be used. */
	public enum Strategy {
		HUMAN, EXPECTIMAX
	}

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private static final String CLASSNAME = GridSolver.class.getSimpleName();

	/** The timer used to schedule this task. */
	private Timer timer;
	/** The delay between consecutive task executions. */
	private int delay = 20;

	/** The original Grid to calculate for and make a move on. */
	private Grid original;
	/** An indication on whether this solver is running. */
	private boolean running;
	/**
	 * The recursion depth to use. Depending on the used solver, this may be
	 * ignored.
	 */
	private int depth;
	/** The solver used to solve the Grid. */
	private Solver solver;

	/** Constructs a new GridSolver. */
	public GridSolver(Grid grid, int delay, int depth) {
		logger.debug(CLASSNAME, "Initializing GridSolver...");

		this.running = false;
		this.original = grid;
		this.delay = PreferenceHandler.getInstance().getSolverDelay();
		Strategy strategy = PreferenceHandler.getInstance().getSolverStrategy();
		this.depth = depth;

		if (strategy == Strategy.HUMAN) {
			this.solver = new HumanSolver();
		} else if (strategy == Strategy.EXPECTIMAX) {
			this.solver = new Expectimax();
		}
	}

	/** Starts the TimerTask. */
	public void start() {
		if (!isRunning()) {
			logger.info(CLASSNAME, "Starting solver...");

			if (timer == null) {
				timer = new Timer();
				timer.schedule(this, 0, delay);
			}
			running = true;
		}
	}

	/** Stop the TimerTask. */
	public void stop() {
		if (isRunning()) {
			timer.cancel();
			timer.purge();
			timer = null;
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

	/** Setter for testing purposes only */
	public void setLogger(Logger logger) {
		GridSolver.logger = logger;
	}
}
