package nl.tudelft.ti2206.utils.ai;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.utils.log.Logger;

public class GridSolver extends TimerTask {
	public enum Strategy {
		HUMAN
	}

	/** Get current class name, used for logging output. */
	private static final String CLASSNAME = GridSolver.class.getSimpleName();

	private Timer timer;
	private int delay = 20;

	private Grid original;
	private Strategy strategy;
	private boolean running;
	private int depth;

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	public GridSolver(Grid grid, Strategy strategy, int delay, int depth) {
		logger.debug(CLASSNAME, "Initializing GridSolver...");

		this.running = false;
		this.original = grid;
		this.delay = delay;
		this.strategy = strategy;
		this.depth = depth;
	}

	/** Benchmark start. */
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

	/** Stop benchmark. */
	public void stop() {
		if (isRunning()) {
			timer.cancel();
			timer.purge();
			timer = null;
			running = false;
			logger.info(CLASSNAME, "Solver stopped.");
		}
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		// keep playing until we run out of moves
		if (original.getPossibleMoves() == 0) {
			logger.info(CLASSNAME, "Solver cannot make any more moves.");
			stop();
		} else {
			Direction direction = null;
			if (strategy == Strategy.HUMAN) {
				direction = HumanSolver.selectMove(original, depth);
			}

			logger.debug(CLASSNAME, "Direction selected: " + direction);
			// make the selected move
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
