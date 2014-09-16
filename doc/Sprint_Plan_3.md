#Sprint Plan #3

Game: 2048  
Group: 21

| User Story   | Task | Assigned to   | Estimated effort |
|--------------|------|---------------|------------------|
| Assignment 1 | 1.1  | Paul & Arthur | Easy      |
|              | 1.2  | Paul & Arthur | Easy      |
|              | 1.3  | Paul & Arthur | Easy      |
|              | 1.4  | Paul & Arthur | Easy      |
| Assignment 2 | 2.1  | Arthur        | Easy      |
|              | 2.2  | Arthur        | Easy      |
|              | 2.3  | Arthur & Paul | Difficult |
| User story 1 | 1.1  | Jente         | Medium    |
|              | 1.2  | Jente         | Medium    |
| User story 2 | 2.1  | Jochem        | Medium    |
|              | 2.2  | Jochem        | Medium    |
|              | 2.3  | Piet & Jente  | Difficult |
|              | 2.4  | Piet & Jente  | Difficult |
|              | 2.5  | Piet & Jente  | Difficult |
| User story 3 | 3.1  | Jochem        | Medium    |
|              | 3.2  | Jochem        | Medium    |
|              | 3.3  | Jochem        | Medium    |
| User story 4 | 4.1  | Piet & Jente  | Difficult |

## User stories

### User story 1: start the game
```
As a player,
 I want to start the game
 and choose between single- and multiplayer,
so that I can play how I want.

**Scenario 1.1: single player**
Given the user has launched the game,
When the user presses the singleplayer button,
Then the singleplayer game should start.

**Scenario 1.2: multiplayer**
Given the user has launched the game,
When the user presses the multiplayer button,
Then the multiplayer game should start.
```

###User story 2: start multiplayer
```
As a player,
 I want to play multiplayer,
so I can play versus my friend.

**Scenario 2.1: entering IP address**
Given the user wants to start a multiplayer game,
When connection is to be made,
Then the connecting user should enter the IP address of another user.
  and the game should try to connect.

**Scenario 2.2: listing IP addresses**
Given the user has started a multiplayer game,
When a connection is to be made,
Then the user being connected to should be able to see his IP address.

**Scenario 2.3: connecting**
Given the user wants to start a multiplayer game,
When the connection is to be made,
Then the user being connected to should see a "Connecting..." message,
  and the game should try to connect.

**Scenario 2.4: cannot connect**
Given the user has started a multiplayer game,
When a connection cannot be made,
Then the user should be notified
  and the game should return to the menu.

**Scenario 2.5: can connect**
Given the user has started a multiplayer game,
When a connection can be made,
Then the game should start.

```

###User story 3: playing multiplayer
```
As a player,
  I want to move my tiles around,
so I can get a 2048 tile.

**Scenario 3.1: player gets 2048**
Given the user is playing a multiplayer game,
When the user gets to a 2048 tile first,
Then the user has won
  and the user should be notified
  and the game should end
  and both players should return to the main menu.

**Scenario 3.2: opponent gets 2048**
Given the user is playing a multiplayer game,
When the opponent gets to a 2048 tile first,
Then the user has lost
  and the user should be notified
  and the game should end
  and both players should return to the main menu.

**Scenario 3.3: both players get 2048**
Given the user is playing a multiplayer game,
When the players both get to a 2048 tile simultaneously,
Then it is a draw
  and the user should be notified
  and the game should end
  and both players should return to the main menu.
```

###User story 4: connection drops
```
As a player,
  I want to stay connected
so I can play against my opponent.

**Scenario 4.1: connection drops**
Given the user is playing a multiplayer game,
When the connection drops,
Then the user should be notified
  and the game should end
  and both players should return to the main menu.
```
