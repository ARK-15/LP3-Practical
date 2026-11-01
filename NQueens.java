/*
 * DAA LP3 Practical
 * Experiment: N-Queens using Backtracking
 *
 * Aim:
 * Design an N-Queens matrix having the first Queen placed
 * and use backtracking to place the remaining Queens.
 *
 * Problem:
 * Place N Queens on an N x N chessboard such that no two
 * Queens attack each other.
 *
 * A Queen can attack another Queen if they are in:
 * 1. Same row
 * 2. Same column
 * 3. Same diagonal
 *
 * Approach:
 * 1. Place the first Queen at a given position.
 * 2. Try to place one Queen in each remaining row.
 * 3. Check whether the position is safe.
 * 4. If safe, place the Queen and move to the next row.
 * 5. If no position is possible, backtrack and remove the
 *    previously placed Queen.
 *
 * Backtracking:
 * Try a possible solution -> If it fails, undo the choice
 * and try another possibility.
 *
 * Time Complexity: O(N!)
 * Space Complexity: O(N^2) for the chessboard
 *                   + O(N) for recursion stack
 *
 * Sample Input:
 * Enter value of N: 4
 * Enter row of first Queen (1-based): 1
 * Enter column of first Queen (1-based): 2
 *
 * Sample Output:
 * . Q . .
 * . . . Q
 * Q . . .
 * . . Q .
 *
 * Viva Points:
 * 1. N-Queens is a constraint satisfaction problem.
 * 2. Backtracking is used to systematically try possible positions.
 * 3. A Queen must not share a row, column, or diagonal with another Queen.
 * 4. If a choice leads to no solution, we undo that choice.
 */

import java.util.Scanner;

public class NQueens {

    // Check whether a Queen can be placed at board[row][col]
    static boolean isSafe(int[][] board, int row, int col, int n) {

        // Check the same column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1)
                return false;
        }

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1;
             i >= 0 && j >= 0;
             i--, j--) {

            if (board[i][j] == 1)
                return false;
        }

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1;
             i >= 0 && j < n;
             i--, j++) {

            if (board[i][j] == 1)
                return false;
        }

        return true;
    }

    // Backtracking function to place remaining Queens
    static boolean solveNQueens(int[][] board, int row, int n) {

        // All Queens have been successfully placed
        if (row == n)
            return true;

        // Try every column in the current row
        for (int col = 0; col < n; col++) {

            if (isSafe(board, row, col, n)) {

                // Place Queen
                board[row][col] = 1;

                // Recursively place Queen in next row
                if (solveNQueens(board, row + 1, n))
                    return true;

                // Backtrack: remove Queen
                board[row][col] = 0;
            }
        }

        return false;
    }

    // Display the chessboard
    static void printBoard(int[][] board, int n) {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] == 1)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }

            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter value of N: ");
        int n = sc.nextInt();

        if (n < 1) {
            System.out.println("\nN must be positive.");
            sc.close();
            return;
        }

        System.out.print("\nEnter row of first Queen (1-based): ");
        int firstRow = sc.nextInt();

        System.out.print("\nEnter column of first Queen (1-based): ");
        int firstCol = sc.nextInt();

        // Convert 1-based position to 0-based index
        firstRow--;
        firstCol--;

        if (firstRow < 0 || firstRow >= n ||
            firstCol < 0 || firstCol >= n) {

            System.out.println("\nInvalid position.");
            sc.close();
            return;
        }

        int[][] board = new int[n][n];

        // Place the first Queen
        board[firstRow][firstCol] = 1;

        /*
         * This program assumes the first Queen is placed
         * in the first row.
         *
         * Therefore, solve remaining rows starting from row 1.
         */
        if (firstRow != 0) {
            System.out.println(
                "\nFor this program, first Queen must be in row 1."
            );
            sc.close();
            return;
        }

        // Place remaining Queens
        boolean result = solveNQueens(board, 1, n);

        if (result) {
            System.out.println("\nN-Queens Solution:");
            printBoard(board, n);
        } else {
            System.out.println(
                "\nNo solution exists with the given first Queen position."
            );
        }

        sc.close();
    }
}