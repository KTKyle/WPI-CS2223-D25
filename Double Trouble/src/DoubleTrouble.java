import java.util.Scanner;
import java.util.Random;

class nim {
    int greenMarkers;
    int yellowMarkers;
    int orangeMarkers;
    int compWins;
    int userWins;

    /**
     * Constructs a game of nim with default values including values for the total wins 
     * and losses the user and computer has if they decide to play in a tournament
     */
    public nim() {
        this.greenMarkers = 3;      // defualt value for the green markers
        this.yellowMarkers = 5;     // defualt value for the yellow markers
        this.orangeMarkers = 7;     // defualt value for the orange markers
        this.compWins = 0;          // default value for the amount of wins the computer has 
        this.userWins = 0;          // default value for the amount of wins the user has
    }

    /**
     * Resets the game state of nim to its default values
     * Used for tournaments 
     */
    public void resetGame() {
        this.greenMarkers = 3;
        this.yellowMarkers = 5;
        this.orangeMarkers = 7;
    }
}

class DoubleTrouble {

    /**
     * Simulates an individual game of Double Trouble (or Nim if you want to call it that)
     * @param game the nim object to help us manage the game by updating our markers
     */
    public static void play(nim game) {
        Scanner input = new Scanner(System.in);     // open the scanner to take in user inputs
        boolean userTurn = true;                    // default value of the user's turn
        Random computerMove = new Random();         // creates an object of the Random class used to generate a random move for the computer

        System.out.print("Who do you want to go first? (1 for you, 2 for computer): ");
        int userInput = input.nextInt();

        while (userInput != 1 && userInput != 2) {  // checks the case where the user inputs an invalid number
            System.out.println("Invalid option. Who do you want to go first? (1 for you, 2 for computer): ");
            userInput = input.nextInt();
        }
        if (userInput == 2) {   // if the user wanted the computer to go first
            userTurn = false;
        }

        while (game.greenMarkers > 0 || game.yellowMarkers > 0 || game.orangeMarkers > 0) {     // while we still have markers to remove
            System.out.println("Green markers left: " + game.greenMarkers + "\n" 
                             + "Yellow markers left: " + game.yellowMarkers + "\n" 
                             + "Orange markers left: " + game.orangeMarkers + "\n");
            if (userTurn) {
                userMove(input, game);
            } else {
                compMove(computerMove, game);
            }
            userTurn = !userTurn; // swaps the user's turn boolean so the computer can move or vice versa
        }

        if (userTurn) {      // if the user still has to move but there are no markers left to move then they have lost
            System.out.println("Computer won.\n");
            game.compWins++; // updates the computer's wins
        } else {             // if the computer has to move and there are no more markers left to move then the user has won
            System.out.println("You Win!\n");
            game.userWins++; // updates the user's wins
        }
    }

    /**
     * Simulates the user's move based on the color of the marker they want to remove and how many they want to remove
     * @param move object of the Scanner class that represents the user's input 
     * @param game object of the nim class to manage our game state by removing the markers the user wants with respect 
     * to the color they want to remove from
     */
    public static void userMove(Scanner move, nim game) {
        System.out.print("Which colors do you want to select (G/Y/O) and how many markers you want to move: ");
        String color = move.next();
        int markers = move.nextInt();

        if (isValidMove(color, markers, game)) {    // as long as the move they inputted is valid
            switch (color) {
                case "G":   // if the user picked green to remove from
                    game.greenMarkers -= markers;   // remove the amount of green markers they wanted
                    break;
                case "Y":   // if the user picked yellow to remove from
                    game.yellowMarkers -= markers;  // remove the amount of yellow markers they wanted
                    break;
                case "O":   // if the user picked orange to remove from
                    game.orangeMarkers -= markers;  // remove the amount of orange markers they wanted
                    break;
            }
        } else {            // if the user didn't pick a valid color
            System.out.println("You didn't input a valid move, try again.\n");
            userMove(move, game); // repeats their turn to try again
        }
    }

