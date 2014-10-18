package nl.tudelft.ti2206.gameobjects;

import java.util.Iterator;

/**
 * The TileIterator class is used to iterate over all the tiles in the Grid,
 * without having to care about the internal representation.
 */
public class TileIterator implements Iterator<Tile> {
	/** A reference to the array of Tiles. */
	private Tile[] tiles;

	/** The current position in the array. */
	private int index;

	/** Constructs a new TileIterator. */
	public TileIterator(Tile[] tiles) {
		this.tiles = tiles;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		return index < tiles.length;
	}

	@Override
	public Tile next() {
		return tiles[index++];
	}

	/**
	 * Resets the position to zero, so the iterator can be used again instead of
	 * having to create a new one.
	 */
	public void reset() {
		this.index = 0;
	}

	/**
	 * @return The current index of the iterator.
	 */
	public int getIndex() {
		return this.index;
	}
	
	/* Needed on Windows machines*/
	@Override
	public void remove() {		
	}	
}
