package nl.tudelft.ti2206.gameobjects;

import java.util.Iterator;

/**
 * The TileIterator class is used to iterate over all the tiles in the Grid,
 * without having to care about the internal representation.
 */
public class TileIterator implements Iterator<Tile> {
	/** A reference to the collection of Tiles. */
	private Tile[] tiles;

	/** The current position in the collection. */
	private int index;

	/** Constructs a new TileIterator. */
	public TileIterator(Tile[] tiles) {
		this.tiles = tiles;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		/*
		 * Not sure if tiles can ever be null since we instantiate them all in
		 * Grid, but rather safe than sorry...
		 */
		if (index < tiles.length && tiles[index] != null) {
			return true;
		}
		return false;
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
	 * Get the current position index of the iterator.
	 * @return the current index of the iterator
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Apparently on Windows we have to implement this method, but we don't use
	 * it so we leave it empty.
	 */
	@Override
	public void remove() {

	}
}
