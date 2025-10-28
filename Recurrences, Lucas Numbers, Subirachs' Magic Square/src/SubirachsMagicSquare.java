public class SubirachsMagicSquare{
    // Instantiates Subirach's Magic SUBIRACH_SQUARE
    static int[][] SUBIRACH_SQUARE = {
        {1, 14, 14, 4},
        {11, 7, 6, 9},
        {8, 10, 10, 5},
        {13, 2, 3, 15}
    };

    static int NUM_VALS = 16;      // total amount of values in Subirach's Magic Square

    /**
    * The function extracts all elements from a 2D array into a 1D array
    * @param SUBIRACH_SQUARE 2D array representing Subirach's Magic Square
    * @return a 1D array containing all elements of SUBIRACH_SQUARE
    */
    public static int[] extractElements(int[][] SUBIRACH_SQUARE) {
        int[] elements = new int[NUM_VALS];
        int index = 0;
        for (int[] row : SUBIRACH_SQUARE) {
            for (int cell : row) {
                elements[index++] = cell;
            }
        }
        return elements;
    }

    /**
    * The function counts the subsets of an array that satisfy a specific condition and sum
    * @param array an array of integers to process
    * @param index current index in the array
    * @param targetSum target sum to find
    * @param currSum current sum of the subset
    * @param count current number of elements in the subset
    * @param condition the condition to check (1: less than 4 elements, 2: exactly 4 elements, 3: more than 4 elements)
    * @return the number of subsets that satisfy the condition and sum
    */
    public static int countSubsets(int[] array, int index, int targetSum, int currSum, int count, int condition) {
        // if we've reached the end of the array
        if (index >= array.length) {
            // if the current subset satisfies the condition and target sum
            switch (condition) {
                case 1: // less than 4 elements
                    if(count < 4 && currSum == targetSum){
                        return 1;
                    } 
                    else return 0;
                case 2: // exactly 4 elements
                    if(count == 4 && currSum == targetSum){
                        return 1;
                    }
                    else return 0;
                case 3: // more than 4 elements
                    if(count > 4 && currSum == targetSum){
                        return 1;
                    }
                    else return 0;
                default:
                    return 0;
            }
        }

        // Include the current element
        int include = countSubsets(array, index + 1, targetSum, currSum + array[index], count + 1, condition);
        // Exclude the current element
        int exclude = countSubsets(array, index + 1, targetSum, currSum, count, condition);

        return include + exclude;
    }

    /**
    * The function calculates the maximum possible sum of all elements in an array
    * @param array array of integers
    * @return the maximum possible sum of the array
    */
    public static int calculateMaxSum(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    /**
    * The function counts all possible sums that can be formed from subsets of an array
    * @param array an array of integers
    * @param index current index in the array
    * @param currSum current sum of the subset
    * @param totals an array to store the count of each possible sum
    */
    public static void countAllSums(int[] array, int index, int currSum, int[] totals) {
        if (index == array.length) {
            totals[currSum]++;
            return;
        }

        // Include the current element
        countAllSums(array, index + 1, currSum + array[index], totals);
        // Exclude the current element
        countAllSums(array, index + 1, currSum, totals);
    }

    /**
    * The function finds the sum with the greatest number of combinations from an array of totals
    * @param totals an array where each index represents a sum and the value represents the number of combinations
    * @return the sum with the greatest number of combinations
    */
    public static int findMaxCombos(int[] totals){
        int maxCombinations = 0;
        int sumWithMaxCombinations = 0;
        for (int i = 0; i < totals.length; i++) {
           if (totals[i] > maxCombinations) {
              maxCombinations = totals[i];
              sumWithMaxCombinations = i;
            }
        }
        return sumWithMaxCombinations;
    }

    public static void main(String[] args) {
        int[] elements = extractElements(SUBIRACH_SQUARE);
        int targetSum = 33;        // the "magic number" in subirach's magic square
    
        int lessThan4 = countSubsets(elements, 0, targetSum, 0, 0, 1);  // counts the subsets of the magic square that add up to the target sum with less than 4 elements
        int exactly4 = countSubsets(elements, 0, targetSum, 0, 0, 2);   // counts the subsets of the magic square that add up to the target sum with exactly 4 elements
        int moreThan4 = countSubsets(elements, 0, targetSum, 0, 0, 3);  // counts the subsets of the magic square that add up to the target sum with more than 4 elements
    
        // Prints the results for each variation of subsets
        System.out.println("Subsets with less than 4 elements: " + lessThan4 + " subsets");
        System.out.println("Subsets with exactly 4 elements: " + exactly4 + " subsets");
        System.out.println("Subsets with more than 4 elements: " + moreThan4 + " subsets");
    
        // Total number of ways every possible sum can be formed
        int maxSum = calculateMaxSum(elements);
        int[] totals = new int[maxSum + 1];
        countAllSums(elements, 0, 0, totals);
    
        // Print the number of ways each sum can be formed
        System.out.println("\nNumber of ways each sum can be formed:");
        for (int i = 0; i < totals.length; i++) {
            if (totals[i] > 0) {
                System.out.println("Sum " + i + ": " + totals[i] + " combinations");
            }
        }
    
        // Finds the sum with the most number of combinations
        int maxSumCombo = findMaxCombos(totals);
        int maxCombinations = totals[maxSumCombo];
    
        // Print the sum with the greatest number of combinations that can be formed
        System.out.println("\nThe sum with the greatest number of ways to be formed is " + maxSumCombo + " with " + maxCombinations + " combinations.");
    }
}