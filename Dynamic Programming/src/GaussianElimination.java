import java.util.Arrays;

public class GaussianElimination {

    /*
     * 1)
     *  Reasons why forwardElimination doesn't give us the correct answer for the resulting elimination:
     * The first step when subtracting row1 from rows 2 and 3 we are left with the matrix:
     * {1, 1, 1, 6},
     * {0, 0, 1, 3},
     * {0, 1, 2, 8}
     * Since our next pivot point at row 2 column 2 is 0 and our alorithm doens't account for pivoting we end up trying to 
     * divide by zero and get an error in our calculation (the current algorithm doesn't account for cases like this)
     * 
     * 2)
     * betterForwardElimination attempts to remedy the faults of forwardElimination by introducing partial pivoting.
     * Partial pivoting that looks for a row with the largest absolute value to the coefficient in the ith
     * column, exchange it with the ith row, and the nuse the new A[i][i] as the ith iteration's pivot point
     * 
     * The reason betterForwardElimination fails is because the rows are linearly dependent on one another.
     * After each elimination step, the second and third rows become very similar to one another. This 
     * leads us into a situation where the pivot element in the third row becomes near-zero (or just 0).
     * Due to this, our algorithm will fail to produce valid results in the third row and we get:
     * [0.0, NaN, NaN, NaN]
     * 
     * How to fix betterForwardElimination:
     * The algorithm needs to incorporate backward substitution. Backwards substitution is the final step when
     * solving a system of linear equations after the matrix has been morphed by the gaussian eliminations.
     * It essentailly takes the last row, which should only have one remaining variable, and computes the values
     * for each variable up the chain (ex row 2 depends on the answer from row 3, row 1 depends on the answer from row 2)
     * Doing this will provide us an accurate answer to the missing values in the third row which are currently all
     * 0s or NaNs 
     * 
     * NOTE: I know I didn't need to exactly implement the functions but I did so I could compare the test cases down below in main
     */


    /**
     * Applies Gaussian elimination to matrix A of a system’s coefficients, 
     * augmented with vector C of the system’s right-hand side values
     * @param A a given matrix 
     * @param B the column vector
     * @return An equivalent upper-triangular matrix in place of A with the
     * corresponding right-hand side values in the (n + 1)st column
     */
    public static double[][] forwardElimination(double[][] A, double[] B){
        int length = A.length;

        // appends B to A as the last column to complete matrix
        for(int i = 0; i < length; i++){
            A[i][length] = B[i];
        }

        // performs the gaussian elimination
        for(int i = 0; i < length - 1; i++){
            for(int j = i + 1; j < length; j++){
                for(int k = i; k <= length; k++){
                    A[j][k] = A[j][k] -  A[i][k] * A[j][i] / A[i][i]; 
                }
            }
        }
        return A;
    }

    /**
     * Implements Gaussian elimination with partial pivoting
     * @param A Matrix A[1..n, 1..n] 
     * @param B Column-vector B[1..n]
     * @return An equivalent upper-triangular matrix in place of A and the
     * corresponding right-hand side values in place of the (n + 1)st column
     */
     public static double[][] betterForwardElimination(double[][] A, double[] B){
        int length = A.length;

        // appends B to A as the last column to complete matrix
        for(int i = 0; i < length; i++){
            A[i][length] = B[i];
        }

        // performs the eliminations
        for(int i = 0; i < length - 1; i++){
            int pivotRow = i;

            for(int j = i + 1; j < length; j++){
                if(Math.abs(A[j][i]) > Math.abs(A[pivotRow][i])){
                    pivotRow = j;
                }
            }

            for(int k = i; k <= length; k++){
                A[i][k] = A[pivotRow][k];
            }

            for(int j = i + 1; j < length; j++){
                double temp = A[j][i] / A[i][i];
                for(int k = i; k <= length; k++){
                    A[j][k] = A[j][k] - A[i][k] * temp;
                }
            }
        }
        return A;
     }

     /**
      * The function computes the Gauss-Jordan Elimination
      * @param A the input matrix array
      * @param B the column array representing the constants in the equations that A is solving for
      * @return an array containing the solutions for the matrix
      */
     public static double[] gaussJordanElimination(double[][] A, double[] B){

        int length = A.length; // length of matrix
        // create the appended array that accounts for an n x n array
        double[][] fullMatrix = new double[length][length + 1];

        // creates the full matrix including column B
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                fullMatrix[i][j] = A[i][j];
            }
            fullMatrix[i][length] = B[i]; // add the last column to our matrix
        }

        // start Gaussian Elimination
        for(int i = 0; i < length; i++){
            // finds the row with the largest absolute value in the current column i to use as the pivot row
            int pivotRow = i;
            for(int j = i + 1; j < length; j++){
                if(Math.abs(fullMatrix[j][i]) > Math.abs(fullMatrix[pivotRow][i])){
                    pivotRow = j;
                }
            }
            
            // Swaps the current row i with the pivot row if they are different
            double[] temp = fullMatrix[i];
            fullMatrix[i] = fullMatrix[pivotRow];
            fullMatrix[pivotRow] = temp;

            // Divides all elements in the pivot row by the pivot element to make the pivot element equal to 1
            double pivot = fullMatrix[i][i];
            for (int j = 0; j <= length; j++) {
                fullMatrix[i][j] /= pivot;
            }

            // clear the other elements in the column by subtracting a multiple of the pivot row from each row
            for(int j = 0; j < length; j++){
                if(j != i){ // skips the pivot row
                    double equalize = fullMatrix[j][i]; // the multiple that we will remove from each row to equalize to zero
                    for(int k = 0; k <= length; k++){
                        fullMatrix[j][k] -= equalize * fullMatrix[i][k];
                    }
                }
            }
        }

        // Extracts the solution vector from the last column of the augmented matrix, which now contains the solutions after elimination
        double[] answer = new double [length];
        for(int i = 0; i < length; i++){
            answer[i] = fullMatrix[i][length];
        }
        return answer;
     }

    public static void main(String[] args) throws Exception {
        double[][] matrixExmaple = {
            {1, 1, 1, 0},
            {1, 1, 2, 0},
            {1, 2, 3, 0}
        };

        double[][] matrixExmaple2 = {
            {1, 1, 1, 0},
            {1, 1, 2, 0},
            {2, 2, 3, 0}
        };

        double[][] matrixExample3 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 4, -3, 0, 0, 0, 0, 0},
            {0, 0, 0, 3, -2, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 1, -1, 0, 0},
            {1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1},
        };

        
        double[] column = {6, 9, 14};
        double[] column2 = {6, 9, 15};
        double[] column3 = {364, 4, 16, 36, 64, 100, 79, 61, 0, 0, 0, -42};
        
        double[][] answer = forwardElimination(matrixExmaple, column);
        System.out.println("Naive Gauss Elimination (flawed): " + Arrays.deepToString(answer));

        double[][] answer2 = betterForwardElimination(matrixExmaple2, column2);
        System.out.println("Better Gauss Elimination (still flawed): " + Arrays.deepToString(answer2));

        double[] answer3 = gaussJordanElimination(matrixExample3, column3);
        System.out.println("Gauss Jordan Elimination: " + Arrays.toString(answer3));
    }
}
