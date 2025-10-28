This program consists of three different files 

    Palindromes.java 
        palindromecheck
            - takes in a String as input
            - a recursive boolean function that returns true if a given string is a palindrome, false otherwise
        
        main 
            - tests some premade cases 

    Inversions.java 
        easyinversioncheck
            - takes in an integer array as input 
            - a simple O(n^2) implementation of an inversion checker using nested for loops to count the amount of inversions in a given array

        fastinversioncheck 
            - takes in an integer array as input 
            - an O(nlog(n)) implementation of an inversion checker using a modified version of merge sort to count the amount of inversions present in a given array

        main
            - incorporates I/O for calculating the inversions of a custom array the user can create 
            - tests some premade cases for both easy and fast inversion check functions 

    Klutzomaniacs.java 
        BRGG 
            - takes in an int as input
            - gray code: binary representations of consecutive integers
            - takes in an integer 'n' representing the order of the gray code
            - recursively generates the binary reflected gray code of order 'n' 

        klutzomaniacRoutine 
            - takes in an int as input 
            - prints the signature Klutzomaniac routine based on the number of laps 'n' they make
            - the respective Klutzomaniac will be printed out joining or leaving based on if the bit string changed from 1 to 0 or vice versa respetively

This program was written by Kyle Truong with the aid of:
    - https://stackoverflow.com/questions
    - https://docs.oracle.com/javase/specs/