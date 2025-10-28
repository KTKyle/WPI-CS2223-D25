This program simulates a game of "Double Trouble" between the user and the computer

The game consists of:
    - Three green markers
    - Five yellow markers
    - Seven orange markers 

The two players will take turns removing as many markers of a single color per turns
The player who removes the last marker, regardless of quantity or color, wins the game 

Double Trouble is a variation of a very old mathematical combinitorial game called Nim
The origins of Nim are still debated as some believe it came from China as it closely resembles
the Chinese game of jian-shizi. However others argue that the game originated from Europe.
The current name of "Nim" was coined by Charles L. Bouton from Harvard University who is 
touted as the founder of the complete theory of the game in 1901. In terms of cultural relevance,
Nim has appeared in the 1939 New York World's Fair on a machine where people could play against 
the machine, it has also appeared at the Festival of Britain in 1951 with a nim-playing computer was present, 
and it was also featured in a science article called Scientific American in 1958.

The DoubleTrouble class is made up of 6 distinct functions
    Main: Asks the user a series of questions to determine:
        - whether or not the user wants to play in a tournament or a single game against the computer
        - if the user wants to do a tournament, what is the minimum amount of wins they want to play for 
        - if the user doesn't want to play a tournament, will simulate one singular game 

    play: Simulates an isolated instance of the game Double Trouble (or Nim if you want to call it that)

    userMove: Simulates the move the user wants to make on their given turns
        - removes the amount of markers the user wants in accordance to the color they want to remove markers from 
        - updates the total pile of markers 

    compMove: Simulates the move the computer wants to make based on if it has a winning strategy or not
        - if the computer does have a winning strategy, makes moves to keep the nim-sum above 0
        - if the computer does not have a winning strategy, makes random moves
    
    isValidMove: Validates if a given move is valid or not returning true or false respectively

    tournamentFormat: Simulates a tournament between the user and the computer tracking wins

The nim class is made up of 1 distinct functions 
    resetGame: resets the valeus for each individual marker to their default state 

    Also contains a default constructor for the values of the markers for each color and wins for both user and computer

This program was written by Kyle Truong with the aid of:
    - https://en.wikipedia.org/wiki/Nim#Game_play_and_illustration: for the information about the origins of Double Trouble
    - https://stackoverflow.com/questions: general issues 
