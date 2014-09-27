package nl.tudelft.ti2206.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.TileHandler;
import nl.tudelft.ti2206.log.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * This class represents the 4x4 grid you see when playing 2048.
 * 
 * The internal structure is a simple one-dimensional array. Considering we only
 * require simple operations, this is deemed fast enough, while being very
 * simple at the same time.
 * 
 * For example, imagine the grid being laid out like this:
 * 
 * +---+---+---+---+ | 0 | 1 | 2 | 3 | +---+---+---+---+ | 4 | 5 | 6 | 7 |
 * +---+---+---+---+ | 8 | 9 | 10| 11| +---+---+---+---+ | 12| 13| 14| 15|
 * +---+---+---+---+
 * 
 * Now, a square on field 10 can move left or right by adding or subtracting 1
 * from its index. It can move up or down by adding or subtracting 4 from its
 * index.
 * 
 * The grid will draw all the Tiles it holds.
 */
public class Grid extends Actor {
	/** This enumeration is used to indicate the direction of a movement. */
	public enum Direction {
		DOWN, UP, LEFT, RIGHT;
	}

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * The name of the object Grid initialized on the name of the class. Used
	 * for logging.
	 */
	private String objectName = this.getClass().getSimpleName();

	/** The width of the Grid. */
	private static final int GRID_WIDTH = 400;

	/** The height of the Grid. */
	private static final int GRID_HEIGHT = 400;

	/** The base Grid x-coordinate. */
	private static final int GRID_X = 100;

	/** The base Grid y-coordinate. */
	private static final int GRID_Y = 100;

	/** The grid contains sixteen tiles. */
	private static final int NTILES = 16;

	/** The lowest value to start with. */
	private static final int TWO = 2;

	/** The highest value to start with. */
	private static final int FOUR = 4;

	/** The area of a Texture the Grid will use to draw itself. */
	private TextureRegion region;

	/** The array containing all sixteen tiles. */
	private Tile[] tiles;

	/** A randomizer is needed for filling tiles. */
	private Random random;

	/** The TileIterator is used to iterate over the tiles. */
	private TileIterator iterator;

	/** The TileHandler is used to move the tiles. */
	private TileHandler tileHandler;

	/** Keeps track of the highest Tile value in the current game. */
	private int highestTile;

	/** Keeps track of the current score. */
	private int score;

	/** Keeps track of the current high score. */
	private int highScore;

	/**
	 * Creates a new Grid with NTILES Tile objects.
	 * 
	 * @param isEmpty
	 *            True if the grid should be empty, false otherwise.
	 */
	public Grid(boolean isEmpty) {
		this.region = new TextureRegion(AssetHandler.getInstance().getSkin()
				.get("grid", Texture.class));
		this.random = new Random();
		this.tiles = new Tile[NTILES];
		this.iterator = new TileIterator(tiles);
		this.tileHandler = new TileHandler(tiles);

		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i, 0);
		}
		if (!isEmpty) {
			initGrid();
		}

		/* After loading the grid, start the game. */
		TwentyFourtyGame.setState(GameState.RUNNING);
	}

	/**
	 * Constructor for testing purposes: takes a Skin, a TextureRegion and a
	 * Tile as parameters to allow mocking.
	 */
	public Grid(boolean isEmpty, Skin skin, TextureRegion texture) {
		this.region = texture;
		this.random = new Random();
		this.tiles = new Tile[NTILES];
		this.iterator = new TileIterator(tiles);
		this.tileHandler = new TileHandler(tiles);

		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i, 0, skin, region);
		}
		if (!isEmpty) {
			initGrid();
		}

		/* After loading the grid, start the game. */
		TwentyFourtyGame.setState(GameState.RUNNING);
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
		int index = random.nextInt(tiles.length);
		while (!tiles[index].isEmpty() && getPossibleMoves() > 0) {
			index = random.nextInt(tiles.length);
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
		return random.nextInt(10) < 9 ? TWO : FOUR;
	}

	/**
	 * Sets a Tile's parameters by index.
	 * 
	 * @param index
	 *            The Tile's index on the grid.
	 * @param value
	 *            The Tile's value (should be a multiple of 2 or 0).
	 */
	public void setTile(int index, int value) {
		tiles[index].setValue(value);
	}

	/**
	 * Updates the grid, by updating all the Tiles it contains and checking for
	 * a new highest value.
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		while (iterator.hasNext()) {
			iterator.next().act(delta);
		}
		iterator.reset();

		updateHighestTile();
		if (score > highScore) {
			highScore = score;
		}
	}

	/**
	 * Resets the grid, by calling reset on all the Tiles it contains,
	 * reinitializing itself and checking for the new highest value.
	 */
	public void restart() {
		logger.info(objectName, "Restarting grid.");

		score = 0;
		while (iterator.hasNext()) {
			iterator.next().reset();
		}
		iterator.reset();
		initGrid();

		TwentyFourtyGame.setState(GameState.RUNNING);
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
	 * @return
	 */
	public void move(Direction direction) {
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

			int newScore = score + tileHandler.getScoreIncrement();
			setScore(newScore);
			logger.info(objectName, "Score value set to " + newScore + ".");

			int location = getRandomEmptyLocation();
			int value = initialValue();
			setTile(location, value);
			tiles[location].spawn();

			logger.debug(objectName, "New tile set at location " + location
					+ " (value = " + value + ").");
		} else {
			logger.debug(objectName, "Move " + direction + " ignored.");
		}

		tileHandler.reset();
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

	/**
	 * @return The array containing all the Tiles.
	 */
	public Tile[] getTiles() {
		return tiles;
	}

	/**
	 * @return The highest Tile value.
	 */
	public int getCurrentHighestTile() {
		return highestTile;
	}

	/**
	 * @return The current score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return The current high score. Is not necessarily higher than the saved
	 *         high score. This is checked when saving the game.
	 */
	public int getHighscore() {
		return highScore;
	}

	/**
	 * @return The TileHandler object used by the Grid.
	 */
	public TileHandler getTileHandler() {
		return tileHandler;
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
	 * Sets the highest tile to the value provided.
	 * 
	 * @param highest
	 *            The new highest tile.
	 */
	public void setHighestTile(int highest) {
		this.highestTile = highest;
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
	 * Sets the current high score to the value provided.
	 * 
	 * @param highScore
	 *            The new high score.
	 */
	public void setHighscore(int highScore) {
		this.highScore = highScore;
	}

	@Override
	public float getX() {
		return GRID_X;
	}

	@Override
	public float getY() {
		return GRID_Y;
	}

	@Override
	public float getWidth() {
		return GRID_WIDTH;
	}

	@Override
	public float getHeight() {
		return GRID_HEIGHT;
	}

	/**
	 * Sets the name of the object
	 * 
	 * @param name
	 */
	public void setObjectName(String name) {
		this.objectName = name;
	}

	/**
	 * Gets the name of the object.
	 * 
	 * @return name
	 */
	public String getObjectName() {
		return objectName;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		while (iterator.hasNext()) {
			iterator.next().draw(batch, parentAlpha);
		}
		iterator.reset();
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
}
