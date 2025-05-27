## Goals of the project
1. Develop Minimax and Alpha-Beta-Pruning algorithms.
2. Develop Kalah Game and its various Evaluation Function.
3. Use the game to provide an analysis btw the two algorithms
4. Provide statistics between the two algorithms in terms of the following :-
   * No of Nodes Expanded
   * No of Nodes Generated
   * No of Memory Occupied
   * Time Taken for the execution
   * Win/Lose Statistics: This gives the count of wins/loose when re-run with same first player or different first player. This will be covered as part of Result Analysis. And not by the Program.

## Input of the project
1. User to choose her First Player, Second Player and Evaluation Function that he/she would like to use.
2. Initial Board State (Pits and Kalahs)
3. Cutoff-Depth
   
## Output of the project
1. Shows which Player has won
2. Provides the Statistics of both the players.
   
## KALAH GAME
Kalah is a 2-player board game (which will look like something below taken from Internet).
![image](https://github.com/user-attachments/assets/bd4581b0-8c22-4984-8728-df286d1dc507)

## Description of the board
* There are 6 holes/pits on each side of the board (as shown in the above figure)
* There will be 6 stones(or prices) in each of them.
* The holes/pits closest to them is theirs.
* The pits on the left and the right side of the board, each belongs to a Player and can be called as Player A’s Kalaha and Player B’s Kalaha.
  
## How the game is played
* Initial set up - We will have a total of 72 stones (or pieces) and these 72 stones will be divided equally into all of those (6+6) pits – which means that each of the 12 pits will have 6 stones in them.
* A player is chosen to go first. (say Player A)
* Player A grabs all the stones residing in one of the pits. (there are no rules regarding the which pit he chooses to grab the stones from.)
* A goes counter-clockwise, he places each of the 6 stones in the following pits :
    * His own pits
    * His Kalah
    * Opponents pits but not the opponents Kalah
* Each player keeps taking turns to play this way.
* If the last stone falls in your Kalah, then you get to play once again
* If the last stones falls in your empty cup, you get to pick all the stones present in the directly opposite pit of the opponents player. Pick the stones and place them in your Kalah.
* When all the pits of a player is empty, that ends the game. The stones still remaining in the other player’s pit can be added to his own Kalah.
