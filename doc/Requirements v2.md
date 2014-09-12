
Requirements specification:
The purpose is to develop a fully working 2048 clone. Here it
will be explained what is defined under "fully working", together with other
requirements taken from the requirements analysis:

>All of the requirements have been given each a unique identifier: "R" for game rules, "I" for interface requirements, "NF" for non-functional requirements
>and the letter "O" for other requirements, followed by a number.

Game rules (equal, high priority):
* R1:  The game is won when two tiles of the value 1024 merge into a single 2048 tile.
* R2:  A score should be kept of the progress of the game. Merging two tiles adds the value of the merged tile to the score.
* R3:  Only tiles of the same value can be merged. #If tiles of different values collide they will not merge but be placed as far as possible in the direction of the move that is made.
* R4:  Two tiles of the same value are merged into one single tile when they collide. The value of the resulting tile will be the sum of the two tiles.
* R5:  Two tiles cannot merge into one if one of them has already been merged this move.
* R6:  Every move, all tiles should move into that direction, for the greatest distance possible.
* R7:  Initially, the grid should contain only two tiles with either value 2 or 4.
* R8:  The player can move around tiles on the grid using the four arrow keys on the keyboard.
* R9:  Moves can go into the upper, lower, left and right direction.
* R10: Tiles can not go off-grid.
* R11: Moves are only valid when adjacent squares in the direction of the movement have the same value, or are empty.
* R12: After every valid move a new tile should appear, with a value of either 2 or 4.
* R13: The chance of a new tile appearing with the value 2 is 90%, in contrast to a chance of 10% on a value of 4.
* R14: When no more moves are possible before the game is won, the game is lost. 
* R15: The game ends when the player loses.
* R16: After winning the game, the user can decide to keep playing or restart the game.
* R17: After the user has won the game and keeps playing, the user can only increase the highscore, and is thus playing in endless mode.
* R18: In endless mode, the player can still lose the game for the above mentioned criteria.

As for the user interface, these requirements have been made (sorted from high
to low priority):
* I1: The interface should display a board to the user with a 4x4 grid on it.
* I2: There should be an indication of the current score, the highscore and the highest tile ever reached.
* I3: There should be a replay button.
* I4: Tiles of different values should have different colors.
* I5: When the game is won, a dialog will pop up telling the player he/she won and asking whether or not to continue.
* I6: When moving tiles, there should be a visual indication of this movement.
* I7: When a tile spawns, it should do so with a "jojo" animation.
* I8: When two tiles merge, there should be a bounce animation

Other functional requirements are:
* O1: The game should be able to be both started and restarted by the user.
* O2: After closing a game, upon restart, the game should start where it left of before being closed.

Non-functional requirements are:
* NF1: The game should run on a regular desktop computer, regardless of Operating System.
#* NF2: The game should run on a modern computer.
* NF3: The use of Java 1.7.
* NF4: The use of Maven.
* NF5: The use of Git.
* NF6: The use of DevHub to make sure the software runs in a clean environment.
* NF7: A working version before the deadline on saturday the 14th of september 23:59 CEST.
