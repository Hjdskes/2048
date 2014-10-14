# Requirements specification Sprint 6

The goal is to develop a solver algorithm for the 2048 game and to clean up our code, including updating our javadoc.  

All of the requirements have each been given a unique identifier followed by a
number: 

* "AI" for the solver requirements;
* "NF" for non-functional requirements.

## Functional Requirements

Solver requirements are a leftover from last sprint, where we implemented another solver. This solver differs internally from the other in the sense that it uses a common algorithm, the expectimax algorithm, to solve the game.

### Solver

AI1: The AI should be able to win at least 50% of the singleplayer-games it plays.

AI2: The AI should be able to look at the player's grid and add a tile to it that would hinder the player the most from winning.

## Non-Functional Requirements

NF1: Javadoc should be updated to match with the functionality of the methods and parameters.
