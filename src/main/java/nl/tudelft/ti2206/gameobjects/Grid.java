package nl.tudelft.ti2206.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.utils.handlers.TileHandler;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.states.RunningState;

/**
 * This class represents the 4x4 grid you see when playing 2048.
 * 
 * The internal structure is a simple one-dimensional array. Considering we only
 * require simple operations, this is deemed fast enough, while being very
 * simple at the same time.
 * 
 * For example, imagine the grid being laid out like this:
 * 
 * +---+---+---+---+
 * | 0 | 1 | 2 | 3 |
 * +---+---+---+---+
 * | 4 | 5 | 6 | 7 |
 * +---+---+---+---+
 * | 8 | 9 | 10| 11| 
 * +---+---+---+---+
 * | 12| 13| 14| 15|
 * +---+---+---+---+
 * 
 * Now, a square on field 10 can move left or right by adding or subtracting 1
 * from its index. It can move up or down by adding or subtracting 4 from its
 * index.
 */
public class Grid extends Observable implements Cloneable {
	/** This enumeration is used to indicate the direction of a movement. */
	public enum Direction {
		DOWN, UP, LEFT, RIGHT;
	}

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** The name of the instance, initialized to the name of the class. */
	private String objectName = this.getClass().getSimpleName();

	/** The grid contains sixteen tiles. */
	public static final int NTILES = 16;

	/** The lowest value to start with. */
	public static final int TWO = 1;

	/** The highest value to start with. */
	public static final int FOUR = 2;

	/** The array containing all sixteen tiles. */
	private Tile[] tiles;

	/** The TileIterator is used to iterate over the tiles. */
	private TileIterator iterator;

	/** The TileHandler is used to move the tiles. */
	private TileHandler tileHandler;

	/** The current score of the Grid. */
	private int score;

	/** The highest tile value present in the Grid. */
	private int highestTile;
	
	private Stack<String> undo;
	
	private Stack<String> redo;

