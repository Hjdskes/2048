package nl.tudelft.ti2206.solver;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class Benchmark extends TimerTask {

	public enum Strategy {
		HUMAN, RANDOM
	}

	private static long initTime = System.currentTimeMillis();
	private static long startTime = System.currentTimeMillis();

	private static int wins = 0;
	private static int runs = 0;

	private Timer timer;

	private int delay;
	private int maxruns = 20;
	private static int succesfulMoves = 0;
	private Grid original;
	private Strategy strategy;
	private boolean running = false;

	public Benchmark(Grid grid, Strategy strategy, int delay, int maxruns) {

		timer = new Timer();
		running = false;
		runs = 0;

		setGrid(grid);
		setDelay(delay);
		setMaxruns(maxruns);
		setStrategy(strategy);
	}
	
	/////////////
	// setters
	/////////////
	
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

	///////////////
	// start/stop
	///////////////
	
	/** Benchmark start. */
	public void start() {

		print("Trying to solve grid automatically " + maxruns
				+ " times, making one move every " + delay + "ms...");

		initTime = System.currentTimeMillis();

		timer.schedule(this, 0, delay);
		
		running = true;
	}
	
	/** Stop benchmark. */
	public void stop() {
		timer.cancel();
		
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}

	public static void makeMove(Grid grid, Direction direction) {
		
		if (direction == null) {
			print("WARNING: direction == null!");
		}
		else if (grid.move(direction) != -1) {
			// print("selected move succesfully performed: " + direction);
			succesfulMoves += 1;
		}
		else
			print("selected move failed: " + direction);
	}
	
	public static void printGrid(Grid grid) {
		Tile[] tiles = grid.getTiles();

		String line = "Resulting grid: ";

		for (int i = 0; i < tiles.length; i += 1) {
			line += tiles[i].getValue() + " ";
		}
		print(line);
	}

	////////////////////////
	// statistics printing
	////////////////////////
	
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
		print("Accuracy: " + (wins * 100.0f) / runs + "%");
		print("Total time elapsed: " + seconds + " seconds");
		print("Average time per game: " + (seconds / runs) + " seconds");
		print("---------------------------------------------------");
	}

	@Override
	public void run() {

		// keep playing until we run out of moves
		if (original.getPossibleMoves() == 0) {
			runs += 1;

			if (original.getCurrentHighestTile() >= 2048) {
				// we ran out of moves but we still won!
				wins += 1;
				printStatsWon();
			} else {
				// we lost
				printStatsLost();
			}

			// restart grid and counter:
			original.restart();
			succesfulMoves = 0;
			startTime = System.currentTimeMillis();

		} else {

			Direction direction = null;

			if (strategy == Strategy.RANDOM) // Arthur's strategy
				direction = RandomSolver.selectMove(original);
			else if (strategy == Strategy.HUMAN)
				direction = HumanSolver.selectMove(original);

			// make the selected move
			makeMove(original, direction);
		}

		if (runs >= maxruns) {
			printStatsFinal();
			stop();
		}
	}
}
