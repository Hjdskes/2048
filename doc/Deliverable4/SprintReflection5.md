#Reflection on Sprint #5

Game: 2048  
Group: 21

| User Story	| Task 							| Assigned to 		  | Estimated Effort 										| Actual Effort               				|Done | Notes |
|:-------------:|:-----------------------------:|:-------------------:|:-------------------------------------------------------:|:-----------------------------------------:|:---:|:-----:|
| Story 1 		| Exercise 1 					| Jente, Jochem, Piet | 20 hours (Jente & Jochem), 10 hours (Piet) (very hard)  | 40 (Jente & Jochem), 20 (Piet) (very hard)|Semi | Note 1|
| Story 2 		| Exercise 1 					| Piet 				  | 12 hours (hard) 										| 12 (hard)									| Yes | 	  |
| Story 3 & 4 	| Exercise 1 					| Arthur 			  | 8 hours (medium) 									   | 12 (medium)							   | Yes |		 |
| N/A 			| Exercise 2 Command Pattern 	| Paul 				  | 6 hours (medium) 									   | 6 (medium)								   | Yes |       |
| N/A 			| Exercise 2 State Pattern 		| Arthur 			  | 6 hours (medium) 									   | 12 (medium)							   | Yes |		 |
| N/A 			| Exercise 3 MVC Pattern 		| Jente, Piet 		  | 12 hours each (hard) 									  | 6 (medium)								  | Yes |		|

## Note 1
We have decided to implement multiple solvers. One is trying to mimic the human strategy and another uses the expectimax algorithm. The human-like solver is finished and wins about 30% of its games. The other solver is not finished and therefore has not been benchmarked yet. It is used however to provide hints to the player in the singleplayer game. The human-like solver is used to solve the games and to play against.

## User Stories
Below the user stories for this sprint are defined.

### Story 1
As a user, I want to be able to let the game be solved for me when I am stuck or when I want to see how it's done.

### Story 2
As a user, I want to see how tiles are moving in order to gain a better understanding of the game.

### Story 3
As a user, I want to be able to undo a move that I am not happy with.

### Story 4
As a user, I want to have the option to redo the move I made after I have undone it.


## Main Problems encountered

***Description***  
The non-human-like solver turned out to be very complicated to make. We started
by implementing the minimax algorithm, but this turned out to be too slow on a
decent recursion depth. We tried to circumvent this by converting minimax to
alpha-beta, but againt this did not work. After more research, we discovered the
expectimax algorithm and implemented it accordingly. The speed is now decent
(with a hashmap of previously evaluated grids) and it works properly until it
gets to a 1024 and a bit more. We will continue to look into this in the next
sprint.

***Reaction***  
We kept trying and continued doing research until we were out of time.

