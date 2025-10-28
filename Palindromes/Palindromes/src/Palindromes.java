public class Palindromes {
    
    /**
     * The function's purpose is to check if a given string is a palindrome inclusive of whitespace
     * @param palindrome the string we are checking to see if it is a palindrome or not
     * @return true if the given string is a palindrome, false otherwise
     */
    public static boolean palindromecheck (String palindrome){

        // We only care about the letters/values not the capitilization
        String palindromeCopy = palindrome.toLowerCase();   
        if(palindromeCopy.length() <= 1){   // base case
            return true;
        }

        char firstChar = palindromeCopy.charAt(0);  // first character
        char lastChar = palindromeCopy.charAt(palindromeCopy.length() - 1); // last character

        // Checks if the first and last char aren't the same meaning the string can't be a palindrome
        if(firstChar != lastChar){ 
            return false;
        }
        
        // Return the shortened version of the string exclusive of the previous first and last characters
        return palindromecheck(palindromeCopy.substring(1, palindromeCopy.length() - 1));
    }

    public static void main(String[] args) {
        // Tests for palindromes
        System.out.println("Palindrome tests: ");
        String test = "Kayak";
        String test2 = "Able was I ere I saw Elba";
        String test3 = "peEp";
        String test4 = "askdfjasldfkja";
        String test5 = "abba";
        String test6 = "aba";

        System.out.println(test + ": "  + palindromecheck(test));
        System.out.println(test2 + ": " + palindromecheck(test2));
        System.out.println(test3 + ": " + palindromecheck(test3));
        System.out.println(test4 + ": " + palindromecheck(test4));
        System.out.println(test5 + ": " + palindromecheck(test5));
        System.out.println(test6 + ": " + palindromecheck(test6));
    }
}