	/**
	 * Creates a new Grid with NTILES Tile objects.
	 * 
	 * @param isEmpty
	 *            True if the grid should be empty, false otherwise.
	 */
	public Grid(boolean isEmpty) {
		this.tiles = new Tile[NTILES];
		this.iterator = new TileIterator(tiles);
		this.tileHandler = new TileHandler(this);
		this.undo = new Stack<String>();
		this.redo = new Stack<String>();

		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i, 0);
		}
		if (!isEmpty) {
			initGrid();
		}
	}

	/**
	 * Initializes the grid, creating two tiles with a value of 2 or 4 and
	 * setting the rest empty.
	 */
	private void initGrid() {
		int loc1 = getRandomEmptyLocation();
		int loc2 = getRandomEmptyLocation();
		while (loc2 == loc1) {
			loc2 = getRandomEmptyLocation();
		}
		tiles[loc1].setValue(initialValue());
		tiles[loc2].setValue(initialValue());
	}

	/**
	 * Returns a random value, smaller than 16, indicating a location for a new
	 * Tile. This new location is always valid, i.e. there is not already an
	 * Tile there.
	 * 
	 * @return A new valid location.
	 */
	private int getRandomEmptyLocation() {
		int index = (int)(Math.random() * tiles.length);
		while (!tiles[index].isEmpty() && getPossibleMoves() > 0) {
			index = (int)(Math.random() * tiles.length);
		}
		return index;
	}

	/**
	 * Returns a random value, which is either 2 or 4. The chances of getting 4
	 * is significantly lower than the change of getting 2.
	 * 
	 * @return A random value, being either 2 or 4.
	 */
	private int initialValue() {
		return Math.random() < 0.9 ? TWO : FOUR;
	}

	/**
	 * Sets a Tile's parameters by index.
	 * 
	 * @param index
	 *            The Tile's index on the grid.
	 * @param value
	 *            The Tile's value (should be a value bigger than zero)).
	 */
	public void setTile(int index, int value) {
		tiles[index].setValue(value);
		changed();
		updateHighestTile();
	}

	/**
	 * Sets this Grid's tiles to the provided array.
	 * @param tiles The tiles to set.
	 */
	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
		this.iterator = new TileIterator(tiles);
		updateHighestTile();
		changed();
	}
	
	/**
	 * Resets the grid, by calling reset on all the Tiles it contains,
	 * reinitializing itself and checking for the new highest value.
	 */
	public void restart() {
		logger.info(objectName, "Restarting grid.");

		while (iterator.hasNext()) {
			iterator.next().reset();
		}
		iterator.reset();
		initGrid();

		/*if (highestTile > PreferenceHandler.getInstance().getHighestTile()) {
			PreferenceHandler.getInstance().setHighest(highestTile);
			ScoreDisplay.updateAllTimeHighestTile();
		}*/

		score = 0;
		highestTile = 0;
		updateHighestTile();
		undo.clear();
		redo.clear();

		TwentyFourtyGame.setState(RunningState.getInstance());
		changed();
	}

	/**
	 * This method is the one method used for moving tiles.
	 * 
	 * Its parameter shall indicate which direction is to be moved in. The
	 * actual moving will be delegated to TileHandler. If a move has been made,
	 * it will update the score and create a new Tile.
	 * 
	 * @param direction
	 *            The direction in which is to be moved.
	 * @return The increment in score of this move, or -1 if no move was made.
	 */
	public int move(Direction direction) {
		int increment = -1;

		switch (direction) {
		case LEFT:
			tileHandler.moveLeft();
			break;
		case RIGHT:
			tileHandler.moveRight();
			break;
		case UP:
			tileHandler.moveUp();
			break;
		case DOWN:
			tileHandler.moveDown();
			break;
		default:
			break;
		}

		if (tileHandler.isMoveMade()) {
			logger.info(objectName, "Move " + direction + " succesfully made.");

			increment = tileHandler.getScoreIncrement();
			updateScore(increment);
			spawnNewTile();
			updateHighestTile();
			changed();
		} else {
			logger.debug(objectName, "Move " + direction + " ignored.");
		}

		tileHandler.reset();
		return increment;
	}

	/**
	 * Updates the score with the score increment from the TileHandler class.
	 */
	public void updateScore(int increment) {
		int newScore = score + increment;
		setScore(newScore);

		logger.info(objectName, "Score value set to " + newScore + ".");
	}

	/**
	 * Gets a new random empty location and spawn a new tile there.
	 */
	public void spawnNewTile() {
		int location = getRandomEmptyLocation();
		int value = initialValue();
		setTile(location, value);
		tiles[location].spawn();

		logger.debug(objectName, "New tile set at location " + location
				+ " (value = " + value + ").");
	}

	/**
	 * This method is called after the TileHandler has been given a direction.
	 */
	public void updateMove() {
		if (tileHandler.isMoveMade()) {
			this.updateScore(tileHandler.getScoreIncrement());
			this.spawnNewTile();
		}
	}

	/**
	 * Updates the highest Tile value present in the grid.
	 */
	public void updateHighestTile() {
		Tile t = null;
		while (iterator.hasNext()) {
			t = iterator.next();
			if (t.getValue() > highestTile) {
				highestTile = t.getValue();
			}
		}
		iterator.reset();
	}

	/**
	 * @return The amount of possible moves.
	 */
	public int getPossibleMoves() {
		TileIterator iterator = new TileIterator(tiles);
		Tile t = null;
		int moves = 0, value = 0;

		while (iterator.hasNext()) {
			t = iterator.next();
			/* An empty Tile cannot move. */
			if (!t.isEmpty()) {
				/* Get current Tile value. */
				value = t.getValue();
				/* Get all Tile's neighbors. */
				List<Tile> neighbors = getTileNeighbors(iterator.getIndex() - 1);

				/* For all neighboring tiles, compare the values. */
				for (Tile neighbor : neighbors) {
					if (neighbor.getValue() == value
							|| neighbor.getValue() == 0) {
						moves++;
					}
				}
			}
		}
		iterator.reset();

		return moves;
	}

	/**
	 * Get a list of neighboring Tiles by index.
	 * 
	 * @param index
	 *            The Tile index.
	 * @return A list of neighboring tiles.
	 */
	public List<Tile> getTileNeighbors(int index) {
		List<Tile> neighbors = new ArrayList<Tile>();

		/*
		 * Right neighbor: check if the index we're checking is not the right
		 * edge of the grid by making sure (index + 1) is a not a multiple of 4.
		 */
		if ((index + 1) % 4 != 0 && index + 1 < tiles.length) {
			neighbors.add(tiles[index + 1]);
		}

		/*
		 * Left neighbor: check if the index we're checking is not the left edge
		 * of the grid by making sure (index - 1) is a not a multiple of 4.
		 */
		if (index % 4 != 0 && index - 1 >= 0) {
			neighbors.add(tiles[index - 1]);
		}

		/*
		 * Lower neighbor: check if the index we're checking is not the bottom
		 * edge of the grid by making sure (index + 4) is smaller than
		 * grid.length:
		 */
		if (index + 4 < tiles.length) {
			neighbors.add(tiles[index + 4]);
		}

		/*
		 * Upper neighbor: check if the index we're checking is not the top edge
		 * of the grid by making sure (index - 4) is not smaller than zero:
		 */
		if (index - 4 >= 0) {
			neighbors.add(tiles[index - 4]);
		}

		return neighbors;
	}

	private void changed() {
		if (!hasChanged()) {
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * @return The current score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return The highest Tile value.
	 */
	public int getCurrentHighestTile() {
		return highestTile;
	}

	/**
	 * @return The array containing all the Tiles.
	 */
	public Tile[] getTiles() {
		return tiles;
	}

	/**
	 * @return The TileHandler object used by the Grid.
	 */
	public TileHandler getTileHandler() {
		return tileHandler;
	}

	/**
	 * @return The name of this instance.
	 */
	public String getObjectName() {
		return objectName;
	}

	public Stack<String> getUndoStack() {
		return undo;
	}
		
	public Stack<String> getRedoStack() {
		return redo; 
	}

	/**
	 * Sets the TileHandler object used by the grid.
	 * 
	 * @param tileHandler
	 *            The TileHandler object to set.
	 */
	public void setTileHandler(TileHandler tileHandler) {
		this.tileHandler = tileHandler;
	}

	/**
	 * Sets the current score to the value provided.
	 * 
	 * @param score
	 *            The new score.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Sets the name of this instance.
	 * 
	 * @param name
	 *            The name for this instance.
	 */
	public void setObjectName(String name) {
		this.objectName = name;
	}

	@Override
	public String toString() {
		String res = "";

		while (iterator.hasNext()) {
			res += iterator.next().getValue() + ",";
		}
		iterator.reset();

		res = res.substring(0, res.length() - 1);
		return res;
	}
	
	@Override
	public Grid clone() {
		Grid newGrid = new Grid(true);

		for (int i = 0; i < tiles.length; i++) {
			newGrid.tiles[i] = new Tile(i, tiles[i].getValue());
		}
		newGrid.setScore(score);
		newGrid.highestTile = highestTile;
		return newGrid;
	}
}