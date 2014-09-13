
Requirements specification:
The purpose is to develop a fully working 2048 clone. Here it
will be explained what is defined under "fully working", together with other
requirements taken from the requirements analysis:

>All of the requirements have been given each a unique identifier: "R" for game rules, "I" for interface requirements, "NF" for non-functional requirements
>and the letter "O" for other requirements, followed by a number.

#Game rules:


## R1: Grid 

* R1.1: The game is played on a 4x4 grid. Every cell in the grid can only hold a single Tile. Because of this every tile has a unique position on the grid. 
* R1.2: Tiles are moved within a 4x4 grid and cannot be moved beyond this grid.
* R1.3: Initially, the grid should contain only two tiles with either value 2 or 4. After every valid move a new tile should appear, with a value of either 2 or 4.
* R1.4: The chance of a new tile appearing with the value 2 is 90%, in contrast to a chance of 10% on a value of 4.



## R2: Tile movement on the grid.

* R2.1: Moves can go into the upper, lower, left and right direction. Moves are only valid when adjacent squares in the direction of the movement have the same value, or are empty.
* R2.2: Making a move will send the tiles in that direction on the grid as far as possible. 
* R2.3: A move affects every tile on the grid pushing all of the tiles in the direction pressed.
* R2.4: Since every cell in the grid can only hold one tile and cannot be moved beyond the grid, the tiles that are moved to the same spot collide.
* R2.5: The player can move around tiles on the grid using the four arrow keys on the keyboard.


## R3: Tile collisions.

* R3.1: Every tile on the board has a value.
* R3.2: When two tiles of the same value collide with each other they will merge together into a tile that is the added value of both.
* R3.3: On the contrary when a tile collides with a tile of a different value, it will not merge and will not move on or beyond that tile. 
* R3.4: Tiles that have already merged once in the current move, cannot merge again until the next turn. 
* R3.5: Merging two tiles adds the value of the merged tile to the score and setting the highest tile to the new tile if it is higher than the current highest tile.


## R4: Winning and losing the game.

* R4.1: The game is won when two tiles of the value 1024 merge into a single 2048 tile.  
* R4.2: When no more moves are possible before the game is won, the game is lost.
* R4.3: After winning the game, the user can decide to keep playing with the current grid or restart the game.
* R4.4: If the player decides to continue after merging a 2048 tile he will proceed with the current grid. Able to merge tiles higher than 2048.
* R4.5: After continuing the player can "endlessly" increase his score until he runs out of legal moves. At that point the player loses the game.
 

# As for the user interface, the following requirements have been made:
* I1: The interface should display a board to the user with a 4x4 grid on it and the tiles that it holds. 
* I2: There should be an indication of the current score, the highscore and the highest tile ever reached.
* I3: A replay button that gives the player the possibility of restarting the game.
* I4: The tiles should show the value that they hold having a distinct colour compared to tiles of other value.
* I5: When the game is won, a dialog will pop up telling the player he/she won and asking whether or not to continue.
* I5: When a tile spawns, it should do so with a "jojo" animation.
* I6: When two tiles merge, there should be a bounce animation.

# Other functional requirements are:
* O1: The game should be able to be both started and restarted by the user.
* O2: After closing a game, upon restart, the game should start where it left of before being closed.

# Non-functional requirements are:
* NF1: The game should run on a desktop computer, regardless of Operating System.
* NF2: The use of Java 1.8.
* NF3: The use of Maven.
* NF4: The use of Git as a tool for revision control.
* NF5: The use of DevHub to make sure the software runs in a clean environment.
* NF6: A working version before the deadline on saturday the 14th of september 23:55 CEST.


