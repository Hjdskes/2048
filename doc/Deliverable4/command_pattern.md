#Command pattern

We implemented the command pattern because we wanted to undo and redo the movements made by the player. After deciding to implement the command pattern we also wanted to make commands for the movements, as every move was a clear command.

###Implementation 

We have decided to make the Command an abstract class as there where pieces of code that was used in every command so it would be beneficial to have that code in the Command class as a method.  The invoker of a command is the InputHandler. This object invokes the execute() method of the command that is currently set. 