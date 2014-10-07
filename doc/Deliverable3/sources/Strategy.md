# Design Pattern: Strategy

## Why the Strategy Design Pattern?

We use a Screen hierarchy to define some of the GUI aspects that we use in the game. The parent, Screen, has a draw-method among other methods that are extended
to the child classes. These child classes would use one of the two implementations of the draw-method: the implementation that was extended from the parent or
by override into a different implementation. Because of this, the classes that override the existing method would have duplicate code.
By applying the Strategy design pattern to our existing hierarchy we can not only reduce duplicate code but also prepare our program to further changes.
This has been done by encapsulating the different implementations in a family of algorithms. Every class can now add one of the objects to use an implementation
dynamically (even in runtime) instead of implementing it themselves. As the program grows and a new set of screens with a different implementation
of the draw-method is added, we can simply add a new class to the family where we define the implementation. Without forcing any changes on the existing classes.


## How have we implemented the Pattern?

The pattern has been implemented by moving the existing implementations of the draw-method into separated classes that implement the DrawBehavior interface.
The interface contains a draw method that the implementing classes override with their implementation. Then we added a DrawBehavior local variable to the Screen class and
a method to assign a new DrawBehavior object to it. Now a DrawBehavior object can be assigned to every screen object to determine its draw-implementation. 
