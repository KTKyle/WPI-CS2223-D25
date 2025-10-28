import java.util.Scanner;

class RecursiveFunctions {
    // 1

    /**
     * This function simulates the Lucas Number Series using recursion
     * @param n the number of iterations of the Lucas Number Series we want
     * @return the value of the Lucas Number at n
     * Big O: O(2^n)
     */
    public static int lucasNumbers(int n){
        if(n == 0){
            return 2;
        }
        if(n == 1){
            return 1;
        }
        return lucasNumbers(n - 1) + lucasNumbers(n - 2);
    }

    /**
     * This function is my own personal sequence, simple but functional
     * @param n the number of iterations of the personalFunct series we want
     * @return the value of the personalFunct at n
     * Big O: O(2^n)
     */
    public static int personalFunct(int n){
        if(n == 0){
            return 8;
        }
        if(n == 1){
            return 3;
        }
        return (n + 4) + personalFunct(n - 1) + personalFunct(n - 2);
    }

    /**
    * The function calculates and prints the runtime and ratio of successive Lucas Numbers
    * @param lucasNumber the number of iterations of the Lucas Number Series to compute
    */
    public static void calculateLucasNum(int lucasNumber) {
        long previousVal = 0;
        long previousTime = 0;

        System.out.println("Individual Lucas Series run time of L(" + lucasNumber + "):\n");

        // Loops through each number leading up to the value the user wanted 
        for (int i = 0; i <= lucasNumber; i++) { 

            // The time it takes to compute the Lucas Numbers at index i
            long start = System.nanoTime();
            lucasNumbers(i);
            long end = System.nanoTime();

            long currTime = end - start;
            System.out.println("lucasNumber function at " + i + " took " + currTime + " nanoseconds");

            /**
             * Ratio of successive values and time respectively
             * ratioVal = L(n+1)/L(n)
             * ratioTime = Time(L(n+1))/Time(L(n))   
             */
            double ratioVal;
            double ratioTime;
            if (i == 0) {       // accounts for the initial case of the for loop (i starts from 0)
                ratioVal = 0;   // default value
                ratioTime = 0;  // default value
            } 
            else {
                ratioVal = (double) lucasNumbers(i) / previousVal;
                ratioTime = (double) currTime / previousTime;
            }
            System.out.printf("Ratio of Successive Calculations: %.2f, Ratio of Successive Calculation Times: %.2f\n\n", ratioVal, ratioTime);

            // Update ratio values respectively
            previousVal = lucasNumbers(i);
            previousTime = currTime;
        }
    }

    /**
     * The function calculates and prints the runtime and ratio of successive values for personalFunct
     * @param personalFunctInput the number of iterations of the personalFunct series to compute
     */
    public static void calculatePersonalNum(int personalFunctInput) {
        long previousVal = 0;
        long previousTime = 0;

        System.out.println("personalFunct Series run time of K(" + personalFunctInput + "):\n");

        // Loop through each number leading up to the amount the user wanted
        for (int i = 0; i <= personalFunctInput; i++) {

            // The time it takes to compute the personal funct at index i
            long start = System.nanoTime();
            personalFunct(i);
            long end = System.nanoTime();

            long currTime = end - start;
            System.out.println("personalFunct function at " + i + " took " + currTime + " nanoseconds");

            /**
             * Ratio of successive values and time respectively
             * ratioVal = K(n+1)/K(n)
             * ratioTime = Time(K(n+1))/Time(K(n))   
             */
            double ratioVal;
            double ratioTime;
            if (i == 0) {       // accounts for the initial case of the for loop
                ratioVal = 0;    // default value
                ratioTime = 0;   // default value
            } 
            else {
                ratioVal = (double) personalFunct(i) / previousVal;
                ratioTime = (double) currTime / previousTime;
            }

            System.out.printf("Ratio of Successive Calculations: %.2f, Ratio of Successive Calculation Times: %.2f\n\n", ratioVal, ratioTime);

            // Update ratio values respectively
            previousVal = personalFunct(i);
            previousTime = currTime;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Tests for Lucas Numbers
        System.out.print("Test Lucas Numbers: ");
        int lucasNumber = input.nextInt();

        // Time it takes to run the initial case the user wants
        long start = System.nanoTime();
        lucasNumbers(lucasNumber);
        long end = System.nanoTime();

        long completeTime = end - start;
        System.out.println("lucasNumber function L(" + lucasNumber + ") took " + completeTime + " nanoseconds\n\n");

         // Print each individual value up to the value the user wants
        calculateLucasNum(lucasNumber);

        

        // Tests for personalFunct numbers
        System.out.print("\n\nTest personalFunct: ");
        int personalFunctInput = input.nextInt();

        // Time it takes to run the initial case the user wants
        long startP = System.nanoTime();
        personalFunct(personalFunctInput);
        long endP = System.nanoTime();

        long completeTimeP = endP - startP;
        System.out.println("personalFunct function P(" + personalFunctInput + ") took " + completeTimeP + " nanoseconds\n\n");

        // Print each individual value up to the value the user wants
        calculatePersonalNum(personalFunctInput);

        input.close();
    }
}