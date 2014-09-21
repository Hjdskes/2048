# Deliverable Two

In this document we will explain which requirements were not met and why.

## I01 and I02
These requirements are as follows:
* I01: The game should start with a main menu, offering two buttons: one to     
       launch a singleplayer game and another to launch a multiplayer game.     
* I02: When a multiplayer game is launched, the user has two options: be        
       connected to by someone else, or connect to someone else.                

Initially, this is what we implemented. It bugged us that the user needed to go
through three menus before finally being able to play a multiplayer game.
We also noticed that the main menu looked very empty with just two
buttons and a label. This made us decide that we would place the "Join a game"
and the "Host a game" buttons on the main menu and leave the multiplayer menu
out altogether.

## I08: There should be no indication of a highscore
In the multiplayer screen, we reuse the score tiles from the singleplayer
screen. Due to time constraints, we did not have the time to create a new class
for this to filter out the highscore tile. It does not bother us too much while
playing, so we decided that it was not going to be a priority to fix.

## I09: There should be no indication of the current score
Initially, we left the score tiles out altogether but this created a very empty
multiplayer screen. As such, we decided we would put our score tiles back into
the game. We are of the opinion that this looks better.

## I10: There should be an indication of the time the game has been running.
This is left out because in our opinion, it would clutter up the multiplayer
screen too much. Besides this, it is not a critical element to miss.
