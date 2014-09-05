#Requirements

General requirements:
* The game should be able to be both started and restarted by the user.
* The game ends when the player loses.
* The game can be won.
* The game is won when two tiles of value 1024 merge into a single 2048 tile.
* A score should be kept of the progress of the game. Merging two tiles adds the value of the merged tile to the score.

User interface requirements:
* The interface should display a board to the user with a 4x4 grid on it.
* There should be a replay button.
* Tiles of different values should have different colors.
* There should be an indication of the current score, the highscore and the highest tile ever reached.

Game rules:
* Only tiles of the same value can be merged.
* Two tiles of the same value are merged into one single tile when they collide. The value of the resulting tile will be the sum of the two tiles.
* Two tiles cannot merge into one if one of them has already been merged this move.
* Every move, all tiles should move into that direction, for the greatest distance possible.
* Initially, the grid should contain only two tiles with either value 2 or 4.
* The player can move around tiles on the grid using the four arrow keys on the keyboard.
* Moves can go into the upper, lower, left and right direction.
* Tiles can not go off-grid
* Moves are only valid when adjacent squares in the direction of the movement have the same value, or are empty.
* After every valid move a new tile should appear, with a value of either 2 or 4.
* The chance of a new tile appearing with the value 2 is significantly higher than the value 4.
* When no more moves are possible before the game is won, the game is lost. 
* After winning the game, the user can decide to keep playing or restart the game.
* After the user has won the game and keeps playing, the user can only increase the highscore, and is thus playing in endless mode.
* In endless mode, the player can still lose the game for the above mentioned criteria.