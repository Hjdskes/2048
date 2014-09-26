package nl.tudelft.ti2206.gameobjects;

import java.util.Iterator;

public class TileIterator implements Iterator<Tile> {
	private Tile[] tiles;
	private int index;

	public TileIterator(Tile[] tiles) {
		this.tiles = tiles;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		/*
		 * Not sure if tiles can ever be null since we instantiate them all in
		 * Grid, but better safe than sorry...
		 */
		if (index < tiles.length
				&& tiles[index] != null) {
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
	 * @return The current index of the iterator.
	 */
	public int getIndex() {
		return this.index;
	}
}
