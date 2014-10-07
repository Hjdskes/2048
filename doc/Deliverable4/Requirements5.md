# Requirements specification Sprint 5

The goal is to develop a multiplayer feature in our 2048 clone. These
requirements extend the other requirements defined in `doc/Requirements.pdf`.

All of the requirements have each been given a unique identifier followed by a
number:

  "I"  for interface requirements;
  "AI" for the solver requirements;
  "U"  for undo-function requirements;
  "R"  for redo-function requirements.

## Functional Requirements

### User Interface

I1: The user-interface should have an undo button that the player can click to use the undo function.

I2: Next to the undo button the interface should have an redo button that the player can click to use the redo function.

I3: The tiles in the grid should be animated during a move, visualizing the direction the tiles move in.

### Undo/redo

U1: With the undo button the user should be able to go multiple moves back.

R1: After a move is undone, the user should be able to go a move forward with the redo function.

R2: It should be possible to redo all of the undone moves untill a new move is made.

### Solver

AI1: The Ai should be able to win 50% of the singleplayer-games it plays.

AI2: The player should be able to choose in the multiplayer menu to play versus the computer instead of a different player. 

AI3: The AI should be able to look at the player's grid and add a tile to it that would hinder the player the most from winning.

AI4: The AI should be able to make an effective move for the player when the player asks for a hint.







 