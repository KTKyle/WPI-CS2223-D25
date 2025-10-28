import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Klutzomaniacs {
    /**
     * Recursively generates the binary reflected Gray code of order n
     * @param n positive integer representing the order of the gray code
     * @return A list of all bit strings of length n composing the Gray code
     */
    public static List<String> BRGG(int n){
        // Initializing a list to store the gray codes
        List<String> L = new ArrayList<>(); 

        // Base case
        if(n == 1){
            L.add("0");
            L.add("1");
            return L;
        } 

        // Create a list L1 of bit strings of (n - 1) recursively
        List<String> L1 = BRGG(n - 1);

        // Copy the list of L1 to L2
        List<String> L2 = new ArrayList<>(L1);
        // Reverse L2
        Collections.reverse(L2);

        // Add a "0" to each bit string in L1
        for (String bit : L1) {
            L.add("0" + bit);
        }

        // Add a "1" to each bit string in L2
        for (String bit : L2) {
            L.add("1" + bit);
        }

        return L;
    }

    /**
     * Prints the signature Klutzomaniac routine based on the number of laps they make
     * @param n the number of laps the Klutzomaniacs make
     */
    public static void klutzomaniacRoutine (int n){
        // Generate the gray code for n amount of laps
        List<String> grayCode = BRGG(n);

        // Names of the klutzomaniacs
        String[] klutzomaniacs = {"Enzo", "Doofus", "Crunchy", "Boxo", "Axel"};

        // Initial state before any Klutzomaniac joins the show
        System.out.println("0: Spotlight");

        // Iterate through the gray code to determine changes
        for(int i = 1; i < grayCode.size(); i++){
            String curr = grayCode.get(i);  // current gray code bit string to compare change later
            String prev = grayCode.get(i - 1); // previous gray code bit string to compare change later

            // Compare current and previous gray code bit strings to find changes
            for(int j = 0; j < n; j++){
                // Check if the bit at position j had changed
                if(curr.charAt(j) != prev.charAt(j)){
                    // if bit string is 1, the respective klutzomaniac join the show, else leaves
                    if(curr.charAt(j) == '1'){
                        System.out.println(i + ": " + klutzomaniacs[j] + " Joins");
                    } else {
                        System.out.println(i + ": " + klutzomaniacs[j] + " Leaves");
                    }
                }
            }
        }
        // Final state when all Klutzomaniacs leave the show
        System.out.println(grayCode.size() + ": Enzo Crashes");
    }

    public static void main(String[] args) {
        // Tests for gray codes
        System.out.println("Binary Reflected Gray Code for order 5: " + BRGG(5) + "\n");
        System.out.println("Klutzomaniac routine for 5 laps:");
        klutzomaniacRoutine(5);
    }
    
}
