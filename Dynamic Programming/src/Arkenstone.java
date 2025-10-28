public class Arkenstone {

    /**
     * The function finds the path in a given matrix that yields the highest value using a dynamic programming approach
     * @param treasureRoom the matrix that simulates the treasure room Biblo traverses to find the Arkenstone
     * @return an array containing the path Bilbo took to yield the most treasure and the subsequent Arkenstone
     */
    public static int[] mostPreciousPath(int[][] treasureRoom){
        int row = treasureRoom.length;          // the number of rows in the treasueRoom matrix
        int col = treasureRoom[0].length;       // the number of columns in the treasureRoom matrix

        int[][] gemstones = new int[row][col];  // a 2D array that keeps track of the maximum amount of gemstones collected in each cell
        int[][] currColumn = new int[row][col]; // a 2D array that tracks the column index of the previous cell in the path

        // initializes the first row of gemstones with the first row of treasureRoom
        for(int i = 0; i < col; i++){
            gemstones[0][i] = treasureRoom[0][i];
        }

        // Iterates through each cell starting from the second row (we already initialized the first row)
        for(int i = 1; i < row; i++){
            for(int j = 0; j < col; j++){
                int gemVal = gemstones[i - 1][j];   // stores the maximum gemstones collected from the cell above
                currColumn[i][j] = j;              // points to the column index of the cell above

                // checks the left diagonal (rules above explain why)
                if(j > 0 && gemstones[i - 1][j - 1] > gemVal){
                    gemVal = gemstones[i - 1][j - 1];   // if the left diagonal has more gemstones then hold it in gemVal
                    currColumn[i][j] = j - 1;          // points to the column index of the cell in the top left of the current cell
                }

                // checks the right diagonal (rules above explain why)
                if(j < col - 1 && gemstones[i - 1][j + 1] > gemVal){
                    gemVal = gemstones[i - 1][j + 1];   // if the right diagonal has more gemstones then hold it in gemVal
                    currColumn[i][j] = j + 1;          // points to the column index of the cell in the top right of the current cell
                }

                // updates the current cell with the maximum gemstones collected so far including the current cell
                gemstones[i][j] = gemVal + treasureRoom[i][j]; 
            }
        }

        // finds the column in the last row of gemstones matrix where the maximum amount of gemstones were collected
        int countMaxGem = 0;  // the maximum amount of gemstones found in the cell
        int colTracker = 0;   // column index of the cell
        for(int i = 0; i < col; i++){
            // checks the value in the current column to see if it has the current max amount of gems
            if(gemstones[row - 1][i] > countMaxGem){
                countMaxGem = gemstones[row - 1][i];  // updates new maximum
                colTracker = i;   // updates the column index
            }
        }

        // initialize the array that contains the path Bilbo took (our answer)
        int[] preciousPath = new int[row];
        // the column index of hte last row of the gemstones matrix where the maximum amount was found (starting point for our backtracking)
        int currentColumn = colTracker; 

        // backtracks though Bilbo's path in reverse order to find the sequential path he took
        for(int currRow = row - 1; currRow >= 0; currRow--){
            preciousPath[currRow] = currentColumn + 1;  // offsets computer 0th counting to 1 based counting (used for printing out the correct number column later on)
            currentColumn = currColumn[currRow][currentColumn]; // updates the column index to keep backtracking correctly
        }


        // relevant information pertaining to Bilbo's journey through the treasure room
        System.out.println("Bilbo's starting square: " + preciousPath[row - 1]);
        System.out.print("Path he took: ");
        for (int step = preciousPath.length - 1; step >= 0; step-- ){
            System.out.print(preciousPath[step] + " ");
        }
        System.out.println("\nTotal gemstones collected: " + countMaxGem);
        System.out.println("Vault containing the Arkenstone: Vault " + preciousPath[0]);
        return preciousPath;
    }

    public static void main(String[] args) {
        int[][] treasureRoom = {
            {1000, 900, 800, 0, 1000000, 0, 600, 1001},
            {1000, 900, 800, 0, 0, 0, 600, 1001},
            {1000, 900, 800, 0, 0, 0, 600, 1001},
            {1000, 900, 800, 0, 0, 0, 600, 1001},
            {1000, 900, 800, 0, 0, 0, 600, 1001},
            {1000, 900, 800, 0, 0, 0, 600, 1001},
            {1000, 900, 800, 0, 0, 0, 600, 1001},
            {1000, 900, 800, 0, 0, 0, 600, 1001}
        };

        int[][] treasureRoom2 = {
            {35, 89, 52, 66, 82, 20, 95, 21},
            {79, 5, 14, 23, 78, 37, 40, 74},
            {32, 59, 17, 25, 31, 4, 16, 63},
            {91, 11, 77, 48, 13, 71, 92, 15},
            {56, 70, 47, 64, 22, 88, 67, 12},
            {83, 97, 94, 27, 65, 51, 30, 7},
            {10, 41, 1, 86, 46, 24, 53, 93},
            {96, 33, 44, 98, 75, 68, 99, 84}
        };

        mostPreciousPath(treasureRoom);
        System.out.println();
        mostPreciousPath(treasureRoom2);
    }

}
