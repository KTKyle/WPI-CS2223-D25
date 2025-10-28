import java.util.Arrays;

public class nQueens {

    /**
     * The function's purpose is to determine if a given setup of a board is valid for the n-Queens problem
     * @param board an array of integers with each index representing the position a queen is in the ith row
     * @param n the size of the array (we have an n x n board)
     * @return true if the board is set up in a valid way, false otherwise
     */
    public static boolean isLegalPosition(int[] board, int n){
        for(int i = 0; i < n; i++){
            if(board[i] == 0) continue;      // skips partial solutions
            if(board[i] < 0) return false;   // returns false if we have any negative values 
            for(int j = i + 1; j < n; j++){  
                if(board[j] == 0) continue;  // skips empty slots
                if(board[i] == board[j]) return false;   // checks columns
                if(Math.abs(board[i] - board[j]) == Math.abs(i - j)) return false;   // checks diagonals
            }
        }
        return true;
    }

    /**
     * The function's purpose is to check if the board is empty
     * @param board the board we are checking
     * @return true if the board is empty (all 0's), false otherwise
     */
    public static boolean isEmpty(int[] board){
        for(int val : board){
            if(val != 0) return false;
        }
        return true;
    }

    /**
     * The function's purpose is to check if the board is full
     * @param board the board we are checking
     * @return true if the board is full (all values > 0), false otherwise
     */
    public static boolean isFull(int[] board){
        for(int val : board){
            if(val <= 0) return false;
        }
        return true;
    }

    /**
     * The function's purpose is to generate a permutation for a potential board setup
     * @param board the initial board we are working with
     * @param n the size of the board (n x n)
     * @return a new board relative to the state the original board was in
     */
    public static int[] successor(int[] board, int n){
        int[] permutation = board;

        // find last non-zero position
        int lastNonZero = -1;
        for(int i = n - 1; i >= 0; i--){
            if(permutation[i] != 0){
                lastNonZero = i;
                break;   // stops when it has found the last queen placed on the board
            }
        }

        // case 1: the current board is not a legal board
        if(!isLegalPosition(permutation, n)){
            // starts from where the last queen was placed and finds a valid place to put it
            for(int j = lastNonZero; j >= 0; j--){
                if(permutation[j] < n){
                    permutation[j]++;   // move the queen in row j to the next column
                    for(int k = j + 1; k < n; k++){
                        permutation[k] = 0;   // resets positions after the current row to all 0s
                    }
                    return permutation;   // return new board configuration
                }
            }
        } 
        
        // case 2: the board is full AND in a legal position
        else if(isLegalPosition(permutation, n) && isFull(permutation)){
            // trys to find next permutation by incrememnting last queen's position
            for(int j = lastNonZero; j >= 0; j--){
                if(permutation[j] < n){
                    permutation[j]++;   // move the queen in row j to the next column
                    for (int k = j + 1; k < n; k++){
                        permutation[k] = 0;   // resets positions after the current row to all 0s
                    }
                    return permutation;   // return new board configuration
                }
            }
            return new int[n];   // // returns an empty board if no valid permutation found
        } 
        
        // case 3: we have a partial solution
        else {
            // checks if there is any place to add a new queen in the next row
            if(lastNonZero + 1 < n){
                permutation[lastNonZero + 1] = 1;   // places a queen in the next available row
                return permutation;   // return new board configuration
            }
        }

        // case 4: returns an empty board if no valid move is possible
        return new int[n];
    }

    /**
     * The function's purpose is to generate the next valid solution for an n x n queens problem
     * @param board the board we are finding the next legal position for
     * @param n the size of the board (n x n)
     * @return a new board configuration that is legal
     */
    public static int[] nextLegalPosition(int[] board, int n){
        int[] permutation = successor(board, n);

        // loops until we find a permutation that is legal
        while(!isEmpty(permutation)){
            if(isLegalPosition(permutation, n)){
                return permutation;   // return the first legal board setup we find
            }
            permutation = successor(permutation, n);   // generates another permutation we must look at
        }
        return permutation;
    }

    /**
     * The function's purpose is the find the first valid solution to an n-Queens problem of size n
     * @param n the size of the board (n x n)
     * @return the first valid board that is a solution given the size of the board
     */
    public static int[] findFirstSol(int n){
        int[] board = new int[n];
        board[0] = 1;   // starts a queen at the first row/column

        // generations new solutions until a valid one is found
        while(!isEmpty(board)){
            if (isLegalPosition(board, n) && isFull(board)){
                return board;   // returns first full valid solution 
            }
            board = nextLegalPosition(board, n);   // generates a new board to look at
        }
        return new int[0];   // returns an empty board if nothing is found
    }

    /**
     * The function's purpose is to calculate the total amount of possible solutions to an n-Queen(s) problem
     * @param n the size of the board (n x n)
     * @return the total number of solutions for this n-Queen(s) problem
     */
    public static int countAllSol(int n){
        int[] board = new int[n];
        board[0] = 1;   // starts a queen at first row/column
        int count = 0;  // initialize count

        // generates solutions until no more valid ones are left
        while (!isEmpty(board)) {
            if (isLegalPosition(board, n) && isFull(board)){
                count++;   // increment count every time a full valid board is found
            }
            board = nextLegalPosition(board, n);   // generate a new board to look at
        }
        return count;   // return total amount of valid solutions for this n-Queen(s) problem
    }
    
    public static void main(String[] args) throws Exception{
        int[] test = {1, 6, 8, 3, 7, 4, 2, 5};
        int[] test2 = {1, 6, 8, 3, 7, 0, 0, 0};
        int[] test3 = {1, 5, 5, 6, 3, 7, 2, 4};
        int[] test4 = {1, 6, 8, 3, -7, 0, 0, 0};

        System.out.println("Is array " + Arrays.toString(test) + " a valid board setup: " + isLegalPosition(test, test.length));
        System.out.println("Is array " + Arrays.toString(test2) + " a valid board setup: " + isLegalPosition(test2, test2.length));
        System.out.println("Is array " + Arrays.toString(test3) + " a valid board setup: " + isLegalPosition(test3, test3.length));
        System.out.println("Is array " + Arrays.toString(test4) + " a valid board setup: " + isLegalPosition(test4, test4.length));

        System.out.println();
        System.out.println("Next legal position from: " + Arrays.toString(test)+ " is " + Arrays.toString(nextLegalPosition(test, test.length)));
        System.out.println("Next legal position from: " + Arrays.toString(test2)+ " is "+ Arrays.toString(nextLegalPosition(test2, test2.length)));
        System.out.println("Next legal position from: " + Arrays.toString(test4)+ " is "+ Arrays.toString(nextLegalPosition(test4, test4.length)));

        System.out.println();
        for(int i = 4; i <= 20; i++){
            System.out.println("The first solution to a " + i + " x " + i + " board: " + Arrays.toString(findFirstSol(i)));
        }

        System.out.println();
        for(int i = 4; i <= 14; i++){
            System.out.println("Every possible solution in a " + i + " x " + i + " board: " + countAllSol(i));
        }

        System.out.println();
    }
}
