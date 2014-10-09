package nl.tudelft.ti2206.solver;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class Benchmark extends TimerTask {

	public enum Strategy {
		HUMAN, RANDOM, ARTHUR
	}

	private static long initTime = System.currentTimeMillis();
	private static long startTime = System.currentTimeMillis();

	private static int wins = 0;
	private static int runs = 0;

	private static int succesfulMoves = 0;

	private Timer timer;

	private int delay = 20;
	private int maxruns = 20;

	private Grid original = null;
	private Strategy strategy = Strategy.RANDOM;
	private boolean running = false;
	private int depth = 0;
	private boolean printIntermediate = false;
	private int highestTileReached = 0;

	public Benchmark(Grid grid, Strategy strategy, int delay, int maxruns,
			int depth) {

		print("Initialising benchmark: " + maxruns + " games using strategy "
				+ strategy + ", making one move every " + delay + " ms.");

		running = false;
		runs = 0;
		highestTileReached = 0;

		setGrid(grid);
		setDelay(delay);
		setMaxruns(maxruns);
		setStrategy(strategy);
		setDepth(depth);
		setPrintIntermediate(false);
	}

	// ///////////
	// setters
	// ///////////

	private static void print(String str) {
		System.out.println("[AUTSOLVE]: " + str);
	}

	public void setGrid(Grid grid) {
		this.original = grid;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setMaxruns(int maxruns) {
		this.maxruns = maxruns;
	}

	private void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	private void setDepth(int depth) {
		this.depth = depth;
		print("Recursion depth set to " + depth);
	}

	private int getDepth() {
		return this.depth;
	}

	public boolean doPrintIntermediate() {
		return printIntermediate;
	}

	public void setPrintIntermediate(boolean printIntermediate) {
		this.printIntermediate = printIntermediate;
	}

	// /////////////
	// start/stop
	// /////////////

	/** Benchmark start. */
	public void start() {

		print("Benchmark started.");
		double estimation = (maxruns * (2 + (Math.pow(getDepth() + 1, 4)))) / 100;
		print("This run will take about " + estimation + " seconds...");

		initTime = System.currentTimeMillis();

		timer = new Timer();
		timer.schedule(this, 0, delay);

		running = true;
	}

	/** Stop benchmark. */
	public void stop() {

		if (isRunning()) {
			timer.cancel();
			timer.purge();

			timer = null;
			running = false;
			print("Benchmark terminated.");
		}
	}

	public boolean isRunning() {
		return running;
	}

	public static void makeMove(Grid grid, Direction direction) {

		if (direction == null) {
			print("WARNING: direction == null!");
		} else if (grid.move(direction) != -1) {
			// print("selected move succesfully performed: " + direction);
			succesfulMoves += 1;
		}
		// else
		// print("selected move failed: " + direction);
	}

	public static void printGrid(Grid grid) {
		Tile[] tiles = grid.getTiles();

		String line = "Resulting grid: ";

		for (int i = 0; i < tiles.length; i += 1) {
			line += tiles[i].getValue() + " ";
		}
		print(line);
	}

	// //////////////////////
	// statistics printing
	// //////////////////////

	private void printStatsWon() {
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;

		print("-- Statistics for game " + runs + " -- WON");
		print(runs + ": Game ended after " + seconds
				+ " seconds. Highest tile = "
				+ original.getCurrentHighestTile() + ". Succesful moves made: "
				+ succesfulMoves);
		print(runs + ": Game WON (" + wins + " out of " + runs
				+ " games were won = " + (wins * 100.0f) / runs + "%)");
		printGrid(original);
	}

	private void printStatsLost() {
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;

		print("-- Statistics for game " + runs + " -- LOST");
		print(runs + ": Game ended after " + seconds
				+ " seconds. Highest tile = "
				+ original.getCurrentHighestTile() + ". Succesful moves made: "
				+ succesfulMoves);
		print(runs + ": Game LOST (" + wins + " out of " + runs
				+ " games were won = " + (wins * 100.0f) / runs + "%)");
		printGrid(original);

	}

	private void printStatsFinal() {
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - initTime) / 1000;

		print("---------------------------------------------------");
		print("Benchmark complete!");
		print("Maximum amount of runs reached: " + maxruns);
		print("---------------------------------------------------");
		print("Final statistics: ");
		print("Games run: " + runs);
		print("Games won: " + wins + " (" + (runs - wins) + " lost)");
		print("Recursion depth: " + getDepth());
		print("Accuracy: " + (wins * 100.0f) / runs + "%");
		print("Total time elapsed: " + seconds + " seconds");
		print("Average time per game: " + (seconds / runs) + " seconds");
		print("Highest tile reached: " + highestTileReached);
		print("---------------------------------------------------");
	}

	@Override
	public void run() {

		// keep playing until we run out of moves
		if (original.getPossibleMoves() == 0) { // ||
												// original.getCurrentHighestTile()
												// >= 2048) {
			stop();
			runs += 1;
			highestTileReached = Math.max(highestTileReached,
					original.getCurrentHighestTile());

			if (original.getCurrentHighestTile() >= 2048) {
				// we ran out of moves but we still won!
				wins += 1;
				if (doPrintIntermediate())
					printStatsWon();
			} else {
				// we lost
				if (doPrintIntermediate())
					printStatsLost();
			}

			// restart grid and counter:
			original.restart();
			succesfulMoves = 0;
			startTime = System.currentTimeMillis();

		} else {

			Direction direction = null;

			if (strategy == Strategy.RANDOM || strategy == Strategy.ARTHUR) // Arthur's
																			// strategy
				direction = RandomSolver.selectMove(original);
			else if (strategy == Strategy.HUMAN)
				direction = HumanSolver.selectMove(original, getDepth());

			// make the selected move
			makeMove(original, direction);
		}

		if (runs >= maxruns) {
			printStatsFinal();
			// stop();

			print("Run is finished, increasing depth from " + getDepth()
					+ " to " + (getDepth() + 1) + " and restarting...");
			runs = 0;
			wins = 0;
			highestTileReached = 0;

			setDepth(getDepth() + 1);

			double estimation = (maxruns * (2 + (Math.pow(getDepth() + 1, 4)))) / 100;
			print("This run will take about " + estimation + " seconds...");

		}
	}
}