    /**
     * Simulates the computer's move with either a winning strategy if possible or removing random markers if no winning strategy is possible.
     * The computer calculates the Nim-sum of the marker piles to determine if a winning strategy exists:
     *
     *The Nim-sum is calculated by performing a bitwise XOR operation on the counts of markers in all piles.
     * Example if the machine starts first:
     *   - 3 in binary is 011
     *   - 5 in binary is 101
     *   - 7 in binary is 111
     *   Performing XOR on these values:
     *   3 ^ 5 = 011 ^ 101 = 110
     *   (3 ^ 5) ^ 7 = 110 ^ 111 = 001
     *   Thus, the Nim-sum is 1.
     * 
     *  If the Nim-sum is zero, the current player is in a losing position if the opponent plays optimally.
     *  If the Nim-sum is non-zero, the current player can force a win by making a move that results in a Nim-sum of zero for the opponent.
     * 
     * For the computer's move:
     *   - If the Nim-sum is non-zero, the computer calculates which pile to adjust to make the Nim-sum zero.
     *   - If the Nim-sum is zero, the computer makes a random valid move since no winning strategy exists.
     * 
     * @param randomMove an object of the Random class to help generate a random move the computer will make when 
     * no present winning strategy is possible
     * @param game object of the nim class to help remove markers the computer wants with respect to the 
     * color they want to remove from
     */
    public static void compMove(Random randomMove, nim game) {
        String[] colors = {"G", "Y", "O"};      // creates an array of possible colors to randomly choose from when the computer can' use the winning strategy
        String color = "";                      // the initial value of the string representation of the color the computer wants to remove markers from
        int removeMarkers = 0;                  // the initial value of the amount of markers the computer wants to remove
        int nimSum = game.greenMarkers ^ game.yellowMarkers ^ game.orangeMarkers; // the nimSum of the current gamestate

        if (nimSum != 0) {      // if the computer can apply its winning strategy
            if ((nimSum ^ game.greenMarkers) < game.greenMarkers) {                 // Check if modifying the green markers pile can make the Nim-sum zero
                color = "G";    // set the color we want to remove from to be green
                removeMarkers = game.greenMarkers - (nimSum ^ game.greenMarkers);   // the amount of markers we want to remove from the green pile given it is the correct winning move
            } 
            else if ((nimSum ^ game.yellowMarkers) < game.yellowMarkers) {          // Check if modifying the yellow markers pile can make the Nim-sum zero
                color = "Y";    // set the color we want to remove from to be yellow
                removeMarkers = game.yellowMarkers - (nimSum ^ game.yellowMarkers); // the amount of markers we want to remove from the yellow pile given it is the correct winning move
            } 
            else if ((nimSum ^ game.orangeMarkers) < game.orangeMarkers) {          // Check if modifying the orange markers pile can make the Nim-sum zero
                color = "O";    // set the color we want to remove from to be orange
                removeMarkers = game.orangeMarkers - (nimSum ^ game.orangeMarkers); // the amount of markers we want to remove from the orange pile given it is the correct winning move
            }
        } else {    // if no winning strategy is present
            while (!isValidMove(color, removeMarkers, game)) {     // keep randomizing the color we remove from and the amount of markers until it becomes a valid move
                color = colors[randomMove.nextInt(3)];      // the random color we want to remove from
                removeMarkers = randomMove.nextInt(7) + 1;  // the random amount of markers we want to remove
            }
        }

        switch (color) {
            case "G":
                game.greenMarkers -= removeMarkers;     // remove the amount of green markers the computer wants
                break;
            case "Y":
                game.yellowMarkers -= removeMarkers;    // remove the amount of yellow markers the computer wants
                break;
            case "O":
                game.orangeMarkers -= removeMarkers;    // remove the amount of orange markers the computer wants
                break;
        }
        System.out.println("Computer moved " + color + " " + removeMarkers + " times.\n");
    }

    /**
     * The function validates whether a move can be made based on the ruleset of the game
     * @param markerColor the string representing the color 
     * @param count the amount of markers we want to remove
     * @param game an object of the nim class
     * @return true if there are any markers within the color we want to remove and if we
     * are removing a valid amount of markers respective to the color, false otherwise
     */
    public static boolean isValidMove(String markerColor, int count, nim game) {
        switch (markerColor) {
            case "G":
                return count > 0 && count <= game.greenMarkers;     // if there are any green markers left and if the amount we want to remove is less than the amount left
            case "Y":
                return count > 0 && count <= game.yellowMarkers;    // if there are any yellow markers left and if the amount we want to remove is less than the amount left
            case "O":
                return count > 0 && count <= game.orangeMarkers;    // if there are any orange markers left and if the amount we want to remove is less than the amount left
            default:
                return false;
        }
    }

    /**
     * Simulates a tournament between the user and the computer
     * @param games the number of games needed to win the tournament
     * @param game an object of the nim class to help manage the gamestate
     */
    public static void tournamentFormat(int games, nim game) {
        int totalGames = 2 * games - 1;     // maximum number of games that can be played to decide a winner (i.e "best of # games" formatting)
        for (int i = 0; i < totalGames; i++) {
            if (game.compWins == games) {           // if the computer won the necessary amount of games to win the tournament
                System.out.println("The computer won the tournament.");
                break;
            } else if (game.userWins == games) {    // if the user won the necessary amount of games to win the tournament
                System.out.println("You won the tournament!");
                break;
            }

            System.out.println("Game #: " + (i + 1));
            System.out.println("Score - Computer wins: " + game.compWins + " - User wins: " + game.userWins);
            play(game);
            game.resetGame();
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // opens the scanner to take in user inputs
        nim game = new nim();       // created an instance of the nim class to manage our game state

        System.out.print("Would you like to play a tournament against the computer? (Y/N): ");
        String userInput = input.next();

        while (!userInput.equals("Y") && !userInput.equals("N")) {      // If the user's input wasn't a valid option
            System.out.print("Invalid input. Would you like to play a tournament against the computer? (Y/N): ");
            userInput = input.next();
        }

        if (userInput.equals("Y")) {                        // if the user wants to play a tournament
            System.out.print("Enter number of games to win: ");
            int bracketSize = input.nextInt();                      // the minimum amount of wins in the tournament

            while (bracketSize < 0) {   // if the user input an invalid number of minimum wins for the tournament
                System.out.print("Invalid input, need positive number. Enter number of games to win: ");
                bracketSize = input.nextInt();
            }
            tournamentFormat(bracketSize, game);
        } else {
            play(game);
        }
        input.close(); // closes the scanner
    }
}