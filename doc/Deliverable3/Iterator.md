# The Iterator Design Pattern

This document describes why and how we implemented the iterator design pattern in our project.

## Why the iterator design pattern?

We implemented the iterator design pattern because we have to iterate over our
grid a lot. It seemed a natural choise to implement an iterator on top of this.
The prospect of having to refactor our TileHandler class was daunting, though,
so we never did it. This assignment, however, gave us the needed motivation to
go ahead and do it.

## How has it been implemented?

A new class, the TileIterator class, has been created. This class implements the
Iterator interface that is defined in java.util. The classes that have to
iterate over the grid now make use of this TileIterator.

One detail we would like to point out, is that our TileIterator also has a reset
method. This method will move the index back to the beginning of the array, so
the iterator can be used again. This allows us to have only one instance of
TileIterator inside the Grid class instead of every method creating an iterator
when needed. This would mean that several new TileIterators would be
instantiated sixty times per second! The same trick is used inside TileHandler,
although the impact here is less big.

