import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class ClosedHashing{
    String[] table;     
    int numElements;
    int tableCapacity;
    int hashConstant;

    public ClosedHashing(int capacity){
        this.tableCapacity = capacity;
        this.table = new String[capacity];
        this.numElements = 0;
        this.hashConstant = 31;
    }

    public ClosedHashing(int capacity, int hashConstant){
        this.tableCapacity = capacity;
        this.table = new String[capacity];
        this.numElements = 0;
        this.hashConstant = hashConstant;
    }

    /**
     * Function's purpose is to insert the given value into the hash table correctly
     * @param key the string we are putting in the table
     * @return true if we can insert the value, false otherwise
     */
    public boolean insert(String key) {
        if(numElements >= tableCapacity){   // if inserting this key exceeds value of the hash table
            return false;
        }

        int probe = hash(key, tableCapacity, hashConstant);
        int baseIndex = probe;  // used to keep track of where we started so we don't do an infinite loop

        while(table[probe] != null){
            if(table[probe].equals(key)){
                return false;   // duplicate value, don't insert here
            }

            probe = (probe + 1) % tableCapacity;   // move to next slot
            if(probe == baseIndex){
                return false;   // we have looped around the entire table and haven't found a valid spot
            }
        }

        // insert key into empty slot;
        table[probe] = key;
        numElements++;
        return true;
    }

     /**
     * Void function that prints out the hash table in a specific format
     * NOTE: -1 indicates null values in the table
     */
    public void displayTable() {
        for(int i = 0; i < tableCapacity; i++){
            if(table[i] != null){
                System.out.println(i + "    " + table[i] + "    " + hash(table[i], tableCapacity, hashConstant));
            }
            else {
                System.out.println(i + "        -1");
            }
        }
    }

    /**
     * Function's purpose is to generate the hash code for a given string
     * @param word the string we are hashing
     * @param tableSize size of the hash table
     * @param C the hash constant (provides an even spread of values for the hash codes to reduce collisions)
     * @return the hash code for the inputted string
     */
    public static int hash(String word, int tableSize, int C){
        int h = 0;
        for(int i = 0; i < word.length(); i++){
            h = (h * C + (int) word.charAt(i)) % tableSize;
        }
        return h;
    }

    /**
     * Function's purpose is to read from a file and input each word in the file into a given hash table
     * @param filePath the filepath to the file we are reading from
     * @param hashTable the hashtable we are inputting the hash codes into
     */
    public static void readFile(String filePath, ClosedHashing hashTable) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringBuilder word = new StringBuilder();
                for (char c : line.toCharArray()) {
                    if (Character.isLetter(c) || c == '\'' || c == '-') {
                        word.append(c); // Build the word
                    } else if (word.length() > 0) {
                        // Insert the word into the hash table
                        hashTable.insert(word.toString());
                        word.setLength(0); // Clear the word
                    }
                }
                // Insert the last word in the line if it exists
                if (word.length() > 0) {
                    hashTable.insert(word.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function's purpose is to count the number of non empty cells in the hash table
     * @param hashTable the hash table we are looking through
     * @return the number of non empty cells within the hash table
     */
    public static int countFilledCells(ClosedHashing hashTable){
        int count = 0;
        for(String cell : hashTable.table){
            if(cell != null){
                count++;
            }
        }
        return count;
    }

    /** 
     * Function's purpose is to find the largest gap of null (empty) cells 
     * @param hashTable the hash table we are traversing through
     * @return and integer array that holds the value of the largest gap and the indicies it started and ended at
     */
    public static int[] findLargestGap(ClosedHashing hashTable){
        int largestGap = 0;   // the largest number of consecutive empty cells
        int currGap = 0;      // the size of a current gap
        int gapStart = 0;     // the index the gap started at
        int gapFinish = 0;    // the index the gap finished at
        int temp = 0;         // placehodler value for keeping track of wrap arounds in the hash table

        for(int i = 0; i < hashTable.tableCapacity; i++){
            if(hashTable.table[i] == null){
                if(currGap == 0){
                    temp = i;
                }
                currGap++;
            } else {   // if cell at index i isn't null
                if(currGap > largestGap){
                    largestGap = currGap;
                    gapStart = temp;
                    gapFinish = i - 1;
                }
                currGap = 0;
            }
        }

        // accounts for end of hash table
        if(currGap > largestGap){
            largestGap = currGap;
            gapStart = temp;
            gapFinish = hashTable.tableCapacity - 1;
        }

        int[] gap = {largestGap, gapStart, gapFinish};    // contains gapStart and gapFinish for printing purposes later
        return gap; 
    }

    /**
     * Function's purpose is to track the largest cluster of non-empty cells in the hash table
     * @param hashTable the hash table we are traversing through
     * @return an integer array containing the values of: the largest cluster, index cluster starts at, and index cluster ends at
     */
    public static int[] findLargestCluster(ClosedHashing hashTable){
        int tableSize = hashTable.tableCapacity;    // size of the hash table
        int largestClusterSize = 0;                 // size of the largest cluster in the table
        int largestClusterStart = 0;                // index of where that largest cluster starts
        int largestClusterEnd = 0;                  // index of where that largest cluster ends

        for(int i = 0; i < tableSize; i++){
            // skips empty cells
            if(i < tableSize && hashTable.table[i] == null) i++;

            // reaching the end of the table
            if(i == tableSize) break;

            // handle case for wrapping around the hash table
            int start = i;
            int wrapSize = 0;
            while(hashTable.table[i % tableSize] != null && wrapSize < tableSize){
                wrapSize++;
                i++;
            }

            // update data
            int end = (start + wrapSize - 1) % tableSize;
            if(wrapSize > largestClusterSize){
                largestClusterSize = wrapSize;
                largestClusterStart = start;
                largestClusterEnd = end;
            }
        }

        int[] cluster = {largestClusterSize, largestClusterStart, largestClusterEnd};   // contains largestClusterSize, largestClusterStart, and largestClusterEnd for printing purposes later
        return cluster;
    }

    /**
     * Function's purpose is to find the most common hash value in a given hash table and the number of occurrences
     * @param hashTable the hash table we are traversing through
     * @return an integer array containing: the most common hash value and its amount of occurrences
     */
    public static int[] mostCommonHash(ClosedHashing hashTable){
        int[] hashValOccur = new int[hashTable.tableCapacity];
        for(String cell : hashTable.table){
            if(cell != null){ 
                int hashVal = ClosedHashing.hash(cell, hashTable.tableCapacity, hashTable.hashConstant);
                hashValOccur[hashVal]++;   // increment the number of occurrences of this specific hash value
            }
        }

        int maxCount = 0;   // keeps track of the number of occurrences of the most common hash
        int mostCommonHash = 0;   // index most common hash is found at
        for(int i = 0; i < hashValOccur.length; i++){
            if(hashValOccur[i] > maxCount){
                maxCount = hashValOccur[i];
                mostCommonHash = i;
            }
        }

        int[] commonHash = {mostCommonHash, maxCount};   // contains mostCommonHash and maxCount for printing purposes later
        return commonHash;
    }

    /**
     * Function's purpose is to find the string in the hash table that has deviated the most from it's original value due to a collision
     * @param hashTable the hash table we are traversing through
     * @return an integer array containing: the value of the largest shift, the original hash value of the shifted word, the final position the word ended up at
     */
    public static int[] findLargestDrift(ClosedHashing hashTable){
        int largestShift = 0;   // size of the gap between where the string was supposed to be and where it ended up
        int originalHash = 0;   // the hash value of that largest shifted string
        int finalPos = 0;       // the index in the hash table it ended up at

        for (int i = 0; i < hashTable.tableCapacity; i++) {
            if (hashTable.table[i] != null) {
                int hashVal = ClosedHashing.hash(hashTable.table[i], hashTable.tableCapacity, hashTable.hashConstant);   // hash value of string at index i
                int currShift = 0;

                // Handle wrap-around
                if(i >= hashVal){
                    currShift = i - hashVal;
                } else currShift = hashTable.tableCapacity - hashVal + i; 

                // update the largest shift if the current shift is greater
                if (currShift > largestShift) {
                    largestShift = currShift; // update largest shift size
                    originalHash = hashVal;   // update original hash value
                    finalPos = i;             // update final position
                }
            }
        }
        int[] drift = {largestShift, originalHash, finalPos};
        return drift;
    }

    /**
 * Function's purpose is to explore the hash table and print meaningful statistics about its state.
 * @param hashTable the hash table to explore
 */
public static void exploreHashTable(ClosedHashing hashTable) {
    int filledCells = countFilledCells(hashTable);                      // count the number of filled cells
    int openCells = hashTable.tableCapacity - filledCells;              // calculate the number of open cells
    double loadFactor = (double) filledCells / hashTable.tableCapacity; // calculate the load factor

    int[] largestGap = findLargestGap(hashTable);         // data for the largest gap
    int[] largestCluster = findLargestCluster(hashTable); // data for the largest cluster
    int[] mostCommonHash = mostCommonHash(hashTable);     // data forthe most common hash value
    int[] largestDrift = findLargestDrift(hashTable);     // data for the largest drift

    // Print the statistics
    System.out.println("There are " + filledCells + " distinct entries in the hash table.");
    System.out.println("There are " + openCells + " empty cells in the hash table.");
    System.out.printf("The load factor (alpha) is thus %.6f for our hash table.%n", loadFactor);
    System.out.println("\nThe longest run of open cells is " + largestGap[0] + " entries long:");
    System.out.println("Position " + largestGap[1] + " to position " + largestGap[2] + " (inclusive)");
    System.out.println("\nThe longest cluster is " + largestCluster[0] + " entries long:");
    System.out.println("Position " + largestCluster[1] + " to position " + largestCluster[2] + " (inclusive)");
    System.out.println("\nHash value " + mostCommonHash[0] + " occurs " + mostCommonHash[1] + " times.");
    System.out.println("\nThe largest drift is " + largestDrift[0] + " places: " + hashTable.table[largestDrift[2]]);
    System.out.println("Original hash address: " + largestDrift[1]);
    System.out.println("Final position: " + largestDrift[2] +"\n");
}

    public static void main(String[] args){
        // removed personal file path as to not show information from my personal comp
        String filePath1 = "";
        String filePath2 = "";
        String filePath3 = "";
        String filePath4 = "";

        int tableSize1 = 397;
        int tableSize2 = 1307;
        int tableSize3 = 4007;
        int tableSize4 = 997;

       int C1 = 123;

       ClosedHashing hashTable1 = new ClosedHashing(tableSize1, C1); 
       ClosedHashing hashTable2 = new ClosedHashing(tableSize2, C1);
       ClosedHashing hashTable3 = new ClosedHashing(tableSize3, C1);
       ClosedHashing hashTable4 = new ClosedHashing(tableSize4, C1);

       // Executing test cases:
       readFile(filePath1, hashTable1);
       exploreHashTable(hashTable1);
       hashTable1.displayTable();

    //    System.out.println();
    //    readFile(filePath2, hashTable2);
    //    exploreHashTable(hashTable2);
    //    hashTable2.displayTable();

    //    System.out.println();
    //    readFile(filePath2, hashTable3);
    //    exploreHashTable(hashTable3);
    //    hashTable3.displayTable();

    //    System.out.println();
    //    readFile(filePath3, hashTable3);
    //    exploreHashTable(hashTable3);
    //    hashTable3.displayTable();

    //    System.out.println();
    //    readFile(filePath4, hashTable4);
    //    exploreHashTable(hashTable4);
    //    hashTable4.displayTable();

       // User input
       Scanner scanner = new Scanner(System.in);

       System.out.print("Enter the file path: ");
       String filePath = scanner.nextLine();    

       System.out.print("Enter the table size: ");
       int tableSize = scanner.nextInt();

       System.out.print("Enter the hash constant: ");
       int hashConstant = scanner.nextInt();

       ClosedHashing userHashTable = new ClosedHashing(tableSize, hashConstant);
       readFile(filePath, userHashTable);
       userHashTable.displayTable();
       exploreHashTable(userHashTable);
       
       scanner.close();
    }
}
