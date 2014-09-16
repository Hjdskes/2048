# Requirements specification multiplayer game

The goal is to develop a multiplayer feature in our 2048 clone. These
requirements extend the other requirements defined in `doc/Requirements.pdf`.

All of the requirements have each been given a unique identifier followed by a
number:

  "I" for interface requirements;
  "N" for networking requirements;
  "O" for other requirements.

## Functional Requirements:

### Game rules:
#### Winning and losing the game.

* R01: The player wins when he is the first to reach a 2048 tile.
* R02: The game ends when one of the players has reached a 2048 tile.
* R03: When both players simultaneously get a 2048 tile, it is a draw.

### User Interface:

* I01: The game should start with a main menu, offering two buttons: one to
       launch a singleplayer game and another to launch a multiplayer game.
* I02: When a multiplayer game is launched, the user has two options: be
       connected to by someone else, or connect to someone else.
* I03: If the user is connecting to someone else, there should be an entry to
       enter the IP address to connect to.
* I04: If the user is connecting to someone else, after entering the IP address,
       there should be a dialog indicating the game is searching for connection.
* I05: If the user is being connected to, he should have the option to display
       his IP address.
* I06: If the user is being connected to, there should be a dialog indicating
       the game is searching for a connection.
* I07: If the game has ended, there should be a dialog indicating the player he
    won, lost or if it was a draw.
* I08: There should be no indication of a highscore.
* I09: There should be no indication of the current score.
* I10: There should be an indication of the time the game has been running.
* I11: There should be an indication of the highest current tile of the other
       player.

### Networking:

* N01: If a connection cannot be made, there should be a dialog indicating this.
* N02: If a connection cannot be made, the game should return to the main menu.
* N03: If a connection has been made, the game should start automatically.
* N04: When a connection drops mid-game, the game should return to the main
       menu.

### Other:

* O01: When the game ends, the player should return to the main menu.
