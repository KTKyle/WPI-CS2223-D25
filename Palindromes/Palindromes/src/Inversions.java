import java.util.Scanner;

public class Inversions {
    /**
     * The function does a basic naive sorting algorithm to count the amount of inversions in a given array
     * An inversion is found: i < j but A[i] > A[j]
     * @param array an array of integers
     * @return the amount of inversions present in the given array
     * Big O: O(n^2)
     */
    public static int easyinversioncheck(int[] array){
        int count = 0;  // int representing the amount of inversions in the array

        // Iterate through each pair of elements in the array
        for(int i = 0; i < array.length; i++){
            for(int j = i + 1; j < array.length; j++){
                // If the current pair is an inversion
                if(array[i] > array[j]){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * The function does a more complex but faster sorting algorithm to count the amount of inversions in a given array
     * using an implementation of merge sort
     * @param array an array of integers
     * @return the amount of inversions present in a given array
     * Big O: O(nlog(n))
     */
    public static int fastinversioncheck(int[] array){
        int length = array.length;

        // Base case
        if(length <= 1 ){
            return 0;
        }

        int midpoint = length / 2;  // midpoint of the array
        int[] leftArray = new int[midpoint];  // subarray for the left half of the array
        int[] rightArray = new int[length - midpoint];  // subarray for the right half of the array
        
        // First half of the original array
        for(int i = 0; i < midpoint; i++){
            leftArray[i] = array[i];
        }

        // Second half of the original array
        for(int j = midpoint; j < length; j++){
            rightArray[j - midpoint] = array[j];
        }

        // Recursively count inversions in left and right arrays, and merge them
        int leftInversions = fastinversioncheck(leftArray);
        int rightInversions = fastinversioncheck(rightArray);

        // Count the inversions when merging the left and right half back together
        int mergedInversions = mergeAndCount(array, leftArray, rightArray);

        return leftInversions + rightInversions + mergedInversions;
    }

    /**
     * This is a helper function for the fastinversioncheck function that counts the inversions for each merged array 
     * @param array the merged/combined array
     * @param left  the left array
     * @param right the right array
     * @return the number of inversions when merging the left and right array back
     */
    public static int mergeAndCount(int[] array, int[] left, int[] right){
        int i = 0;  // left array index
        int j = 0;  // right array index
        int k = 0;  // merged array index
        int inversionCount = 0; // number of inversions we have

        // Merging the two arrays and counding the number of inversions
        while(i < left.length && j < right.length){
            // If the current element in the left array is smaller than the current elements in the right array, no inversion
            if(left[i] <= right[j]){
                array[k] = left[i];
                i++;
            } else {
                // If the current element in the right array is smaller it is an inversion
                array[k] = right[j];
                j++;
                inversionCount += left.length - i;
            }
            k++;
        }

        // Copy any remaining elements from the left array
        while(i < left.length){
            array[k] = left[i];
            i++;
            k++;
        }

        // Copy any remaining elements from the right array
        while(j < right.length){
            array[k] = right[j];
            j++;
            k++;
        }

        return inversionCount;
    }

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        // Tests for inversions using simple O(n^2) method including I/O from user
        // Default size of array
        int arrLength = 8;     

        // Prompt user if they want a custom array length or not
        System.out.print("Would you like to input a custom amount of numbers for inversion checks (Y/N): ");
        String customLen = userInput.next();

        // Case to check if the user didn't input a correct value for a custom length
        while(!customLen.equals("Y") && !customLen.equals("N")){
            System.out.print("Error. Input valid answer (Y/N):");
            customLen = userInput.next();
        }

        // If user chooses custom length, prompt for the size of that custom array
        if(customLen.equals("Y")){
            System.out.print("Enter a custom length of the array: ");
            arrLength = userInput.nextInt();
        }
        
        // Initialize array with respect to the default size or user inputted size
        int[] userArray = new int[arrLength];

        // Take individual inputs for elements in array
        System.out.println("Enter the elements of the array: ");
        for (int i = 0; i < arrLength; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            userArray[i] = userInput.nextInt();
        }

        // Test for user inputted array
        long start = System.nanoTime();
        System.out.println("\nInversions in user inputted array (easyinversioncheck): " + easyinversioncheck(userArray));
        long end = System.nanoTime();
        System.out.println("Time in nanoseconds for easy inversion check: " + (end - start));

        long startFast = System.nanoTime();
        System.out.println("\nInversions in user inputted array (fastinversioncheck): " + fastinversioncheck(userArray));
        long endFast = System.nanoTime();
        System.out.println("Time in nanoseconds for fast inversion check: " + (endFast - startFast));

        // Tests for prebuilt arrays
        int[] testInversion = {3,2,1};
        int[] testInversion2 = {1,2,3};
        int[] testInversion3 = {4, 2, 1, 5, 8, 6};
        
        System.out.println("\nTests for easy inversion check: ");
        System.out.println(easyinversioncheck(testInversion));
        System.out.println(easyinversioncheck(testInversion2));
        System.out.println(easyinversioncheck(testInversion3));

        System.out.println("\nTests for fast inversion check: ");
        System.out.println(fastinversioncheck(testInversion));
        System.out.println(fastinversioncheck(testInversion2));
        System.out.println(fastinversioncheck(testInversion3));

        userInput.close();
    }
}
