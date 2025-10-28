This project consists of two seperate files which both use recursion for their respective reasons

    RecursiveFunctions.java:
        LucasNumbers
            - calculates the nth value in the Lucas Number Series

        personalFunct
            - a personal function created by me, hopefully somewhat unique
            - calculates the nth value in my "personalFunct" Series
        
        calculateLucasNum
            - calculates the times it takes to run each individual value of the Lucas Numbers until we reach the nth value
            - prints out how long it took to compute each value in nanoseconds

        calculatePersonalNum
            - calculates the times it takes to run each individual value of the Lucas Numbers until we reach the nth value
            - prints out how long it took to compute each value in nanoseconds


        main
            - utilizes I/O to record the amount of Lucas Numbers and personalFunct values the user wants to generate
            - calculates the time it takes to run the Lucas Numbers at the user's nth value 
            - prints out how long it took to compute in nanoseconds
            - calculates the time it takes to run the personalFunct Numbers at the user's nth value 
            - prints out how long it took to compute in nanoseconds

    
    SubirachsMagicSquare.java:
        instantiates a Subirach Magic Square
        instantiates a gloabal int variable that represents the total amount of values in the Subirach Magic Square

        extractElements
            - extracts all elements from a 2D array into a 1D array

        countSubsets
            - counts the subsets of an array that satisfy a specific condition and sum

        calculateMaxSum
            - calculates the maximum possible sum of all elements in an array
        
        countAllSums
            - counts all possible sums that can be formed from subsets of an array

        findMaxCombos
            - finds the sum with the greatest number of combinations from an array of totals

        main
            prints out the data using the afformentioned functions to display:
                - The amount of subsets that add up to the magic number with less than 4 elements
                - The amount of subsets that add up to the magic number with exactly 4 elements
                - The amount of subsets that add up to the magic number with more than 4 elements
                - The total number of ways every sum can be formed based on Subirach's Magic Sqaure
                - The sum that has the greatest number of combinations that produces said sum

This program was written by Kyle Truong with the aid of:
    - https://stackoverflow.com/questions: general syntax issues 
    - https://docs.oracle.com/javase/specs/: general syntax issues