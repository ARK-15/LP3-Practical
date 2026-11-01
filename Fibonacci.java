/*
 * DAA LP3 Practical
 * Experiment: Fibonacci Numbers
 *
 * Aim:
 * Write a non-recursive and recursive program to calculate
 * Fibonacci numbers and analyze their time and space complexity.
 *
 * Fibonacci Series:
 * F(0) = 0
 * F(1) = 1
 * F(n) = F(n-1) + F(n-2)
 *
 * Non-Recursive (Iterative):
 * - Uses a loop to calculate Fibonacci numbers.
 * - Time Complexity: O(n)
 * - Space Complexity: O(1)
 *
 * Recursive:
 * - Calls itself to calculate F(n-1) and F(n-2).
 * - Time Complexity: O(2^n) approximately
 * - Space Complexity: O(n) due to recursive call stack.
 *
 * Sample Test Case:
 * Input:
 * n = 7
 *
 * Output:
 * Fibonacci number using Non-Recursive method: 13
 * Fibonacci number using Recursive method: 13
 *
 * Oral/Viva Points:
 * 1. Fibonacci starts with 0 and 1.
 * 2. Every next number is the sum of the previous two.
 * 3. Iterative method is faster because it does not repeat calculations.
 * 4. Recursive method repeatedly calculates the same Fibonacci values.
 * 5. Recursive method uses extra stack space.
 */

import java.util.Scanner;

public class Fibonacci {

    // Non-Recursive / Iterative Method
    static int fibonacciIterative(int n) {

        if (n == 0)
            return 0;

        if (n == 1)
            return 1;

        int a = 0;
        int b = 1;

        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }

        return b;
    }

    // Recursive Method
    static int fibonacciRecursive(int n) {

        if (n == 0)
            return 0;

        if (n == 1)
            return 1;

        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter n: ");
        int n = sc.nextInt();

        if (n < 0) {
            System.out.println("\nPlease enter a non-negative number.");
            sc.close();
            return;
        }

        int iterativeResult = fibonacciIterative(n);
        int recursiveResult = fibonacciRecursive(n);

        System.out.println("\nFibonacci number using Non-Recursive method: " + iterativeResult);

        System.out.println("\nFibonacci number using Recursive method: " + recursiveResult);

        sc.close();
    }
}