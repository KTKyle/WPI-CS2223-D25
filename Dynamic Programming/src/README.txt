Gaussian Elimination and Dynamic Programming

This project was a collection of two files
    - GaussianElimination.java
    - Arkenstone.java

How Arkenstone.java is supposed to work:

    Paraphrased Prompt For Context:

    The Arkenstone, a treasured jewel, is locked in a magically sealed vault beneath the Lonely Mountain, 
    within a treasure room whose 64-square floor alternates gold and silver tiles. Each square holds a different number of precious gemstones.
    To unlock the vault, one must walk the "Most Precious Path"—the route that collects the highest number of gemstones. 
    Bilbo Baggins, wishing to see the Arkenstone again, seeks to find this path. The task is to design and implement a 
    dynamic programming algorithm in java that helps Bilbo determine the Most Precious Path across the room's floor.
    
    Rules: 
        we can start anywhere in row 1 (the bottom of the matrix/row 7) to choose as Bilbo's starting point
        - we only chose one adjacent cell in a row to occupy/take gems from:
        - we can only move the square directly ahead (above)
        - diagonal left
        - diagonal right

        The only stipulation is that of our three options we are only permitted to take the highest yielding value
        of gems to maximize our journey through the treasure room. We will then end up at the last row (the top of the matrix/row 1)

        We then print out the following:
        - Print Bilbo's starting square
        - Print some representation fo Bilbo's path
        - The total number of gems he collected on the most precious path to the Arkenstone
        - The vault (column) wherein Bilbo found the Arkenstone

What I learned in each file:
    GaussianElimination.java:
        - To work on implementing pseudocode for a flawed algorithm and try to find and improve it's vulnerabilities
        - Create a new function to implement the Gauss-Jordan Elimination method utilizing the improvements noted in the first two algorithms form the textbook
        - Strengthens my understanding of Gaussian Elimination techniques 
    
    Arkenstone.java:
        - Practiced using dynamic programming approach to solve problem
        - Worked on traversing a matrix and keeping track of relevant data

This program was written by Kyle Truong with the aid of:
    - https://stackoverflow.com/questions
    - Introduction to The Design and Analysis of Algorithms 3rd Edition - Anany Levitin

        