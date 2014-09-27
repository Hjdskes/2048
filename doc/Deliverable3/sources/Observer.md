# The Observer Design Pattern

This document describes why and how we implemented the observer design pattern in our project.


## Why Observers?

The Networking class required to communicate with the opponent's grid in the game, for movements and for synchronization of the grid.

In previous releases of 2048, we would give a handle to the RemoteInputHandler object to the Networking class. This way, however, attaches RemoteInputHandler entirely to Networking.

To circumvent this in our new version, the RemoteInputHandler registers itself to Networking as an Observer so that the Observable (Networking) can notify the RemoteInputHandler whenever it needs to be updated.

## How have they been implemented?

The RemoteInputHandler class simply gets a singleton instance of the Networking class. 

	private static Networking networking = Networking.getInstance();

Then, in its constructor, it calls the addObserver method on the networking object and registers itself as a observer.

	networking.addObserver(this);

The Networking class can now send objects to the RemoteInputHandler without being fully attached to eachother. Whenever Networking needs to send an object to RemoteInputHandler it can simply set a flag to a changed state, using:

	setChanged();

and then simply notify all observers that were registered in the past:

	notifyObservers(response);

The benifits of this design are that Networking can communicate with other objects while not being fully attached to them and newly added objects can easily register to the list of observers, allowing the application to be maintained and extended in an easier way.